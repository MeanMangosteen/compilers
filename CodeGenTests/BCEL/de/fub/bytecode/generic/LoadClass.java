package de.fub.bytecode.generic;

/**
 * Denotes that an instruction may start the process of loading and resolving 
 * the referenced class in the Virtual Machine.
 *
 * @version $Id: LoadClass.java,v 1.7 2001/07/11 13:19:04 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface LoadClass {
  /**
   * Returns the ObjectType of the referenced class or interface
   * that may be loaded and resolved.
   * @return object type that may be loaded or null if a primitive is
   * referenced
   */
  public ObjectType getLoadClassType(ConstantPoolGen cpg);
	
  /**
   * Returns the type associated with this instruction.
   * LoadClass instances are always typed, but this type
   * does not always refer to the type of the class or interface
   * that it possibly forces to load. For example, GETFIELD would
   * return the type of the field and not the type of the class
   * where the field is defined.
   * If no class is forced to be loaded, <B>null</B> is returned.
   * An example for this is an ANEWARRAY instruction that creates
   * an int[][].
   * @see #getLoadClassType(ConstantPoolGen)
   */
  public Type getType(ConstantPoolGen cpg);
}
