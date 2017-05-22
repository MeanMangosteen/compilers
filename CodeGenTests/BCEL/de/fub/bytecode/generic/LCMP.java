package de.fub.bytecode.generic;

/**
 * LCMP - Compare longs:
 * <PRE>Stack: ..., value1.word1, value1.word2, value2.word1, value2.word2 -&gt;</PRE>
 *        ..., result <= -1, 0, 1>
 *
 * @version $Id: LCMP.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LCMP extends Instruction {
  public LCMP() {
    super(de.fub.bytecode.Constants.LCMP, (short)1);
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
    v.visitLCMP(this);
  }
}
