package de.fub.bytecode.classfile;

import  de.fub.bytecode.Constants;
import  java.io.*;

/**
 * This class represents the type of a local variable or item on stack
 * used in the StackMap entries.
 *
 * @version $Id: StackMapType.java,v 1.2 2001/08/15 14:47:50 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     StackMapEntry
 * @see     StackMap
 * @see     Constants
 */
public final class StackMapType implements Cloneable {
  private byte         type;
  private int          index = -1; // Index to CONSTANT_Class or offset
  private ConstantPool constant_pool;

  /**
   * Construct object from file stream.
   * @param file Input stream
   * @throw IOException
   */
  StackMapType(DataInputStream file, ConstantPool constant_pool) throws IOException
  {
    this(file.readByte(), -1, constant_pool);

    if(hasIndex())
      setIndex(file.readShort());

    setConstantPool(constant_pool);
  }

  /**
   * @param type type tag as defined in the Constants interface
   * @param index index to constant pool, or byte code offset
   */
  public StackMapType(byte type, int index, ConstantPool constant_pool) {
    setType(type);
    setIndex(index);
    setConstantPool(constant_pool);
  }

  public void setType(byte t) {
    if((t < Constants.ITEM_Bogus) || (t > Constants.ITEM_NewObject))
      throw new RuntimeException("Illegal type for StackMapType: " + t);
    type = t;
  }

  public byte getType()       { return type; }
  public void setIndex(int t) { index = t; }

  /** @return index to constant pool if type == ITEM_Object, or offset
   * in byte code, if type == ITEM_NewObject, and -1 otherwise
   */
  public int  getIndex()      { return index; }
  
  /**
   * Dump type entries to file.
   *
   * @param file Output file stream
   * @throw IOException
   */ 
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeByte(type);
    if(hasIndex())
      file.writeShort(getIndex());
  }    

  /** @return true, if type is either ITEM_Object or ITEM_NewObject
   */
  public final boolean hasIndex() {
    return ((type == Constants.ITEM_Object) ||
	    (type == Constants.ITEM_NewObject));
  }

  private String printIndex() {
    if(type == Constants.ITEM_Object)
      return ", class=" + constant_pool.constantToString(index, Constants.CONSTANT_Class);
    else if(type == Constants.ITEM_NewObject)
      return ", offset=" + index;
    else
      return "";
  }

  /**
   * @return String representation
   */ 
  public final String toString() {
    return "(type=" + Constants.ITEM_NAMES[type] + printIndex() + ")";
  }    

  /**
   * @return deep copy of this object
   */
  public StackMapType copy() {
    try {
      return (StackMapType)clone();
    } catch(CloneNotSupportedException e) {}

    return null;
  }

  /**
   * @return Constant pool used by this object.
   */   
  public final ConstantPool getConstantPool() { return constant_pool; }

  /**
   * @param constant_pool Constant pool to be used for this object.
   */   
  public final void setConstantPool(ConstantPool constant_pool) {
    this.constant_pool = constant_pool;
  }
}
