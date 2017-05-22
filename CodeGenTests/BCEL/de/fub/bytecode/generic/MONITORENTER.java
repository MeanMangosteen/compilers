package de.fub.bytecode.generic;

/** 
 * MONITORENTER - Enter monitor for object
 * <PRE>Stack: ..., objectref -&gt; ...</PRE>
 *
 * @version $Id: MONITORENTER.java,v 1.6 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class MONITORENTER extends Instruction
  implements ExceptionThrower, StackConsumer {
  public MONITORENTER() {
    super(de.fub.bytecode.Constants.MONITORENTER, (short)1);
  }

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
    v.visitStackConsumer(this);
    v.visitMONITORENTER(this);
  }
}
