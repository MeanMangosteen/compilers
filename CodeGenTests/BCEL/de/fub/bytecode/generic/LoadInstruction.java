package de.fub.bytecode.generic;

/**
 * Denotes an unparameterized instruction to load a value from a local
 * variable, e.g. ILOAD.
 *
 * @version $Id: LoadInstruction.java,v 1.3 2001/07/03 08:20:18 dahm Exp $
 * @author <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class LoadInstruction extends LocalVariableInstruction
  implements PushInstruction
{
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   * tag and length are defined in readInstruction and initFromFile, respectively.
   */
  LoadInstruction(short canon_tag, short c_tag) {
    super(canon_tag, c_tag);
  }

  /**
   * @param opcode Instruction opcode
   * @param c_tag Instruction number for compact version, ALOAD_0, e.g.
   * @param n local variable index (unsigned short)
   */
  protected LoadInstruction(short opcode, short c_tag, int n) {
    super(opcode, c_tag, n);
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
    v.visitStackProducer(this);
    v.visitPushInstruction(this);
    v.visitTypedInstruction(this);
    v.visitLocalVariableInstruction(this);
    v.visitLoadInstruction(this);
  }
}

