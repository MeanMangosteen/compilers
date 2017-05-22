package de.fub.bytecode.generic;

/**
 * Super class for stack operations like DUP and POP.
 *
 * @version $Id: StackInstruction.java,v 1.5 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class StackInstruction extends Instruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  StackInstruction() {}

  /**
   * @param opcode instruction opcode
   */
  protected StackInstruction(short opcode) {
    super(opcode, (short)1);
  }

  /** @return Type.UNKNOWN
   */
  public Type getType(ConstantPoolGen cp) {
    return Type.UNKNOWN;
  }  
}

