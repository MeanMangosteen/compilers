
package de.fub.bytecode.classfile;


import  de.fub.bytecode.Constants;
import  java.io.*;

/** 
 * This class is derived from the abstract 
 * <A HREF="de.fub.bytecode.classfile.Constant.html">Constant</A> class 
 * and represents a reference to an int object.
 *
 * @version $Id: ConstantInteger.java,v 1.4 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     Constant
 */
public final class ConstantInteger extends Constant implements ConstantObject {
  private int bytes;

  /** 
   * @param bytes Data
   */
  public ConstantInteger(int bytes)
  {    
    super(Constants.CONSTANT_Integer);
    this.bytes = bytes;
  }

  /**
   * Initialize from another object.
   */
  public ConstantInteger(ConstantInteger c) {
    this(c.getBytes());
  }

  /** 
   * Initialize instance from file data.
   *
   * @param file Input stream
   * @throw IOException
   */
  ConstantInteger(DataInputStream file) throws IOException
  {    
    this(file.readInt());
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitConstantInteger(this);
  }

  /**
   * Dump constant integer to file stream in binary format.
   *
   * @param file Output file stream
   * @throw IOException
   */ 
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeByte(tag);
    file.writeInt(bytes);
  }

  /**
   * @return data, i.e., 4 bytes.
   */  
  public final int getBytes() { return bytes; }

  /**
   * @param bytes.
   */
  public final void setBytes(int bytes) {
    this.bytes = bytes;
  }

  /**
   * @return String representation.
   */
  public final String toString() {
    return super.toString() + "(bytes = " + bytes + ")";
  }    

  /** @return Integer object
   */
  public Object getConstantValue(ConstantPool cp) {
    return new Integer(bytes);
  }
}
