package de.fub.bytecode.generic;

/** 
 * DUP2_X1 - Duplicate two top operand stack words and put three down
 * <PRE>Stack: ..., word3, word2, word1 -&gt; ..., word2, word1, word3, word2, word1</PRE>
 *
 * @version $Id: DUP2_X1.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class DUP2_X1 extends StackInstruction {
  public DUP2_X1() {
    super(de.fub.bytecode.Constants.DUP2_X1);
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
    v.visitStackInstruction(this);
    v.visitDUP2_X1(this);
  }
}
