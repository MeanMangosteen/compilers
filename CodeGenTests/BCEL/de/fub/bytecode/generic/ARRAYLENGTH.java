package de.fub.bytecode.generic;

/** 
 * ARRAYLENGTH -  Get length of array
 * <PRE>Stack: ..., arrayref -&gt; ..., length</PRE>
 *
 * @version $Id: ARRAYLENGTH.java,v 1.7 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ARRAYLENGTH extends Instruction
  implements ExceptionThrower, StackProducer {
  /** Get length of array
   */
  public ARRAYLENGTH() {
    super(de.fub.bytecode.Constants.ARRAYLENGTH, (short)1);
  }

  /** @return exceptions this instruction may cause
   */
  public Class[] getExceptions() {
    return new Class[] { de.fub.bytecode.ExceptionConstants.NULL_POINTER_EXCEPTION };
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
    v.visitExceptionThrower(this);
    v.visitStackProducer(this);
    v.visitARRAYLENGTH(this);
  }
}
