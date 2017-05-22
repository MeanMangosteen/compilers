package de.fub.bytecode.generic;

import de.fub.bytecode.Constants;
import de.fub.bytecode.classfile.*;
import java.util.Vector;
import java.util.Enumeration;

/** 
 * Template class for building up a java class. May be initialized with an
 * existing java class (file).
 *
 * @see JavaClass
 * @version $Id: ClassGen.java,v 1.11 2001/05/15 15:22:10 pcbeard Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ClassGen extends AccessFlags implements Cloneable {
  /* Corresponds to the fields found in a JavaClass object.
   */
  private String   class_name, super_class_name, file_name;
  private int      class_name_index = -1, superclass_name_index = -1;
  private int      major = Constants.MAJOR_1_1, minor = Constants.MINOR_1_1;

  private ConstantPoolGen cp; // Template for building up constant pool

  // Vectors instead of arrays to gather fields, methods, etc.
  private Vector   field_vec     = new Vector();
  private Vector   method_vec    = new Vector();
  private Vector   attribute_vec = new Vector();
  private Vector   interface_vec = new Vector();

  /** Convenience constructor to set up some important values initially.
   *
   * @param class_name fully qualified class name
   * @param super_class_name fully qualified superclass name
   * @param file_name source file name
   * @param access_flags access qualifiers
   * @param interfaces implemented interfaces
   */
  public ClassGen(String class_name, String super_class_name, String file_name,
		  int access_flags, String[] interfaces) {
    this.class_name       = class_name;
    this.super_class_name = super_class_name;
    this.file_name        = file_name;
    this.access_flags     = access_flags;
    cp = new ConstantPoolGen(); // Create empty constant pool

    // Put everything needed by default into the constant pool and the vectors
    addAttribute(new SourceFile(cp.addUtf8("SourceFile"), 2,
				cp.addUtf8(file_name), cp.getConstantPool()));
    class_name_index      = cp.addClass(class_name);
    superclass_name_index = cp.addClass(super_class_name);

    if(interfaces != null)
      for(int i=0; i < interfaces.length; i++)
	addInterface(interfaces[i]);
  }

  /**
   * Initialize with existing class.
   * @param clazz JavaClass object (e.g. read from file)
   */
  public ClassGen(JavaClass clazz) {
    class_name_index      = clazz.getClassNameIndex();
    superclass_name_index = clazz.getSuperclassNameIndex();
    class_name            = clazz.getClassName();
    super_class_name      = clazz.getSuperclassName();
    file_name             = clazz.getSourceFileName();
    access_flags          = clazz.getAccessFlags();
    cp                    = new ConstantPoolGen(clazz.getConstantPool());
    major                 = clazz.getMajor();
    minor                 = clazz.getMinor();

    Attribute[] attributes = clazz.getAttributes();
    Method[]    methods    = clazz.getMethods();
    Field[]     fields     = clazz.getFields();
    String[]    interfaces = clazz.getInterfaceNames();
    
    for(int i=0; i < interfaces.length; i++)
      addInterface(interfaces[i]);

    for(int i=0; i < attributes.length; i++)
      addAttribute(attributes[i]);

    for(int i=0; i < methods.length; i++)
      addMethod(methods[i]);

    for(int i=0; i < fields.length; i++)
      addField(fields[i]);
  }

  /**
   * @return the (finally) built up Java class object.
   */
  public JavaClass getJavaClass() {
    int[]        interfaces = getInterfaces();
    Field[]      fields     = getFields();
    Method[]     methods    = getMethods();
    Attribute[]  attributes = getAttributes();

    // Must be last since the above calls may still add something to it
    ConstantPool cp         = this.cp.getFinalConstantPool();
    
    return new JavaClass(class_name_index, superclass_name_index,
			 file_name, major, minor, access_flags,
			 cp, interfaces, fields, methods, attributes);
  }

  /**
   * Add an interface to this class, i.e., this class has to implement it.
   * @param name interface to implement (fully qualified class name)
   */
  public void addInterface(String name) {
    interface_vec.addElement(name);
  }

  /**
   * Remove an interface from this class.
   * @param name interface to remove (fully qualified name)
   */
  public void removeInterface(String name) {
    interface_vec.removeElement(name);
  }

  /**
   * @return major version number of class file
   */
  public int  getMajor()      { return major; }

  /** Set major version number of class file, default value is 45 (JDK 1.1)
   * @param major major version number
   */
  public void setMajor(int major) {
    this.major = major;
  }    

  /** Set minor version number of class file, default value is 3 (JDK 1.1)
   * @param minor minor version number
   */
  public void setMinor(int minor) {
    this.minor = minor;
  }    

  /**
   * @return minor version number of class file
   */
  public int  getMinor()      { return minor; }

  /**
   * Add an attribute to this class.
   * @param a attribute to add
   */
  public void addAttribute(Attribute a)    { attribute_vec.addElement(a); }

  /**
   * Add a method to this class.
   * @param m method to add
   */
  public void addMethod(Method m)          { method_vec.addElement(m); }

  /**
   * Convenience method.
   *
   * Add an empty constructor to this class that does nothing but calling super().
   * @param access rights for constructor
   */
  public void addEmptyConstructor(int access_flags) {
    InstructionList il = new InstructionList();
    il.append(InstructionConstants.THIS); // Push `this'
    il.append(new INVOKESPECIAL(cp.addMethodref(super_class_name,
						"<init>", "()V")));
    il.append(InstructionConstants.RETURN);

    MethodGen mg = new MethodGen(access_flags, Type.VOID, Type.NO_ARGS, null,
		       "<init>", class_name, il, cp);
    mg.setMaxStack(1);
    addMethod(mg.getMethod());
  }

  /**
   * Add a field to this class.
   * @param f field to add
   */
  public void addField(Field f)            { field_vec.addElement(f); }

  public boolean containsField(Field f)    { return field_vec.contains(f); }
  
  /** @return field object with given name, or null
   */
  public Field containsField(String name) {
    for(Enumeration e=field_vec.elements(); e.hasMoreElements();) {
      Field f = (Field)e.nextElement();
      if(f.getName().equals(name))
	return f;
    }

    return null;
  }

  /** @return method object with given name and signature, or null
   */
  public Method containsMethod(String name, String signature) {
    for(Enumeration e=method_vec.elements(); e.hasMoreElements();) {
      Method m = (Method)e.nextElement();
      if(m.getName().equals(name) && m.getSignature().equals(signature))
	return m;
    }

    return null;
  }

  /**
   * Remove an attribute from this class.
   * @param a attribute to remove
   */
  public void removeAttribute(Attribute a) { attribute_vec.removeElement(a); }

  /**
   * Remove a method from this class.
   * @param m method to remove
   */
  public void removeMethod(Method m)       { method_vec.removeElement(m); }

  /** Replace given method with new one. If the old one does not exist
   * add the new_ method to the class anyway.
   */
  public void replaceMethod(Method old, Method new_) {
    if(new_ == null)
      throw new ClassGenException("Replacement method must not be null");

    int i = method_vec.indexOf(old);

    if(i < 0)
      method_vec.addElement(new_);
    else
      method_vec.setElementAt(new_, i);
  }

  /** Replace given field with new one. If the old one does not exist
   * add the new_ field to the class anyway.
   */
  public void replaceField(Field old, Field new_) {
    if(new_ == null)
      throw new ClassGenException("Replacement method must not be null");

    int i = field_vec.indexOf(old);

    if(i < 0)
      field_vec.addElement(new_);
    else
      field_vec.setElementAt(new_, i);
  }

  /**
   * Remove a field to this class.
   * @param f field to remove
   */
  public void removeField(Field f)         { field_vec.removeElement(f); }

  public String getClassName()      { return class_name; }
  public String getSuperclassName() { return super_class_name; }
  public String getFileName()       { return file_name; }

  public void setClassName(String name) {
    class_name = name.replace('/', '.');
    class_name_index = cp.addClass(name);
  }

  public void setSuperclassName(String name) {
    super_class_name = name.replace('/', '.');
    superclass_name_index = cp.addClass(name);
  }

  public Method[] getMethods() {
    Method[] methods = new Method[method_vec.size()];
    method_vec.copyInto(methods);
    return methods;
  }

  public void setMethods(Method[] methods) {
    method_vec.removeAllElements();
    for (int m=0; m<methods.length; m++)
      addMethod(methods[m]);
  }

  public void setMethodAt(Method method, int pos) {
    method_vec.setElementAt(method, pos);
  }

  public Method getMethodAt(int pos) {
    return (Method)method_vec.elementAt(pos);
  }

  public String[] getInterfaceNames() {
    int      size = interface_vec.size();
    String[] interfaces = new String[size];

    interface_vec.copyInto(interfaces);
    return interfaces;
  }

  public int[] getInterfaces() {
    int   size = interface_vec.size();
    int[] interfaces = new int[size];

    for(int i=0; i < size; i++)
      interfaces[i] = cp.addClass((String)interface_vec.elementAt(i));

    return interfaces;
  }

  public Field[] getFields() {
    Field[] fields = new Field[field_vec.size()];
    field_vec.copyInto(fields);
    return fields;
  }

  public Attribute[] getAttributes() {
    Attribute[] attributes = new Attribute[attribute_vec.size()];
    attribute_vec.copyInto(attributes);
    return attributes;
  }

  public ConstantPoolGen getConstantPool() { return cp; }
  public void setConstantPool(ConstantPoolGen constant_pool) {
    cp = constant_pool;
  }    

  public void setClassNameIndex(int class_name_index) {
    this.class_name_index = class_name_index;
    class_name = cp.getConstantPool().
      getConstantString(class_name_index, Constants.CONSTANT_Class).replace('/', '.');
  }

  public void setSuperclassNameIndex(int superclass_name_index) {
    this.superclass_name_index = superclass_name_index;
    super_class_name = cp.getConstantPool().
      getConstantString(superclass_name_index, Constants.CONSTANT_Class).replace('/', '.');
  }

  public int getSuperclassNameIndex() { return superclass_name_index; }    

  public int getClassNameIndex()   { return class_name_index; }

  private Vector observers;

  /** Add observer for this object.
   */
  public void addObserver(ClassObserver o) {
    if(observers == null)
      observers = new Vector();

    observers.addElement(o);
  }

  /** Remove observer for this object.
   */
  public void removeObserver(ClassObserver o) {
    if(observers != null)
      observers.removeElement(o);
  }

  /** Call notify() method on all observers. This method is not called
   * automatically whenever the state has changed, but has to be
   * called by the user after he has finished editing the object.
   */
  public void update() {
    if(observers != null)
      for(Enumeration e = observers.elements(); e.hasMoreElements(); )
	((ClassObserver)e.nextElement()).notify(this);
  }

  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      System.err.println(e);
      return null;
    }
  }
}
