package de.fub.bytecode.generic;

/** 
 * DUP - Duplicate top operand stack word
 * <PRE>Stack: ..., word -&gt; ..., word, word</PRE>
 *
 * @version $Id: DUP.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class DUP extends StackInstruction implements PushInstruction {
  public DUP() {
    super(de.fub.bytecode.Constants.DUP);
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
    v.visitStackInstruction(this);
    v.visitDUP(this);
  }
}
