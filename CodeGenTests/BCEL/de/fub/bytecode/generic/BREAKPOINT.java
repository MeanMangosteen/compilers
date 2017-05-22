package de.fub.bytecode.generic;

/**
 * BREAKPOINT
 *
 * @version $Id: BREAKPOINT.java,v 1.1 2001/06/07 10:50:16 ehaase Exp $
 * @author Enver Haase
 */
public class BREAKPOINT extends Instruction {
  public BREAKPOINT() {
    super(de.fub.bytecode.Constants.BREAKPOINT, (short)1);
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
    v.visitBREAKPOINT(this);
  }
}
