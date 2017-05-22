package de.fub.bytecode.generic;

/**
 * POP2 - Pop two top operand stack words
 *
 * <PRE>Stack: ..., word2, word1 -&gt; ...</PRE>
 *
 * @version $Id: POP2.java,v 1.6 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class POP2 extends StackInstruction implements PopInstruction {
  public POP2() {
    super(de.fub.bytecode.Constants.POP2);
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
    v.visitStackConsumer(this);
    v.visitPopInstruction(this);
    v.visitStackInstruction(this);
    v.visitPOP2(this);
  }
}
