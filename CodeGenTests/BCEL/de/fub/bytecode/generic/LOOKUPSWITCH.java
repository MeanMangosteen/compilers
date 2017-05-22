package de.fub.bytecode.generic;
import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * LOOKUPSWITCH - Switch with unordered set of values
 *
 * @version $Id: LOOKUPSWITCH.java,v 1.6 2001/07/03 11:52:14 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see SWITCH
 */
public class LOOKUPSWITCH extends Select {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  LOOKUPSWITCH() {}

  public LOOKUPSWITCH(int[] match, InstructionHandle[] targets,
		      InstructionHandle target) {
    super(de.fub.bytecode.Constants.LOOKUPSWITCH, match, targets, target);
    
    length = (short)(9 + match_length * 8); /* alignment remainder assumed
					     * 0 here, until dump time. */
    fixed_length = length;
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    super.dump(out);
    out.writeInt(match_length);       // npairs

    for(int i=0; i < match_length; i++) {
      out.writeInt(match[i]);         // match-offset pairs
      out.writeInt(indices[i] = getTargetOffset(targets[i]));
    }
  }

  /**
   * Read needed data (e.g. index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
    super.initFromFile(bytes, wide); // reads padding

    match_length = bytes.readInt();
    fixed_length = (short)(9 + match_length * 8);
    length       = (short)(fixed_length + padding);
	  
    match   = new int[match_length];
    indices = new int[match_length];
    targets = new InstructionHandle[match_length];

    for(int i=0; i < match_length; i++) {
      match[i]   = bytes.readInt();
      indices[i] = bytes.readInt();
    }
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
    v.visitStackProducer(this);
    v.visitBranchInstruction(this);
    v.visitSelect(this);
    v.visitLOOKUPSWITCH(this);
  }
}
