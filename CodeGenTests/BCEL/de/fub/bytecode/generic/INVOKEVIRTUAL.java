package de.fub.bytecode.generic;
import de.fub.bytecode.Constants;
import de.fub.bytecode.ExceptionConstants;

/** 
 * INVOKEVIRTUAL - Invoke instance method; dispatch based on class
 *
 * <PRE>Stack: ..., objectref, [arg1, [arg2 ...]] -&gt; ...</PRE>
 *
 * @version $Id: INVOKEVIRTUAL.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class INVOKEVIRTUAL extends InvokeInstruction {
  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  INVOKEVIRTUAL() {}

  public INVOKEVIRTUAL(int index) {
    super(Constants.INVOKEVIRTUAL, index);
  }

  public Class[] getExceptions() {
    Class[] cs = new Class[4 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];

    System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0,
		     cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);

    cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length+3] = ExceptionConstants.UNSATISFIED_LINK_ERROR;
    cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length+2] = ExceptionConstants.ABSTRACT_METHOD_ERROR;
    cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length+1] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
    cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length]   = ExceptionConstants.NULL_POINTER_EXCEPTION;

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
    v.visitTypedInstruction(this);
    v.visitStackConsumer(this);
    v.visitStackProducer(this);
    v.visitLoadClass(this);
    v.visitCPInstruction(this);
    v.visitFieldOrMethod(this);
    v.visitInvokeInstruction(this);
    v.visitINVOKEVIRTUAL(this);
  }
}
