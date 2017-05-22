package de.fub.bytecode.generic;

/** 
 * ILOAD - Load int from local variable onto stack
 * <PRE>Stack: ... -&gt; ..., result</PRE>
 *
 * @version $Id: ILOAD.java,v 1.7 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ILOAD extends LoadInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  ILOAD() {
    super(de.fub.bytecode.Constants.ILOAD, de.fub.bytecode.Constants.ILOAD_0);
  }

  /** Load int from local variable
   * @param n index of local variable
   */
  public ILOAD(int n) {
    super(de.fub.bytecode.Constants.ILOAD, de.fub.bytecode.Constants.ILOAD_0, n);
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
    v.visitILOAD(this);
  }
}
