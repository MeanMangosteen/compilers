package de.fub.bytecode.generic;
import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * JSR_W - Jump to subroutine
 *
 * @version $Id: JSR_W.java,v 1.9 2001/07/03 11:52:14 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class JSR_W extends JsrInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  JSR_W() {}

  public JSR_W(InstructionHandle target) {
    super(de.fub.bytecode.Constants.JSR_W, target);
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
    index = bytes.readInt();
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
    v.visitStackProducer(this);
    v.visitBranchInstruction(this);
    v.visitJsrInstruction(this);
    v.visitJSR_W(this);
  }
}
