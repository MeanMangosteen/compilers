package de.fub.bytecode.generic;

/**
 * Super class for the IFxxx family of instructions.
 *
 * @version $Id: IfInstruction.java,v 1.4 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class IfInstruction extends BranchInstruction implements StackConsumer {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  IfInstruction() {}

  /**
   * @param instruction Target instruction to branch to
   */
  protected IfInstruction(short opcode, InstructionHandle target) {
    super(opcode, target);
  }

  /**
   * @return negation of instruction, e.g. IFEQ.negate() == IFNE
   */
  public abstract IfInstruction negate();
}

