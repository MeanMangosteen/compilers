package de.fub.bytecode.generic;

/** 
 * LASTORE -  Store into long array
 * <PRE>Stack: ..., arrayref, index, value.word1, value.word2 -&gt; ...</PRE>
 *
 * @version $Id: LASTORE.java,v 1.6 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LASTORE extends ArrayInstruction implements StackConsumer {
  /** Store long into array
   */
  public LASTORE() {
    super(de.fub.bytecode.Constants.LASTORE);
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
    v.visitExceptionThrower(this);
    v.visitTypedInstruction(this);
    v.visitArrayInstruction(this);
    v.visitLASTORE(this);
  }
}
