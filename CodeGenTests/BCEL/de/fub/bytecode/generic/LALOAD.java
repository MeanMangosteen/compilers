package de.fub.bytecode.generic;

/** 
 * LALOAD - Load long from array
 * <PRE>Stack: ..., arrayref, index -&gt; ..., value1, value2</PRE>
 *
 * @version $Id: LALOAD.java,v 1.6 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class LALOAD extends ArrayInstruction implements StackProducer {
  /** Load long from array
   */
  public LALOAD() {
    super(de.fub.bytecode.Constants.LALOAD);
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
    v.visitExceptionThrower(this);
    v.visitTypedInstruction(this);
    v.visitArrayInstruction(this);
    v.visitLALOAD(this);
  }
}
