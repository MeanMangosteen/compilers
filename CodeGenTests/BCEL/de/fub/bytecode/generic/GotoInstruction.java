package de.fub.bytecode.generic;

/** 
 * Super class for GOTO
 *
 * @version $Id: GotoInstruction.java,v 1.3 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class GotoInstruction extends BranchInstruction
  implements UnconditionalBranch
{
  GotoInstruction(short opcode, InstructionHandle target) {
    super(opcode, target);
  }

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  GotoInstruction(){}
}
