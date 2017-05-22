package de.fub.bytecode.generic;
import de.fub.bytecode.Constants;
/**
 * Super class for the x2y family of instructions.
 *
 * @version $Id: ConversionInstruction.java,v 1.6 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class ConversionInstruction extends Instruction
  implements TypedInstruction, StackProducer, StackConsumer {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  ConversionInstruction() {}

  /**
   * @param opcode opcode of instruction
   */
  protected ConversionInstruction(short opcode) {
    super(opcode, (short)1);
  }

  /** @return type associated with the instruction
   */
  public Type getType(ConstantPoolGen cp) {
    switch(opcode) {
    case Constants.D2I: case Constants.F2I: case Constants.L2I:
      return Type.INT;   
    case Constants.D2F: case Constants.I2F: case Constants.L2F:
      return Type.FLOAT;
    case Constants.D2L: case Constants.F2L: case Constants.I2L:
      return Type.LONG;
    case Constants.F2D:  case Constants.I2D: case Constants.L2D:
        return Type.DOUBLE;
    case Constants.I2B:
      return Type.BYTE;
    case Constants.I2C:
      return Type.CHAR;
    case Constants.I2S:
      return Type.SHORT;
 
    default: // Never reached
      throw new ClassGenException("Unknown type " + opcode);
    }
  }
}

