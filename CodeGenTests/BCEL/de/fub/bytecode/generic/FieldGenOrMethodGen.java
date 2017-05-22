package de.fub.bytecode.generic;
import de.fub.bytecode.classfile.*;
import java.util.Vector;

/**
 * Super class for FieldGen and MethodGen objects, since they have
 * some methods in common!
 *
 * @version $Id: FieldGenOrMethodGen.java,v 1.6 2001/05/15 15:22:19 pcbeard Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class FieldGenOrMethodGen extends AccessFlags
  implements NamedAndTyped, Cloneable
{
  protected String          name;
  protected Type            type;
  protected ConstantPoolGen cp;
  private   Vector          attribute_vec = new Vector();

  protected FieldGenOrMethodGen() {}

  public void            setType(Type type)   { this.type = type; }
  public Type            getType()            { return type; }

  /** @return name of method/field.
   */
  public String          getName()            { return name; }
  public void            setName(String name) { this.name = name; }

  public ConstantPoolGen getConstantPool()                   { return cp; }
  public void            setConstantPool(ConstantPoolGen cp) { this.cp = cp; }    

  /**
   * Add an attribute to this method. Currently, the JVM knows about
   * the `Code', `ConstantValue', `Synthetic' and `Exceptions'
   * attributes. Other attributes will be ignored by the JVM but do no
   * harm.
   *
   * @param a attribute to be added
   */
  public void addAttribute(Attribute a) { attribute_vec.addElement(a); }

  /**
   * Remove an attribute.
   */
  public void removeAttribute(Attribute a) { attribute_vec.removeElement(a); }

  /**
   * Remove all attributes.
   */
  public void removeAttributes() { attribute_vec.removeAllElements(); }
   
  /**
   * @return all attributes of this method.
   */
  public Attribute[] getAttributes() {
    Attribute[] attributes = new Attribute[attribute_vec.size()];
    attribute_vec.copyInto(attributes);
    return attributes;
  }

  /** @return signature of method/field.
   */
  public abstract String  getSignature();

  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      System.err.println(e);
      return null;
    }
  }
}
