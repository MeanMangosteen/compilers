package de.fub.bytecode.generic;

/** 
 * ALOAD - Load reference from local variable
 * <PRE>Stack: ... -&gt; ..., objectref</PRE>
 *
 * @version $Id: ALOAD.java,v 1.7 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ALOAD extends LoadInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  ALOAD() {
    super(de.fub.bytecode.Constants.ALOAD, de.fub.bytecode.Constants.ALOAD_0);
  }

  /** Load reference from local variable
   * @param n index of local variable
   */
  public ALOAD(int n) {
    super(de.fub.bytecode.Constants.ALOAD, de.fub.bytecode.Constants.ALOAD_0, n);
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
    v.visitALOAD(this);
  }
}
