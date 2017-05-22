package de.fub.bytecode.generic;

/** 
 * LLOAD - Load long from local variable
 *<PRE>Stack ... -&GT; ..., result.word1, result.word2</PRE>
 *
 * @version $Id: LLOAD.java,v 1.7 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LLOAD extends LoadInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LLOAD() {
    super(de.fub.bytecode.Constants.LLOAD, de.fub.bytecode.Constants.LLOAD_0);
  }

  public LLOAD(int n) {
    super(de.fub.bytecode.Constants.LLOAD, de.fub.bytecode.Constants.LLOAD_0, n);
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
    v.visitLLOAD(this);
  }
}
