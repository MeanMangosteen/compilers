package de.fub.bytecode.generic;

/** 
 * RETURN -  Return from void method
 * <PRE>Stack: ... -&gt; &lt;empty&gt;</PRE>
 *
 * @version $Id: RETURN.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class RETURN extends ReturnInstruction {
  public RETURN() {
    super(de.fub.bytecode.Constants.RETURN);
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
    v.visitTypedInstruction(this);
    v.visitStackConsumer(this);
    v.visitReturnInstruction(this);
    v.visitRETURN(this);
  }
}
