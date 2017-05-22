package de.fub.bytecode.generic;

import de.fub.bytecode.Constants;
import de.fub.bytecode.classfile.Utility;
import de.fub.bytecode.classfile.ConstantPool;
import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * Abstract super class for all Java byte codes.
 *
 * @version $Id: Instruction.java,v 1.13 2001/10/11 11:59:27 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class Instruction implements Cloneable, Serializable {
  protected short length = 1;  // Length of instruction in bytes 
  protected short opcode = -1; // Opcode number

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  Instruction() {}

  public Instruction(short opcode, short length) {
    this.length = length;
    this.opcode = opcode;
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode); // Common for all instructions
  }

  /**
   * Long output format:
   *
   * &lt;name of opcode&gt; "["&lt;opcode number&gt;"]" 
   * "("&lt;length of instruction&gt;")"
   *
   * @param verbose long/short format switch
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
    if(verbose)
      return Constants.OPCODE_NAMES[opcode] + "[" + opcode + "](" + length + ")";
    else
      return Constants.OPCODE_NAMES[opcode];
  }

  /**
   * @return mnemonic for instruction in verbose format
   */
  public String toString() {
    return toString(true);
  }

  /**
   * @return mnemonic for instruction with sumbolic references resolved
   */
  public String toString(ConstantPool cp) {
    return toString(false);
  }

  /**
   * Use with caution, since `BranchInstruction's have a `target' reference which
   * is not copied correctly (only basic types are). This also applies for 
   * `Select' instructions with their multiple branch targets.
   *
   * @see BranchInstruction
   * @return (shallow) copy of an instruction
   */
  public Instruction copy() {
    Instruction i = null;

    // "Constant" instruction, no need to duplicate
    if(InstructionConstants.INSTRUCTIONS[this.getOpcode()] != null)
      i = this;
    else {
      try {
	i = (Instruction)clone();
      } catch(CloneNotSupportedException e) {
	System.err.println(e);
      }
    }

    return i;
  }
  
  /**
   * Read needed data (e.g. index) from file.
   *
   * @param bytes byte sequence to read from
   * @param wide "wide" instruction flag
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
    throws IOException
  {}  

  /**
   * Read an instruction from (byte code) input stream and return the
   * appropiate object.
   *
   * @param file file to read from
   * @return instruction object being read
   */
  public static final Instruction readInstruction(ByteSequence bytes)
    throws IOException
  {
    boolean     wide   = false;
    short       opcode = (short)bytes.readUnsignedByte();
    Instruction obj    = null;

    if(opcode == Constants.WIDE) { // Read next opcode after wide byte
      wide = true;
      opcode  = (short)bytes.readUnsignedByte();
    }

    if(InstructionConstants.INSTRUCTIONS[opcode] != null)
      return InstructionConstants.INSTRUCTIONS[opcode]; // Used predefined immutable object, if available

    /* Find appropiate class, instantiate an (empty) instruction object
     * and initialize it by hand.
     */
    Class clazz;
    try {
      clazz = Class.forName(className(opcode));
    }
    catch (ClassNotFoundException cnfe){
      // If a class by that name does not exist, the opcode is illegal.
      // Note that IMPDEP1, IMPDEP2, BREAKPOINT are also illegal in a sense.
      throw new ClassGenException("Illegal opcode detected.");
    }
    try {
      obj = (Instruction)clazz.newInstance();

      if(wide && !((obj instanceof LocalVariableInstruction) || (obj instanceof IINC) ||
		   (obj instanceof RET)))
	throw new Exception("Illegal opcode after wide: " + opcode);

      obj.setOpcode(opcode);
      obj.initFromFile(bytes, wide); // Do further initializations, if any
      // Byte code offset set in InstructionList
    } catch(Exception e) { throw new ClassGenException(e.toString()); }

    return obj;
  }

  private static final String className(short opcode) {
    String name = Constants.OPCODE_NAMES[opcode].toUpperCase();

    /* ICONST_0, etc. will be shortened to ICONST, etc., since ICONST_0 and the like
     * are not implemented (directly).
     */
    try {
      int  len = name.length();
      char ch1 = name.charAt(len - 2), ch2 = name.charAt(len - 1);

      if((ch1 == '_') && (ch2 >= '0')  && (ch2 <= '5'))
	name = name.substring(0, len - 2);
      
      if(name.equals("ICONST_M1")) // Special case
	name = "ICONST";
    } catch(StringIndexOutOfBoundsException e) { System.err.println(e); }

    return "de.fub.bytecode.generic." + name;
  }

  /**
   * @deprecated Use consumeStack(cp) instead which always gives correct results
   * @return Number of words consumed from stack by this instruction,
   * or Constants.UNPREDICTABLE, if this can not be computed statically
   */
  public int consumeStack() { return Constants.CONSUME_STACK[opcode]; }

  /**
   * @deprecated Use produceStack(cp) instead which always gives correct results
   * @return Number of words produced onto stack by this instruction,
   * or Constants.UNPREDICTABLE, if this can not be computed statically
   */
  public int produceStack() { return Constants.PRODUCE_STACK[opcode]; }

  /**
   * This method also gives right results for instructions whose
   * effect on the stack depends on the constant pool entry they
   * reference.
   *  @return Number of words consumed from stack by this instruction
   */
  public int consumeStack(ConstantPoolGen cpg) {
    return consumeStack();
  }

  /**
   * This method also gives right results for instructions whose
   * effect on the stack depends on the constant pool entry they
   * reference.
   * @return Number of words produced onto stack by this instruction
   */
  public int produceStack(ConstantPoolGen cpg) {
    return produceStack();
  }
 
  /** 
   * @deprecated in favor of getOpcode()
   * @return opcode number
   */
  public short getTag()    { return opcode; }

  /**
   * @return this instructions opcode
   */
  public short getOpcode()    { return opcode; }

  /**
   * @return length (in bytes) of instruction
   */
  public int getLength()   { return length; }

  /**
   * Needed in readInstruction.
   */
  private void setOpcode(short opcode) { this.opcode = opcode; }

  /** Some instructions may be reused, so don't do anything by default.
   */
  void dispose() {  }

  /**
   * Call corresponding visitor method(s). The order is:
   * Call visitor methods of implemented interfaces first, then
   * call methods according to the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor object
   */
  public abstract void accept(Visitor v);
}
