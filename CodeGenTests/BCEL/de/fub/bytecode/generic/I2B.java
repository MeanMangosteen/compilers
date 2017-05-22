package de.fub.bytecode.generic;

/** 
 * I2B - Convert int to byte
 * <PRE>Stack: ..., value -&gt; ..., result</PRE>
 *
 * @version $Id: I2B.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class I2B extends ConversionInstruction {
  /** Convert int to byte
   */
  public I2B() {
    super(de.fub.bytecode.Constants.I2B);
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
    v.visitI2B(this);
  }
}
