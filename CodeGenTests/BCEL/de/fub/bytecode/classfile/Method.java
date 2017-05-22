package de.fub.bytecode.classfile;
import de.fub.bytecode.Constants;
import java.io.*;

/**
 * This class represents the method info structure, i.e., the representation 
 * for a method in the class. See JVM specification for details.
 * A method has access flags, a name, a signature and a number of attributes.
 *
 * @version $Id: Method.java,v 1.5 2001/07/25 10:24:34 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public final class Method extends FieldOrMethod {
  /**
   * Empty constructor, all attributes have to be defined via `setXXX'
   * methods. Use at your own risk.
   */
  public Method() {}

  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   */
  public Method(Method c) {
    super(c);
  }

  /**
   * Construct object from file stream.
   * @param file Input stream
   * @throw IOException
   * @throw ClassFormatError
   */
  Method(DataInputStream file, ConstantPool constant_pool)
       throws IOException, ClassFormatError
  {
    super(file, constant_pool);
  }

  /**
   * @param access_flags Access rights of method
   * @param name_index Points to field name in constant pool
   * @param signature_index Points to encoded signature
   * @param attributes Collection of attributes
   * @param constant_pool Array of constants
   */
  public Method(int access_flags, int name_index, int signature_index,
		Attribute[] attributes, ConstantPool constant_pool)
  {
    super(access_flags, name_index, signature_index, attributes, constant_pool);
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitMethod(this);
  }

  /**
   * @return Code attribute of method, if any
   */   
  public final Code getCode() {
    for(int i=0; i < attributes_count; i++)
      if(attributes[i] instanceof Code)
	return (Code)attributes[i];

    return null;
  }

  /**
   * @return ExceptionTable attribute of method, if any, i.e., list all
   * exceptions the method may throw not exception handlers!
   */
  public final ExceptionTable getExceptionTable() {
    for(int i=0; i < attributes_count; i++)
      if(attributes[i] instanceof ExceptionTable)
	return (ExceptionTable)attributes[i];

    return null;
  }

  /** @return LocalVariableTable of code attribute if any, i.e. the call is forwarded
   * to the Code atribute.
   */
  public final LocalVariableTable getLocalVariableTable() {
    Code code = getCode();

    if(code != null)
      return code.getLocalVariableTable();
    else
      return null;
  }

  /** @return LineNumberTable of code attribute if any, i.e. the call is forwarded
   * to the Code atribute.
   */
  public final LineNumberTable getLineNumberTable() {
    Code code = getCode();

    if(code != null)
      return code.getLineNumberTable();
    else
      return null;
  }

  /**
   * Return string representation close to declaration format,
   * `public static void main(String[] args) throws IOException', e.g.
   *
   * @return String representation of the method.
   */
  public final String toString() {
    ConstantUtf8  c;
    ConstantValue cv;
    String        name, signature, access; // Short cuts to constant pool
    String        exceptions;
    StringBuffer  buf;
    Attribute[]   attr;

    access = Utility.accessToString(access_flags);

    // Get name and signature from constant pool
    c = (ConstantUtf8)constant_pool.getConstant(signature_index, 
						Constants.CONSTANT_Utf8);
    signature = c.getBytes();

    c = (ConstantUtf8)constant_pool.getConstant(name_index, Constants.CONSTANT_Utf8);
    name = c.getBytes();

    signature = Utility.methodSignatureToString(signature, name, access, true,
						getLocalVariableTable());
    buf = new StringBuffer(signature);

    for(int i=0; i < attributes_count; i++) {
      Attribute a = attributes[i];

      if(!((a instanceof Code) || (a instanceof ExceptionTable)))
	buf.append(" [" + a.toString() + "]");
    }

    ExceptionTable e = getExceptionTable();
    if(e != null) {
      String str = e.toString();
      if(!str.equals(""))
	buf.append("\n\t\tthrows " + str);
    }
 
    return buf.toString();
  }

  /**
   * @return deep copy of this method
   */
  public final Method copy(ConstantPool constant_pool) {
    return (Method)copy_(constant_pool);
  }
}
