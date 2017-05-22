package de.fub.bytecode.generic;

/** 
 * ICONST - Push value between -1, ..., 5, other values cause an exception
 *
 * <PRE>Stack: ... -&gt; ..., <i></PRE>
 *
 * @version $Id: ICONST.java,v 1.7 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ICONST extends Instruction
  implements ConstantPushInstruction, TypedInstruction {
  private int value;

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  ICONST() {}

  public ICONST(int i) {
    super(de.fub.bytecode.Constants.ICONST_0, (short)1);

    if((i >= -1) && (i <= 5))
      opcode = (short)(de.fub.bytecode.Constants.ICONST_0 + i); // Even works for i == -1
    else
      throw new ClassGenException("ICONST can be used only for value between -1 and 5: " +
				  i);
    value = i;
  }
  
  public Number getValue() { return new Integer(value); }

  /** @return Type.INT
   */
  public Type getType(ConstantPoolGen cp) {
    return Type.INT;
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
    v.visitPushInstruction(this);
    v.visitStackProducer(this);
    v.visitTypedInstruction(this);
    v.visitConstantPushInstruction(this);
    v.visitICONST(this);
  }
}
