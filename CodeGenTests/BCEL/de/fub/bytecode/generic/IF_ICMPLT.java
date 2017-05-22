package de.fub.bytecode.generic;

/** 
 * IF_ICMPLT - Branch if int comparison succeeds
 *
 * <PRE>Stack: ..., value1, value2 -&gt; ...</PRE>
 *
 * @version $Id: IF_ICMPLT.java,v 1.6 2001/07/03 11:52:14 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class IF_ICMPLT extends IfInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IF_ICMPLT() {}

  public IF_ICMPLT(InstructionHandle target) {
    super(de.fub.bytecode.Constants.IF_ICMPLT, target);
  }

  /**
   * @return negation of instruction
   */
  public IfInstruction negate() {
    return new IF_ICMPGE(target);
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
    v.visitStackConsumer(this);
    v.visitBranchInstruction(this);
    v.visitIfInstruction(this);
    v.visitIF_ICMPLT(this);
  }
}
