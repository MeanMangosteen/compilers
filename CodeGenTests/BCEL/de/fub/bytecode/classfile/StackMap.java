package de.fub.bytecode.classfile;

import  de.fub.bytecode.Constants;
import  java.io.*;

/**
 * This class represents a stack map attribute used for
 * preverification of Java classes for the <a
 * href="http://java.sun.com/j2me/"> Java 2 Micro Edition</a>
 * (J2ME). This attribute is used by the <a
 * href="http://java.sun.com/products/cldc/">KVM</a> and contained
 * within the Code attribute of a method. See CLDC specification
 * §5.3.1.2
 *
 * @version $Id: StackMap.java,v 1.4 2001/08/15 14:47:50 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     Code
 * @see     StackMapEntry
 * @see     StackMapType
 */
public final class StackMap extends Attribute implements Node {
  private int             map_length;
  private StackMapEntry[] map; // Table of stack map entries

  /*
   * @param name_index Index of name
   * @param length Content length in bytes
   * @param map Table of stack map entries
   * @param constant_pool Array of constants
   */
  public StackMap(int name_index, int length,  StackMapEntry[] map,
		  ConstantPool constant_pool)
  {
    super(Constants.ATTR_STACK_MAP, name_index, length, constant_pool);

    setStackMap(map);
  }
   
  /**
   * Construct object from file stream.
   * @param name_index Index of name
   * @param length Content length in bytes
   * @param file Input stream
   * @throw IOException
   * @param constant_pool Array of constants
   */
  StackMap(int name_index, int length, DataInputStream file,
	   ConstantPool constant_pool) throws IOException
  {
    this(name_index, length, (StackMapEntry[])null, constant_pool);

    map_length = file.readUnsignedShort();
    map = new StackMapEntry[map_length];

    for(int i=0; i < map_length; i++)
      map[i] = new StackMapEntry(file, constant_pool);
  }

  /**
   * Dump line number table attribute to file stream in binary format.
   *
   * @param file Output file stream
   * @throw IOException
   */ 
  public final void dump(DataOutputStream file) throws IOException
  {
    super.dump(file);
    file.writeShort(map_length);
    for(int i=0; i < map_length; i++)
      map[i].dump(file);
  }    
   
  /**
   * @return Array of stack map entries
   */  
  public final StackMapEntry[] getStackMap() { return map; }    

  /**
   * @param map Array of stack map entries
   */
  public final void setStackMap(StackMapEntry[] map) {
    this.map = map;

    map_length = (map == null)? 0 : map.length;
  }

  /**
   * @return String representation.
   */ 
  public final String toString() {
    StringBuffer buf = new StringBuffer("StackMap(");

    for(int i=0; i < map_length; i++) {
      buf.append(map[i].toString());

      if(i < map_length - 1)
	buf.append(", ");
    }

    buf.append(')');
	
    return buf.toString();    
  }

  /**
   * @return deep copy of this attribute
   */
  public Attribute copy(ConstantPool constant_pool) {
    StackMap c = (StackMap)clone();

    c.map = new StackMapEntry[map_length];
    for(int i=0; i < map_length; i++)
      c.map[i] = map[i].copy();

    c.constant_pool = constant_pool;
    return c;
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
   public void accept(Visitor v) {
     v.visitStackMap(this);
   }

  public final int getMapLength() { return map_length; }
}
