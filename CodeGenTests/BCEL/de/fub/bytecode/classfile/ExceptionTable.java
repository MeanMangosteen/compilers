package de.fub.bytecode.classfile;

import  de.fub.bytecode.Constants;
import  java.io.*;

/** 
 * This class represents the table of exceptions that are thrown by a
 * method. This attribute may be used once per method.  The name of
 * this class is <em>ExceptionTable</em> for historical reasons; The
 * Java Virtual Machine Specification, Second Edition defines this
 * attribute using the name <em>Exceptions</em> (which is inconsistent
 * with the other classes).
 *
 * @version $Id: ExceptionTable.java,v 1.6 2001/06/25 07:58:16 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     Code
 */
public final class ExceptionTable extends Attribute {
  private int   number_of_exceptions;  // Table of indices into
  private int[] exception_index_table; // constant pool

  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use copy() for a physical copy.
   */
  public ExceptionTable(ExceptionTable c) {
    this(c.getNameIndex(), c.getLength(), c.getExceptionIndexTable(),
	 c.getConstantPool());
  }

  /**
   * @param name_index Index in constant pool
   * @param length Content length in bytes
   * @param exception_index_table Table of indices in constant pool
   * @param constant_pool Array of constants
   */
  public ExceptionTable(int        name_index, int length, 
			int[]      exception_index_table,
			ConstantPool constant_pool)
  {
    super(Constants.ATTR_EXCEPTIONS, name_index, length, constant_pool);
    setExceptionIndexTable(exception_index_table);
  }

  /**
   * Construct object from file stream.
   * @param name_index Index in constant pool
   * @param length Content length in bytes
   * @param file Input stream
   * @param constant_pool Array of constants
   * @throw IOException
   */
  ExceptionTable(int name_index, int length, DataInputStream file,
		 ConstantPool constant_pool) throws IOException
  {
    this(name_index, length, (int[])null, constant_pool);

    number_of_exceptions  = file.readUnsignedShort();
    exception_index_table = new int[number_of_exceptions];

    for(int i=0; i < number_of_exceptions; i++)
      exception_index_table[i] = file.readUnsignedShort();
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitExceptionTable(this);
  }

  /**
   * Dump exceptions attribute to file stream in binary format.
   *
   * @param file Output file stream
   * @throw IOException
   */ 
  public final void dump(DataOutputStream file) throws IOException
  {
    super.dump(file);
    file.writeShort(number_of_exceptions);
    for(int i=0; i < number_of_exceptions; i++)
      file.writeShort(exception_index_table[i]);
  }

  /**
   * @return Array of indices into constant pool of thrown exceptions.
   */  
  public final int[] getExceptionIndexTable() {return exception_index_table;}
  /**
   * @return Length of exception table.
   */  
  public final int getNumberOfExceptions() { return number_of_exceptions; }

  /**
   * @return class names of thrown exceptions
   */
  public final String[] getExceptionNames() {
    String[] names = new String[number_of_exceptions];
    for(int i=0; i < number_of_exceptions; i++)
      names[i] = constant_pool.getConstantString(exception_index_table[i],
						 Constants.CONSTANT_Class).
	replace('/', '.');
    return names;
  }

  /**
   * @param exception_index_table.
   * Also redefines number_of_exceptions according to table length.
   */
  public final void setExceptionIndexTable(int[] exception_index_table) {
    this.exception_index_table = exception_index_table;
    number_of_exceptions       = (exception_index_table == null)? 0 :
      exception_index_table.length;
  }
  /**
   * @return String representation, i.e., a list of thrown exceptions.
   */ 
  public final String toString() {
    StringBuffer buf = new StringBuffer("");
    String       str;

    for(int i=0; i < number_of_exceptions; i++) {
      str = constant_pool.getConstantString(exception_index_table[i],
					    Constants.CONSTANT_Class);
      buf.append(Utility.compactClassName(str, false));

      if(i < number_of_exceptions - 1)
	buf.append(", ");
    }

    return buf.toString();    
  }

  /**
   * @return deep copy of this attribute
   */
  public Attribute copy(ConstantPool constant_pool) {
    ExceptionTable c = (ExceptionTable)clone();
    c.exception_index_table = (int[])exception_index_table.clone();
    c.constant_pool = constant_pool;
    return c;
  }
}
