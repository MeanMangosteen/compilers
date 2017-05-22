package de.fub.bytecode.classfile;
import  de.fub.bytecode.Constants;
import java.io.*;

/**
 * This class represents the field info structure, i.e., the representation 
 * for a variable in the class. See JVM specification for details.
 *
 * @version $Id: Field.java,v 1.4 2001/05/31 08:35:58 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public final class Field extends FieldOrMethod {
  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   */
  public Field(Field c) {
    super(c);
  }

  /**
   * Construct object from file stream.
   * @param file Input stream
   */
  Field(DataInputStream file, ConstantPool constant_pool)
       throws IOException, ClassFormatError
  {
    super(file, constant_pool);
  }

  /**
   * @param access_flags Access rights of field
   * @param name_index Points to field name in constant pool
   * @param signature_index Points to encoded signature
   * @param attributes Collection of attributes
   * @param constant_pool Array of constants
   */
  public Field(int access_flags, int name_index, int signature_index,
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
    v.visitField(this);
  }

  /**
   * @return constant value associated with this field (may be null)
   */
  public final ConstantValue getConstantValue() {
    for(int i=0; i < attributes_count; i++)
      if(attributes[i].getTag() == Constants.ATTR_CONSTANT_VALUE)
	return (ConstantValue)attributes[i];

    return null;
  }

  /**
   * Return string representation close to declaration format,
   * `public static final short MAX = 100', e.g..
   *
   * @return String representation of field, including the signature.
   */
  public final String toString() {
    String name, signature, access; // Short cuts to constant pool

    // Get names from constant pool
    access    = Utility.accessToString(access_flags);
    access    = access.equals("")? "" : (access + " ");
    signature = Utility.signatureToString(getSignature());
    name      = getName();

    StringBuffer  buf = new StringBuffer(access + signature + " " + name);
    ConstantValue cv  = getConstantValue();

    if(cv != null)
      buf.append(" = " + cv);

    for(int i=0; i < attributes_count; i++) {
      Attribute a = attributes[i];

      if(!(a instanceof ConstantValue))
	buf.append(" [" + a.toString() + "]");
    }

    return buf.toString();
  }

  /**
   * @return deep copy of this field
   */
  public final Field copy(ConstantPool constant_pool) {
    return (Field)copy_(constant_pool);
  }
}
