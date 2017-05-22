package de.fub.bytecode.generic;

/** 
 * IF_ACMPEQ - Branch if reference comparison succeeds
 *
 * <PRE>Stack: ..., value1, value2 -&gt; ...</PRE>
 *
 * @version $Id: IF_ACMPEQ.java,v 1.7 2001/07/03 11:52:14 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class IF_ACMPEQ extends IfInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IF_ACMPEQ() {}

  public IF_ACMPEQ(InstructionHandle target) {
    super(de.fub.bytecode.Constants.IF_ACMPEQ, target);
  }

  /**
   * @return negation of instruction
   */
  public IfInstruction negate() {
    return new IF_ACMPNE(target);
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
    v.visitIF_ACMPEQ(this);
  }
}
