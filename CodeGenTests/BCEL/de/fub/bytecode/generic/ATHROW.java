package de.fub.bytecode.generic;

/** 
 * ATHROW -  Throw exception
 * <PRE>Stack: ..., objectref -&gt; objectref</PRE>
 *
 * @version $Id: ATHROW.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ATHROW extends Instruction implements UnconditionalBranch, ExceptionThrower {
  /** 
   *  Throw exception
   */
  public ATHROW() {
    super(de.fub.bytecode.Constants.ATHROW, (short)1);
  }

  /** @return exceptions this instruction may cause
   */
  public Class[] getExceptions() {
    return new Class[] { de.fub.bytecode.ExceptionConstants.THROWABLE };
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
    v.visitUnconditionalBranch(this);
    v.visitExceptionThrower(this);
    v.visitATHROW(this);
  }
}
