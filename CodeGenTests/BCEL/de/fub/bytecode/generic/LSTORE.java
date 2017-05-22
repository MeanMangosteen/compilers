package de.fub.bytecode.generic;

/** 
 * LSTORE - Store long into local variable
 * <PRE>Stack: ..., value.word1, value.word2 -&gt; ... </PRE>
 *
 * @version $Id: LSTORE.java,v 1.8 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LSTORE extends StoreInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LSTORE() {
    super(de.fub.bytecode.Constants.LSTORE, de.fub.bytecode.Constants.LSTORE_0);
  }

  public LSTORE(int n) {
    super(de.fub.bytecode.Constants.LSTORE, de.fub.bytecode.Constants.LSTORE_0, n);
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
    super.accept(v);
    v.visitLSTORE(this);
  }
}
