package de.fub.bytecode.generic;

import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * Abstract super class for branching instructions like GOTO, IFEQ, etc..
 * Branch instructions may have a variable length, namely GOTO, JSR, 
 * LOOKUPSWITCH and TABLESWITCH.
 *
 * @see InstructionList
 * @version $Id: BranchInstruction.java,v 1.4 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class BranchInstruction extends Instruction implements InstructionTargeter {
  protected int               index;    // Branch target relative to this instruction
  protected InstructionHandle target;   // Target object in instruction list
  protected int               position; // Byte code offset

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  BranchInstruction() {}

  /** Common super constructor
   * @param opcodee Instruction opcode
   * @param target instruction to branch to
   */
  protected BranchInstruction(short opcode, InstructionHandle target) {
    super(opcode, (short)3);
    setTarget(target);
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode);

    index = getTargetOffset();

    if(Math.abs(index) >= 32767) // too large for short
      throw new ClassGenException("Branch target offset too large for short");

    out.writeShort(index); // May be negative, i.e., point backwards
  }

  /**
   * @param target branch target
   * @return the offset to  `target' relative to this instruction
   */
  protected int getTargetOffset(InstructionHandle target) {
    if(target == null)
      throw new ClassGenException("Target of " + super.toString(true) + 
				  " is invalid null handle");

    int t = target.getPosition();

    if(t < 0)
      throw new ClassGenException("Invalid branch target position offset for " +
				  super.toString(true) + ":" + t + ":" + target);

    return t - position;
  }

  /**
   * @return the offset to this instruction's target
   */
  protected int getTargetOffset() { return getTargetOffset(target); }

  /**
   * Called by InstructionList.setPositions when setting the position for every
   * instruction. In the presence of variable length instructions `setPositions'
   * performs multiple passes over the instruction list to calculate the
   * correct (byte) positions and offsets by calling this function.
   *
   * @param offset additional offset caused by preceding (variable length) instructions
   * @param max_offset the maximum offset that may be caused by these instructions
   * @return additional offset caused by possible change of this instruction's length
   */
  protected int updatePosition(int offset, int max_offset) {
    position += offset;
    return 0;
  }

  /**
   * Long output format:
   *
   * &lt;position in byte code&gt;
   * &lt;name of opcode&gt; "["&lt;opcode number&gt;"]" 
   * "("&lt;length of instruction&gt;")"
   * "&lt;"&lt;target instruction&gt;"&gt;" "@"&lt;branch target offset&gt;
   *
   * @param verbose long/short format switch
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
    String s = super.toString(verbose);
    String t = "null";

    if(verbose) {
      if(target != null) {
	if(target.getInstruction() == this)
	  t = "<points to itself>";
	else if(target.getInstruction() == null)
	  t = "<null instruction!!!?>";
	else
	  t = target.getInstruction().toString(false); // Avoid circles
      }
    } else {
      if(target != null) {
	index = getTargetOffset();
	t = "" + (index + position);
      }
    }

    return s + " -> " + t;
  }

  /**
   * Read needed data (e.g. index) from file. Conversion to a InstructionHandle
   * is done in InstructionList(byte[]).
   *
   * @param bytes input stream
   * @param wide wide prefix?
   * @see InstructionList
   */
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
    length = 3;
    index  = bytes.readShort();
  }

  /**
   * @return target offset in byte code
   */
  public final int getIndex() { return index; }

  /**
   * @return target of branch instruction
   */
  public InstructionHandle getTarget() { return target; }

  /**
   * Set branch target
   * @param target branch target
   */
  public void setTarget(InstructionHandle target) {
    notifyTarget(this.target, target, this);
    this.target = target;
  }

  /**
   * Used by BranchInstruction, LocalVariableGen, CodeExceptionGen
   */
  static final void notifyTarget(InstructionHandle old_ih, InstructionHandle new_ih,
				 InstructionTargeter t) {
    if(old_ih != null)
      old_ih.removeTargeter(t);
    if(new_ih != null)
      new_ih.addTargeter(t);
  }

  /**
   * @param old_ih old target
   * @param new_ih new target
   */
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
    if(target == old_ih)
      setTarget(new_ih);
    else
      throw new ClassGenException("Not targeting " + old_ih + ", but " + target);
  }

  /**
   * @return true, if ih is target of this instruction
   */
  public boolean containsTarget(InstructionHandle ih) {
    return (target == ih);
  }

  /**
   * Inform target that it's not targeted anymore.
   */
  void dispose() {
    setTarget(null);
    index=-1;
    position=-1;
  }
}
