package de.fub.bytecode.generic;

/** 
 * IFNONNULL - Branch if reference is not null
 *
 * <PRE>Stack: ..., reference -&gt; ...</PRE>
 *
 * @version $Id: IFNONNULL.java,v 1.7 2001/07/16 09:22:26 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class IFNONNULL extends IfInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IFNONNULL() {}

  public IFNONNULL(InstructionHandle target) {
    super(de.fub.bytecode.Constants.IFNONNULL, target);
  }

  /**
   * @return negation of instruction
   */
  public IfInstruction negate() {
    return new IFNULL(target);
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
    v.visitIFNONNULL(this);
  }
}
