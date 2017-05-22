package de.fub.bytecode.generic;

/**
 * Super class for instructions dealing with array access such as IALOAD.
 *
 * @version $Id: ArrayInstruction.java,v 1.6 2001/07/11 12:55:59 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class ArrayInstruction extends Instruction
  implements ExceptionThrower, TypedInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  ArrayInstruction() {}

  /**
   * @param opcode of instruction
   */
  protected ArrayInstruction(short opcode) {
    super(opcode, (short)1);
  }

  public Class[] getExceptions() {
    return de.fub.bytecode.ExceptionConstants.EXCS_ARRAY_EXCEPTION;
  }

  /** @return type associated with the instruction
   */
  public Type getType(ConstantPoolGen cp) {
    switch(opcode) {
    case de.fub.bytecode.Constants.IALOAD: case de.fub.bytecode.Constants.IASTORE: 
      return Type.INT;
    case de.fub.bytecode.Constants.CALOAD: case de.fub.bytecode.Constants.CASTORE: 
      return Type.CHAR;
    case de.fub.bytecode.Constants.BALOAD: case de.fub.bytecode.Constants.BASTORE:
      return Type.BYTE;
    case de.fub.bytecode.Constants.SALOAD: case de.fub.bytecode.Constants.SASTORE:
      return Type.SHORT;
    case de.fub.bytecode.Constants.LALOAD: case de.fub.bytecode.Constants.LASTORE: 
      return Type.LONG;
    case de.fub.bytecode.Constants.DALOAD: case de.fub.bytecode.Constants.DASTORE: 
      return Type.DOUBLE;
    case de.fub.bytecode.Constants.FALOAD: case de.fub.bytecode.Constants.FASTORE: 
      return Type.FLOAT;
    case de.fub.bytecode.Constants.AALOAD: case de.fub.bytecode.Constants.AASTORE:
      return Type.OBJECT;

    default: throw new ClassGenException("Oops: unknown case in switch" + opcode);
    }
  }
}
