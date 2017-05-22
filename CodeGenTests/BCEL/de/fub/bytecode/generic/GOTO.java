package de.fub.bytecode.generic;
import java.io.*;

/** 
 * GOTO - Branch always (to relative offset, not absolute address)
 *
 * @version $Id: GOTO.java,v 1.9 2001/07/16 09:22:26 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class GOTO extends GotoInstruction implements VariableLengthInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  GOTO() {}

  public GOTO(InstructionHandle target) {
    super(de.fub.bytecode.Constants.GOTO, target);
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    index = getTargetOffset();
    if(opcode == de.fub.bytecode.Constants.GOTO)
      super.dump(out);
    else { // GOTO_W
      index = getTargetOffset();
      out.writeByte(opcode);
      out.writeInt(index);
    }
  }

  /** Called in pass 2 of InstructionList.setPositions() in order to update
   * the branch target, that may shift due to variable length instructions.
   */
  protected int updatePosition(int offset, int max_offset) {
    int i = getTargetOffset(); // Depending on old position value

    position += offset; // Position may be shifted by preceding expansions

    if(Math.abs(i) >= (32767 - max_offset)) { // to large for short (estimate)
      opcode = de.fub.bytecode.Constants.GOTO_W;
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
    v.visitVariableLengthInstruction(this);
    v.visitUnconditionalBranch(this);
    v.visitBranchInstruction(this);
    v.visitGotoInstruction(this);
    v.visitGOTO(this);
  }
}
