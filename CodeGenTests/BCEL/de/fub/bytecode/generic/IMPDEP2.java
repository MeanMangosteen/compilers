package de.fub.bytecode.generic;

/**
 * IMPDEP2 - Implementation dependent
 *
 * @version $Id: IMPDEP2.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class IMPDEP2 extends Instruction {
  public IMPDEP2() {
    super(de.fub.bytecode.Constants.IMPDEP2, (short)1);
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
    v.visitIMPDEP2(this);
  }
}
