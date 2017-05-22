package de.fub.bytecode.generic;
import de.fub.bytecode.Constants;
import de.fub.bytecode.Repository;
import de.fub.bytecode.classfile.JavaClass;

/** 
 * Denotes reference such as java.lang.String.
 *
 * @version $Id: ObjectType.java,v 1.5 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public final class ObjectType extends ReferenceType {
  private String class_name; // Class name of type

  /**
   * @param class_name fully qualified class name, e.g. java.lang.String
   */ 
  public ObjectType(String class_name) {
    super(Constants.T_REFERENCE, "L" + class_name.replace('.', '/') + ";");
    this.class_name = class_name.replace('/', '.');
  }

  /** @return name of referenced class
   */
  public String getClassName() { return class_name; }

  /** @return a hash code value for the object.
   */
  public int hashCode()  { return class_name.hashCode(); }

  /** @return true if both type objects refer to the same class.
   */
  public boolean equals(Object type) {
    return (type instanceof ObjectType)?
      ((ObjectType)type).class_name.equals(class_name) : false;
  }

  /**
   * If "this" doesn't reference a class, it references an interface
   * or a non-existant entity.
   */
  public boolean referencesClass(){
    JavaClass jc = Repository.lookupClass(class_name);
    if (jc == null)
      return false;
    else
      return jc.isClass();
  }
  
  /**
   * If "this" doesn't reference an interface, it references a class
   * or a non-existant entity.
   */
  public boolean referencesInterface(){
    JavaClass jc = Repository.lookupClass(class_name);
    if (jc == null)
      return false;
    else
      return !jc.isClass();
  }

  public boolean subclassOf(ObjectType superclass){
    if (this.referencesInterface() || superclass.referencesInterface())
      return false;

    return Repository.instanceOf(this.class_name, superclass.class_name);
  }

  /**
   * Java Virtual Machine Specification edition 2, § 5.4.4 Access Control
   */
  public boolean accessibleTo(ObjectType accessor) {
    JavaClass jc = Repository.lookupClass(class_name);

    if(jc.isPublic()) {
      return true;
    } else {
      JavaClass acc = Repository.lookupClass(accessor.class_name);
      return acc.getPackageName().equals(jc.getPackageName());
    }
  }
}
