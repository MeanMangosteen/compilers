package de.fub.bytecode.generic;

/** 
 * ACONST_NULL - Push null reference
 * <PRE>Stack: ... -&gt; ..., null</PRE>
 *
 * @version $Id: ACONST_NULL.java,v 1.6 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ACONST_NULL extends Instruction
  implements PushInstruction, TypedInstruction {
  /** 
   * Push null reference
   */
  public ACONST_NULL() {
    super(de.fub.bytecode.Constants.ACONST_NULL, (short)1);
  }

  /** @return Type.NULL
   */
  public Type getType(ConstantPoolGen cp) {
    return Type.NULL;
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
    v.visitACONST_NULL(this);
  }
}
