package de.fub.bytecode.classfile;

import de.fub.bytecode.Constants;
import java.io.*;

/** 
 * This class is derived from the abstract 
 * <A HREF="de.fub.bytecode.classfile.Constant.html">Constant</A> class 
 * and represents a reference to a float object.
 *
 * @version $Id: ConstantFloat.java,v 1.4 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     Constant
 */
public final class ConstantFloat extends Constant implements ConstantObject {
  private float bytes;

  /** 
   * @param bytes Data
   */
  public ConstantFloat(float bytes)
  {    
    super(Constants.CONSTANT_Float);
    this.bytes = bytes;
  }    
  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   */
  public ConstantFloat(ConstantFloat c) {
    this(c.getBytes());
  }    
  /** 
   * Initialize instance from file data.
   *
   * @param file Input stream
   * @throw IOException
   */
  ConstantFloat(DataInputStream file) throws IOException
  {    
    this(file.readFloat());
  }    
  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitConstantFloat(this);
  }    
  /**
   * Dump constant float to file stream in binary format.
   *
   * @param file Output file stream
   * @throw IOException
   */ 
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeByte(tag);
    file.writeFloat(bytes);
  }    
  /**
   * @return data, i.e., 4 bytes.
   */  
  public final float getBytes() { return bytes; }    
  /**
   * @param bytes.
   */
  public final void setBytes(float bytes) {
    this.bytes = bytes;
  }

  /**
   * @return String representation.
   */
  public final String toString() {
    return super.toString() + "(bytes = " + bytes + ")";
  }    

  /** @return Float object
   */
  public Object getConstantValue(ConstantPool cp) {
    return new Float(bytes);
  }
}
