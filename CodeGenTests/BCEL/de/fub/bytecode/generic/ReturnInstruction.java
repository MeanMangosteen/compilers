package de.fub.bytecode.generic;
import de.fub.bytecode.Constants;
import de.fub.bytecode.ExceptionConstants;

/**
 * Super class for the xRETURN family of instructions.
 *
 * @version $Id: ReturnInstruction.java,v 1.6 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class ReturnInstruction extends Instruction
  implements ExceptionThrower, TypedInstruction, StackConsumer {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  ReturnInstruction() {}

  /**
   * @param opcode of instruction
   */
  protected ReturnInstruction(short opcode) {
    super(opcode, (short)1);
  }

  public Type getType() {
    switch(opcode) {
      case Constants.IRETURN: return Type.INT;
      case Constants.LRETURN: return Type.LONG;
      case Constants.FRETURN: return Type.FLOAT;
      case Constants.DRETURN: return Type.DOUBLE;
      case Constants.ARETURN: return Type.OBJECT;
      case Constants.RETURN:  return Type.VOID;
 
    default: // Never reached
      throw new ClassGenException("Unknown type " + opcode);
    }
  }

  public Class[] getExceptions() {
    return new Class[] { ExceptionConstants.ILLEGAL_MONITOR_STATE };
  }

  /** @return type associated with the instruction
   */
  public Type getType(ConstantPoolGen cp) {
    return getType();
  }
}

