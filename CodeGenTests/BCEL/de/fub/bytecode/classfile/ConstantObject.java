package de.fub.bytecode.classfile;

/** 
 * This interface denotes those constants that have a "natural" value,
 * such as ConstantLong, ConstantString, etc..
 *
 * @version $Id: ConstantObject.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     Constant
 */
public interface ConstantObject {
  /** @return object representing the constant, e.g., Long for ConstantLong
   */
  public abstract Object getConstantValue(ConstantPool cp);
}
