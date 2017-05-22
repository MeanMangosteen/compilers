package de.fub.bytecode.generic;

/** 
 * F2I - Convert float to int
 * <PRE>Stack: ..., value -&gt; ..., result</PRE>
 *
 * @version $Id: F2I.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class F2I extends ConversionInstruction {
  /** Convert float to int
   */
  public F2I() {
    super(de.fub.bytecode.Constants.F2I);
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
    v.visitTypedInstruction(this);
    v.visitStackProducer(this);
    v.visitStackConsumer(this);
    v.visitConversionInstruction(this);
    v.visitF2I(this);
  }
}
