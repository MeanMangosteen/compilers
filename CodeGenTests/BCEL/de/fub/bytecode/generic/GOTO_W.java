package de.fub.bytecode.generic;
import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * GOTO_W - Branch always (to relative offset, not absolute address)
 *
 * @version $Id: GOTO_W.java,v 1.9 2001/07/03 11:52:14 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class GOTO_W extends GotoInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  GOTO_W() {}

  public GOTO_W(InstructionHandle target) {
    super(de.fub.bytecode.Constants.GOTO_W, target);
    length = 5;
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    index = getTargetOffset();
    out.writeByte(opcode);
    out.writeInt(index);
  }

  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
    index  = bytes.readInt();
    length = 5;
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
    v.visitUnconditionalBranch(this);
    v.visitBranchInstruction(this);
    v.visitGotoInstruction(this);
    v.visitGOTO_W(this);
  }
}
