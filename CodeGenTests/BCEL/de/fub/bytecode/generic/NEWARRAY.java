package de.fub.bytecode.generic;
import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * NEWARRAY -  Create new array of basic type (int, short, ...)
 * <PRE>Stack: ..., count -&gt; ..., arrayref</PRE>
 * type must be one of T_INT, T_SHORT, ...
 * 
 * @version $Id: NEWARRAY.java,v 1.8 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class NEWARRAY extends Instruction
  implements AllocationInstruction, ExceptionThrower, StackProducer {
  private byte type;

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  NEWARRAY() {}

  public NEWARRAY(byte type) {
    super(de.fub.bytecode.Constants.NEWARRAY, (short)2);
    this.type = type;
  }

  public NEWARRAY(BasicType type) {
      this(type.getType());
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode);
    out.writeByte(type);
  }

  /**
   * @return numeric code for basic element type
   */
  public final byte getTypecode() { return type; }

  /**
   * @return type of constructed array
   */
  public final Type getType() {
    return new ArrayType(BasicType.getType(type), 1);
  }

  /**
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
    return super.toString(verbose) + " " + de.fub.bytecode.Constants.TYPE_NAMES[type];
  }
  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
    type   = bytes.readByte();
    length = 2;
  }

  public Class[] getExceptions() {
    return new Class[] { de.fub.bytecode.ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION };
  }


  /**
   * Call corresponding visitor method(s). The order is:
   * Call visitor methods of implemented interfaces first, then
   * call methods according to the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitAllocationInstruction(this);
    v.visitExceptionThrower(this);
    v.visitStackProducer(this);
    v.visitNEWARRAY(this);
  }
}
