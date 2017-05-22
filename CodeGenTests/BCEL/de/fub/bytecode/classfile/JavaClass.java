package de.fub.bytecode.classfile;

import  de.fub.bytecode.Constants;
import  de.fub.bytecode.Repository;
import  java.io.*;
import  java.util.StringTokenizer;

/**
 * Represents a Java class, i.e., the data structures, constant pool,
 * fields, methods and commands contained in a Java .class file.
 * See <a href="ftp://java.sun.com/docs/specs/">JVM 
 * specification</a> for details.
 *
 * @version $Id: JavaClass.java,v 1.6 2001/08/21 14:05:09 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class JavaClass extends AccessFlags implements Cloneable, Node {
  private String       file_name;
  private String       package_name;
  private String       source_file_name = "<Unknown>";
  private int          class_name_index;
  private int          superclass_name_index;
  private String       class_name;
  private String       superclass_name;
  private int          major, minor;  // Compiler version
  private ConstantPool constant_pool; // Constant pool
  private int[]        interfaces;    // implemented interfaces
  private String[]     interface_names;
  private Field[]      fields;        // Fields, i.e., variables of class
  private Method[]     methods;       // methods defined in the class
  private Attribute[]  attributes;    // attributes defined in the class
  private byte         source = HEAP; // Generated in memory

  public static final byte HEAP = 1;
  public static final byte FILE = 2;
  public static final byte ZIP  = 3;

  static boolean debug = false; // Debugging on/off
  static char    sep   = '/';   // directory separator

  /**
   * Constructor gets all contents as arguments.
   *
   * @param class_name Class name
   * @param superclass_name Superclass name
   * @param file_name File name
   * @param major Major compiler version
   * @param minor Minor compiler version
   * @param access_flags Access rights defined by bit flags
   * @param constant_pool Array of constants
   * @param interfaces Implemented interfaces
   * @param fields Class fields
   * @param methods Class methods
   * @param attributes Class attributes
   * @param source Read from file or generated in memory?
   */
  public JavaClass(int        class_name_index,
		   int        superclass_name_index,
		   String     file_name,
		   int        major,
		   int        minor,
		   int        access_flags,
		   ConstantPool constant_pool,
		   int[]      interfaces,
		   Field[]      fields,
		   Method[]     methods,
		   Attribute[]  attributes,
		   byte          source)
  {
    if(interfaces == null) // Allowed for backward compatibility
      interfaces = new int[0];
    if(attributes == null)
      this.attributes = new Attribute[0];
    if(fields == null)
      fields = new Field[0];
    if(methods == null)
      methods = new Method[0];

    this.class_name_index      = class_name_index;
    this.superclass_name_index = superclass_name_index;
    this.file_name             = file_name;
    this.major                 = major;
    this.minor                 = minor;
    this.access_flags          = access_flags;
    this.constant_pool         = constant_pool;
    this.interfaces            = interfaces;
    this.fields                = fields;
    this.methods               = methods;
    this.attributes            = attributes;
    this.source                = source;

    // Get source file name if available
    for(int i=0; i < attributes.length; i++) {
      if(attributes[i] instanceof SourceFile) {
	source_file_name = ((SourceFile)attributes[i]).getSourceFileName();
	break;
      }
    }
	
    // Get class name and superclass name
    ConstantUtf8  name;

    /* According to the specification the following entries must be of type
     * `ConstantClass' but we check that anyway via the 
     * `ConstPool.getConstant' method.
     */
    class_name = constant_pool.getConstantString(class_name_index, 
						 Constants.CONSTANT_Class);
    class_name = Utility.compactClassName(class_name, false);

    int index = class_name.lastIndexOf('.');
    if(index < 0)
      package_name = "";
    else
      package_name = class_name.substring(0, index);

    if(superclass_name_index > 0) { // May be zero -> class is java.lang.Object
      superclass_name = constant_pool.getConstantString(superclass_name_index,
							Constants.CONSTANT_Class);
      superclass_name = Utility.compactClassName(superclass_name, false);
    }
    else
      superclass_name = "java.lang.Object";    

    interface_names = new String[interfaces.length];
    for(int i=0; i < interfaces.length; i++) {
      String str = constant_pool.getConstantString(interfaces[i], Constants.CONSTANT_Class);
      interface_names[i] = Utility.compactClassName(str, false);
    }
  }

  /**
   * Constructor gets all contents as arguments.
   *
   * @param class_name Class name
   * @param superclass_name Superclass name
   * @param file_name File name
   * @param major Major compiler version
   * @param minor Minor compiler version
   * @param access_flags Access rights defined by bit flags
   * @param constant_pool Array of constants
   * @param interfaces Implemented interfaces
   * @param fields Class fields
   * @param methods Class methods
   * @param attributes Class attributes
   */
  public JavaClass(int        class_name_index,
		   int        superclass_name_index,
		   String     file_name,
		   int        major,
		   int        minor,
		   int        access_flags,
		   ConstantPool constant_pool,
		   int[]      interfaces,
		   Field[]      fields,
		   Method[]     methods,
		   Attribute[]  attributes) {
    this(class_name_index, superclass_name_index, file_name, major, minor, access_flags,
	 constant_pool, interfaces, fields, methods, attributes, HEAP);
  }

      
  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitJavaClass(this);
  }

  /* Print debug information depending on `JavaClass.debug'
   */
  static final void Debug(String str) {
    if(debug)
      System.out.println(str);
  }

  /** 
   * Dump class to a file.
   *
   * @param file Output file
   * @throw IOException
   */
  public void dump(File file) throws IOException
  {
    String parent = file.getParent();

    if(parent != null) {
      File dir = new File(parent);
      
      if(dir != null)
	dir.mkdirs();
    }

    dump(new DataOutputStream(new FileOutputStream(file)));
  }

  /** 
   * Dump class to a file named file_name.
   *
   * @param file_name Output file name
   * @exception IOException
   */
  public void dump(String file_name) throws IOException
  {
    dump(new File(file_name));
  }

  /**
   * @return class in binary format
   */
  public byte[] getBytes() {
    ByteArrayOutputStream s  = new ByteArrayOutputStream();
    DataOutputStream      ds = new DataOutputStream(s);

    try {
      dump(ds);
      ds.close();
    } catch(IOException e) { e.printStackTrace(); }

    return s.toByteArray();
  }

  /**
   * Dump Java class to output stream in binary format.
   *
   * @param file Output stream
   * @exception IOException
   */
  public void dump(OutputStream file) throws IOException {
    dump(new DataOutputStream(file));
  }

  /**
   * Dump Java class to output stream in binary format.
   *
   * @param file Output stream
   * @exception IOException
   */
  public void dump(DataOutputStream file) throws IOException
  {
    file.writeInt(0xcafebabe);
    file.writeShort(minor);
    file.writeShort(major);

    constant_pool.dump(file);
	
    file.writeShort(access_flags);
    file.writeShort(class_name_index);
    file.writeShort(superclass_name_index);

    file.writeShort(interfaces.length);
    for(int i=0; i < interfaces.length; i++)
      file.writeShort(interfaces[i]);

    file.writeShort(fields.length);
    for(int i=0; i < fields.length; i++)
      fields[i].dump(file);

    file.writeShort(methods.length);
    for(int i=0; i < methods.length; i++)
      methods[i].dump(file);

    if(attributes != null) {
      file.writeShort(attributes.length);
      for(int i=0; i < attributes.length; i++)
	attributes[i].dump(file);
    }
    else
      file.writeShort(0);

    file.close();
  }

  /**
   * @return Attributes of the class.
   */
  public Attribute[] getAttributes() { return attributes; }

  /**
   * @return Class name.
   */
  public String getClassName()       { return class_name; }

  /**
   * @return Package name.
   */
  public String getPackageName()       { return package_name; }    

  /**
   * @return Class name index.
   */
  public int getClassNameIndex()   { return class_name_index; }

  /**
   * @return Constant pool.
   */
  public ConstantPool getConstantPool() { return constant_pool; }

  /**
   * @return Fields, i.e., variables of the class.
   */
  public Field[] getFields()         { return fields; }    
  /**
   * @return File name of class, aka SourceFile attribute value
   */
  public String getFileName()        { return file_name; }    
  /**
   * @return Names of implemented interfaces.
   */
  public String[] getInterfaceNames()  { return interface_names; }    
  /**
   * @return Implemented interfaces.
   */
  public int[] getInterfaces()     { return interfaces; }    
  /**
   * @return Major number of compiler version.
   */
  public int  getMajor()           { return major; }    
  /**
   * @return Methods of the class.
   */
  public Method[] getMethods()       { return methods; }    
  /**
   * @return Minor number of compiler version.
   */
  public int  getMinor()           { return minor; }    

  /**
   * @return sbsolute path to file where this class was read from
   */
  public String getSourceFileName()  { return source_file_name; }    

  /**
   * @return Superclass name.
   */
  public String getSuperclassName()  { return superclass_name; }    
  /**
   * @return Class name index.
   */
  public int getSuperclassNameIndex() { return superclass_name_index; }    

  static {
    // Debugging ... on/off
    String debug = System.getProperty("JavaClass.debug");

    if(debug != null)
      JavaClass.debug = new Boolean(debug).booleanValue();

    // Get path separator either / or \ usually
    String sep = System.getProperty("file.separator");

    if(sep != null)
      try {
	JavaClass.sep = sep.charAt(0);
    } catch(StringIndexOutOfBoundsException e) {} // Never reached
  }

  /**
   * @param attributes.
   */
  public void setAttributes(Attribute[] attributes) {
    this.attributes = attributes;
  }    
  /**
   * @param class_name.
   */
  public void setClassName(String class_name) {
    this.class_name = class_name;
  }    
  /**
   * @param class_name_index.
   */
  public void setClassNameIndex(int class_name_index) {
    this.class_name_index = class_name_index;
  }    
  /**
   * @param constant_pool.
   */
  public void setConstantPool(ConstantPool constant_pool) {
    this.constant_pool = constant_pool;
  }    
  /**
   * @param fields.
   */
  public void setFields(Field[] fields) {
    this.fields = fields;
  }    
  /**
   * Set File name of class, aka SourceFile attribute value
   */
  public void setFileName(String file_name) {
    this.file_name = file_name;
  }    
  /**
   * @param interface_names.
   */
  public void setInterfaceNames(String[] interface_names) {
    this.interface_names = interface_names;
  }    
  /**
   * @param interfaces.
   */
  public void setInterfaces(int[] interfaces) {
    this.interfaces = interfaces;
  }    
  /**
   * @param major.
   */
  public void setMajor(int major) {
    this.major = major;
  }    
  /**
   * @param methods.
   */
  public void setMethods(Method[] methods) {
    this.methods = methods;
  }    
  /**
   * @param minor.
   */
  public void setMinor(int minor) {
    this.minor = minor;
  }    
  /**
   * Set absolute path to file this class was read from.
   */
  public void setSourceFileName(String source_file_name) {
    this.source_file_name = source_file_name;
  }    
  /**
   * @param superclass_name.
   */
  public void setSuperclassName(String superclass_name) {
    this.superclass_name = superclass_name;
  }    
  /**
   * @param superclass_name_index.
   */
  public void setSuperclassNameIndex(int superclass_name_index) {
    this.superclass_name_index = superclass_name_index;
  }    
  /**
   * @return String representing class contents.
   */
  public String toString() {
    String access = Utility.accessToString(access_flags, true);
    access = access.equals("")? "" : (access + " ");

    StringBuffer buf = new StringBuffer(access +
					Utility.classOrInterface(access_flags) + 
					" " +
					class_name + " extends " +
					Utility.compactClassName(superclass_name,
								 false) + '\n');
    int size = interfaces.length;

    if(size > 0) {
      buf.append("implements\t\t");

      for(int i=0; i < size; i++) {
	buf.append(interface_names[i]);
	if(i < size - 1)
	  buf.append(", ");
      }

      buf.append('\n');
    }

    buf.append("filename\t\t" + file_name + '\n');
    buf.append("compiled from\t\t" + source_file_name + '\n');
    buf.append("compiler version\t" + major + "." + minor + '\n');
    buf.append("access flags\t\t" + access_flags + '\n');
    buf.append("constant pool\t\t" + constant_pool.getLength() + " entries\n");
    buf.append("ACC_SUPER flag\t\t" + isSuper() + "\n");

    if(attributes.length > 0) {
      buf.append("\nAttribute(s):\n");
      for(int i=0; i < attributes.length; i++)
	buf.append(indent(attributes[i]));
    }

    if(fields.length > 0) {
      buf.append("\n" + fields.length + " fields:\n");
      for(int i=0; i < fields.length; i++)
	buf.append("\t" + fields[i] + '\n');
    }

    if(methods.length > 0) {
      buf.append("\n" + methods.length + " methods:\n");
      for(int i=0; i < methods.length; i++)
	buf.append("\t" + methods[i] + '\n');
    }

    return buf.toString();
  }    

  private static final String indent(Object obj) {
    StringTokenizer tok = new StringTokenizer(obj.toString(), "\n");
    StringBuffer buf = new StringBuffer();

    while(tok.hasMoreTokens())
      buf.append("\t" + tok.nextToken() + "\n");

    return buf.toString();
  }

  /**
   * @return deep copy of this class
   */
  public JavaClass copy() {
    JavaClass c = null;

    try {
      c = (JavaClass)clone();
    } catch(CloneNotSupportedException e) {}

    c.constant_pool   = constant_pool.copy();
    c.interfaces      = (int[])interfaces.clone();
    c.interface_names = (String[])interface_names.clone();

    c.fields = new Field[fields.length];
    for(int i=0; i < fields.length; i++)
      c.fields[i] = fields[i].copy(c.constant_pool);

    c.methods = new Method[methods.length];
    for(int i=0; i < methods.length; i++)
      c.methods[i] = methods[i].copy(c.constant_pool);

    c.attributes = new Attribute[attributes.length];
    for(int i=0; i < attributes.length; i++)
      c.attributes[i] = attributes[i].copy(c.constant_pool);

    return c;
  }

  public final boolean instanceOf(JavaClass super_class) {
    return Repository.instanceOf(this, super_class);
  }

  public final boolean isSuper() {
    return (access_flags & Constants.ACC_SUPER) != 0;
  }

  public final boolean isClass() {
    return (access_flags & Constants.ACC_INTERFACE) == 0;
  }

  /** @return returns either HEAP (generated), FILE, or ZIP
   */
  public final byte getSource() {
    return source;
  }
}
