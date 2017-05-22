package de.fub.bytecode.generic;
import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * LDC - Push item from constant pool.
 *
 * <PRE>Stack: ... -&gt; ..., item</PRE>
 *
 * @version $Id: LDC.java,v 1.11 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LDC extends CPInstruction
  implements PushInstruction, ExceptionThrower, TypedInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LDC() {}

  public LDC(int index) {
    super(de.fub.bytecode.Constants.LDC_W, index);
    setSize();
  }
  
  // Adjust to proper size
  protected final void setSize() {
    if(index <= de.fub.bytecode.Constants.MAX_BYTE) { // Fits in one byte?
      opcode = de.fub.bytecode.Constants.LDC;
      length = 2;
    } else {
      opcode = de.fub.bytecode.Constants.LDC_W;
      length = 3;
    }
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode);

    if(length == 2)
      out.writeByte(index);
    else // Applies for LDC_W
      out.writeShort(index);
  }

  /**
   * Set the index to constant pool and adjust size.
   */
  public final void setIndex(int index) { 
    super.setIndex(index);
    setSize();
  }

  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
       throws IOException
  {
    length = 2;
    index  = bytes.readUnsignedByte();
  }

  public Object getValue(ConstantPoolGen cpg) {
    de.fub.bytecode.classfile.Constant c = cpg.getConstantPool().getConstant(index);

    switch(c.getTag()) {
      case de.fub.bytecode.Constants.CONSTANT_String:
	int i = ((de.fub.bytecode.classfile.ConstantString)c).getStringIndex();
	c = cpg.getConstantPool().getConstant(i);
	return ((de.fub.bytecode.classfile.ConstantUtf8)c).getBytes();

    case de.fub.bytecode.Constants.CONSTANT_Float:
	return new Float(((de.fub.bytecode.classfile.ConstantFloat)c).getBytes());

    case de.fub.bytecode.Constants.CONSTANT_Integer:
	return new Integer(((de.fub.bytecode.classfile.ConstantInteger)c).getBytes());

    default: // Never reached
      throw new RuntimeException("Unknown or invalid constant type at " + index);
      }
  }

  public Type getType(ConstantPoolGen cpg) {
    switch(cpg.getConstantPool().getConstant(index).getTag()) {
    case de.fub.bytecode.Constants.CONSTANT_String:  return Type.STRING;
    case de.fub.bytecode.Constants.CONSTANT_Float:   return Type.FLOAT;
    case de.fub.bytecode.Constants.CONSTANT_Integer: return Type.INT;
    default: // Never reached
      throw new RuntimeException("Unknown or invalid constant type at " + index);
    }
  }

  public Class[] getExceptions() {
    return de.fub.bytecode.ExceptionConstants.EXCS_STRING_RESOLUTION;
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
    v.visitStackProducer(this);
    v.visitPushInstruction(this);
    v.visitExceptionThrower(this);
    v.visitTypedInstruction(this);
    v.visitCPInstruction(this);
    v.visitLDC(this);
  }
}
