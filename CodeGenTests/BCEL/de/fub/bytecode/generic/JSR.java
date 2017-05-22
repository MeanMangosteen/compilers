package de.fub.bytecode.generic;
import java.io.*;

/** 
 * JSR - Jump to subroutine
 *
 * @version $Id: JSR.java,v 1.9 2001/07/03 11:52:14 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class JSR extends JsrInstruction implements VariableLengthInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  JSR() {}

  public JSR(InstructionHandle target) {
    super(de.fub.bytecode.Constants.JSR, target);
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    index = getTargetOffset();
    if(opcode == de.fub.bytecode.Constants.JSR)
      super.dump(out);
    else { // JSR_W
      index = getTargetOffset();
      out.writeByte(opcode);
      out.writeInt(index);
    }
  }

  protected int updatePosition(int offset, int max_offset) {
    int i = getTargetOffset(); // Depending on old position value

    position += offset; // Position may be shifted by preceding expansions

    if(Math.abs(i) >= (32767 - max_offset)) { // to large for short (estimate)
      opcode  = de.fub.bytecode.Constants.JSR_W;
      length = 5;
      return 2; // 5 - 3
    }

    return 0;
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
    v.visitVariableLengthInstruction(this);
    v.visitBranchInstruction(this);
    v.visitJsrInstruction(this);
    v.visitJSR(this);
  }
}
