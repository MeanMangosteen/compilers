package de.fub.bytecode.generic;

import de.fub.bytecode.Constants;
import de.fub.bytecode.ExceptionConstants;

/** 
 * GETFIELD - Fetch field from object
 * <PRE>Stack: ..., objectref -&gt; ..., value</PRE>
 * OR
 * <PRE>Stack: ..., objectref -&gt; ..., value.word1, value.word2</PRE>
 *
 * @version $Id: GETFIELD.java,v 1.7 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class GETFIELD extends FieldInstruction
  implements ExceptionThrower, StackConsumer, StackProducer {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  GETFIELD() {}

  public GETFIELD(int index) {
    super(Constants.GETFIELD, index);
  }

  public int produceStack(ConstantPoolGen cpg) { return getFieldSize(cpg); }

  public Class[] getExceptions() {
    Class[] cs = new Class[2 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];

    System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0,
		     cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);

    cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length+1] =
      ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
    cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] =
      ExceptionConstants.NULL_POINTER_EXCEPTION;

    return cs;
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
    v.visitExceptionThrower(this);
    v.visitStackConsumer(this);
    v.visitStackProducer(this);
    v.visitTypedInstruction(this);
    v.visitLoadClass(this);
    v.visitCPInstruction(this);
    v.visitFieldOrMethod(this);
    v.visitFieldInstruction(this);
    v.visitGETFIELD(this);
  }
}
