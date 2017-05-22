package de.fub.bytecode.generic;

/**
 * LREM - Remainder of long
 * <PRE>Stack: ..., value1, value2 -&gt; result</PRE>
 *
 * @version $Id: LREM.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LREM extends ArithmeticInstruction implements ExceptionThrower {
  public LREM() {
    super(de.fub.bytecode.Constants.LREM);
  }

  public Class[] getExceptions() { return new Class[] { de.fub.bytecode.ExceptionConstants.ARITHMETIC_EXCEPTION }; }


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
    v.visitStackProducer(this);
    v.visitStackConsumer(this);
    v.visitArithmeticInstruction(this);
    v.visitLREM(this);
  }
}
