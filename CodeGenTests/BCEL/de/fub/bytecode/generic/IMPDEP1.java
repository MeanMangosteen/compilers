package de.fub.bytecode.generic;

/**
 * IMPDEP1 - Implementation dependent
 *
 * @version $Id: IMPDEP1.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class IMPDEP1 extends Instruction {
  public IMPDEP1() {
    super(de.fub.bytecode.Constants.IMPDEP1, (short)1);
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
    v.visitIMPDEP1(this);
  }
}
