package de.fub.bytecode.util;
import java.util.Vector;
import de.fub.bytecode.classfile.JavaClass;

/** 
 * Utility class implementing a (typesafe) collection of JavaClass
 * objects. Contains the most important methods of a Vector.
 *
 * @version $Id: ClassVector.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A> 
 * @see Vector
*/
public class ClassVector {
  protected Vector vec = new Vector();
  
  public void      addElement(JavaClass clazz) { vec.addElement(clazz); }
  public JavaClass elementAt(int index)        { return (JavaClass)vec.elementAt(index); }
  public void      removeElementAt(int index)  { vec.removeElementAt(index); }

  public JavaClass[] toArray() {
    JavaClass[] classes = new JavaClass[vec.size()];
    vec.copyInto(classes);
    return classes;
  }
}
