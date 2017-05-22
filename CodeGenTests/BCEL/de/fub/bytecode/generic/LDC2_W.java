package de.fub.bytecode.generic;

/** 
 * LDC2_W - Push long or double from constant pool
 *
 * <PRE>Stack: ... -&gt; ..., item.word1, item.word2</PRE>
 *
 * @version $Id: LDC2_W.java,v 1.8 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LDC2_W extends CPInstruction
  implements PushInstruction, TypedInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LDC2_W() {}

  public LDC2_W(int index) {
    super(de.fub.bytecode.Constants.LDC2_W, index);
  }

  public Type getType(ConstantPoolGen cpg) {
    switch(cpg.getConstantPool().getConstant(index).getTag()) {
    case de.fub.bytecode.Constants.CONSTANT_Long:   return Type.LONG;
    case de.fub.bytecode.Constants.CONSTANT_Double: return Type.DOUBLE;
    default: // Never reached
      throw new RuntimeException("Unknown constant type " + opcode);
    }
  }

  public Number getValue(ConstantPoolGen cpg) {
    de.fub.bytecode.classfile.Constant c = cpg.getConstantPool().getConstant(index);

    switch(c.getTag()) {
    case de.fub.bytecode.Constants.CONSTANT_Long:
	return new Long(((de.fub.bytecode.classfile.ConstantLong)c).getBytes());

    case de.fub.bytecode.Constants.CONSTANT_Double:
	return new Double(((de.fub.bytecode.classfile.ConstantDouble)c).getBytes());

    default: // Never reached
      throw new RuntimeException("Unknown or invalid constant type at " + index);
      }
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
    v.visitTypedInstruction(this);
    v.visitCPInstruction(this);
    v.visitLDC2_W(this);
  }
}
