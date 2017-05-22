package de.fub.bytecode.generic;
import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * Select - Abstract super class for LOOKUPSWITCH and TABLESWITCH instructions.
 *
 * @version $Id: Select.java,v 1.4 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see LOOKUPSWITCH
 * @see TABLESWITCH
 * @see InstructionList
 */
public abstract class Select extends BranchInstruction
  implements VariableLengthInstruction, StackProducer
{
  protected int[]               match;        // matches, i.e., case 1: ...
  protected int[]               indices;      // target offsets
  protected InstructionHandle[] targets;      // target objects in instruction list
  protected int                 fixed_length; // fixed length defined by subclasses
  protected int                 match_length; // number of cases
  protected int                 padding = 0;  // number of pad bytes for alignment
  
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  Select() {}

  /**
   * (Match, target) pairs for switch.
   * `Match' and `targets' must have the same length of course.
   *
   * @param match array of matching values
   * @param targets instruction targets
   * @param target default instruction target
   */
  Select(short opcode, int[] match, InstructionHandle[] targets,
	 InstructionHandle target) {
    super(opcode, target);

    this.targets = targets;
    for(int i=0; i < targets.length; i++)
      notifyTarget(null, targets[i], this);

    this.match = match;

    if((match_length = match.length) != targets.length)
      throw new ClassGenException("Match and target array have not the same length");

    indices = new int[match_length];
  }

  /**
   * Since this is a variable length instruction, it may shift the following
   * instructions which then need to update their position.
   *
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
    position += offset; // Additional offset caused by preceding SWITCHs, GOTOs, etc.

    short old_length = length;

    /* Alignment on 4-byte-boundary, + 1, because of tag byte.
     */
    padding = (4 - ((position + 1) % 4)) % 4;
    length  = (short)(fixed_length + padding); // Update length

    return length - old_length;
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode);

    for(int i=0; i < padding; i++) // Padding bytes
      out.writeByte(0);

    index = getTargetOffset();     // Write default target offset
    out.writeInt(index);
  }

  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
    padding = (4 - (bytes.getIndex() % 4)) % 4; // Compute number of pad bytes

    for(int i=0; i < padding; i++) {
      byte b;
      if((b=bytes.readByte()) != 0)
	throw new ClassGenException("Padding byte != 0: " + b);
    }
    
    // Default branch target common for both cases (TABLESWITCH, LOOKUPSWITCH)
    index = bytes.readInt();
  }

  /**
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
    StringBuffer buf = new StringBuffer(super.toString(verbose));

    if(verbose) {
      for(int i=0; i < match_length; i++) {
	String s = "null";
	
	if(targets[i] != null)
	  s = targets[i].getInstruction().toString();
	
	buf.append("(" + match[i] + ", " + s + " = {" + indices[i] + "})");
      }
    }
    else
      buf.append(" ...");
    
    return buf.toString();
  }

  /**
   * Set branch target for `i'th case
   */
  public void setTarget(int i, InstructionHandle target) {
    notifyTarget(targets[i], target, this);
    targets[i] = target;
  }

  /**
   * @param old_ih old target
   * @param new_ih new target
   */
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
    boolean targeted = false;

    if(target == old_ih) {
      targeted = true;
      setTarget(new_ih);
    }

    for(int i=0; i < targets.length; i++) {
      if(targets[i] == old_ih) {
	targeted = true;
	setTarget(i, new_ih);
      }
    }
    
    if(!targeted)
      throw new ClassGenException("Not targeting " + old_ih);
  }

  /**
   * @return true, if ih is target of this instruction
   */
  public boolean containsTarget(InstructionHandle ih) {
    if(target == ih)
      return true;

    for(int i=0; i < targets.length; i++)
      if(targets[i] == ih)
	return true;

    return false;
  }

  /**
   * Inform targets that they're not targeted anymore.
   */
  void dispose() {
    super.dispose();

    for(int i=0; i < targets.length; i++)
      targets[i].removeTargeter(this);
  }

  /**
   * @return array of match indices
   */
  public int[] getMatchs() { return match; }

  /**
   * @return array of match target offsets
   */
  public int[] getIndices() { return indices; }

  /**
   * @return array of match targets
   */
  public InstructionHandle[] getTargets() { return targets; }
}
