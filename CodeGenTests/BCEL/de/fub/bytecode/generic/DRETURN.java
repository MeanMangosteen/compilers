package de.fub.bytecode.generic;

/** 
 * DRETURN -  Return double from method
 * <PRE>Stack: ..., value.word1, value.word2 -&gt; &lt;empty&gt;</PRE>
 *
 * @version $Id: DRETURN.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class DRETURN extends ReturnInstruction {
  /** Return double from method
   */
  public DRETURN() {
    super(de.fub.bytecode.Constants.DRETURN);
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
    v.visitDRETURN(this);
  }
}
