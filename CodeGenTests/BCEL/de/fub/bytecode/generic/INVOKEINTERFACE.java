package de.fub.bytecode.generic;
import de.fub.bytecode.classfile.ConstantPool;
import de.fub.bytecode.Constants;
import de.fub.bytecode.ExceptionConstants;

import java.io.*;
import de.fub.bytecode.util.ByteSequence;

/** 
 * INVOKEINTERFACE - Invoke interface method
 * <PRE>Stack: ..., objectref, [arg1, [arg2 ...]] -&gt; ...</PRE>
 *
 * @version $Id: INVOKEINTERFACE.java,v 1.8 2001/08/16 19:52:45 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public final class INVOKEINTERFACE extends InvokeInstruction {
  private int nargs; // Number of arguments on stack (number of stack slots), called "count" in vmspec2

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  INVOKEINTERFACE() {}

  public INVOKEINTERFACE(int index, int nargs) {
    super(Constants.INVOKEINTERFACE, index);
    length = 5;

    if(nargs < 1)
      throw new ClassGenException("Number of arguments must be > 0 " + nargs);

    this.nargs = nargs;
  }

  /**
   * Dump instruction as byte code to stream out.
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode);
    out.writeShort(index);
    out.writeByte(nargs);
    out.writeByte(0);
  }

  /**
   * The Java Virtual Machine Specification, First Edition was a little
   * bit unprecise about the naming. In the Java Virtual Machine Specification,
   * Second Edition, the value returned here is called &quot;count&quot;.
   *
   * @deprecated Use getCount().
   */
  public int getNoArguments() { return nargs; }

  /**
   * The <B>count</B> argument according to the Java Language Specification,
   * Second Edition.
   */
  public int getCount() { return nargs; }

  /**
   * Read needed data (i.e., index) from file.
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
       throws IOException
  {
    super.initFromFile(bytes, wide);

    length = 5;
    nargs = bytes.readUnsignedByte();
    bytes.readByte(); // Skip 0 byte
  }

  /**
   * @return mnemonic for instruction with symbolic references resolved
   */
  public String toString(ConstantPool cp) {
    return super.toString(cp) + " " + nargs;
  }

  public int consumeStack(ConstantPoolGen cpg) { // nargs is given in byte-code
    return nargs;  // nargs includes this reference
  }

  public Class[] getExceptions() {
    Class[] cs = new Class[4 + ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length];

    System.arraycopy(ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION, 0,
		     cs, 0, ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length);

    cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length+3] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
    cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length+2] = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
    cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length+1] = ExceptionConstants.ABSTRACT_METHOD_ERROR;
    cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length]   = ExceptionConstants.UNSATISFIED_LINK_ERROR;

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
    v.visitINVOKEINTERFACE(this);
  }
}
