package de.fub.bytecode.util;

import java.lang.reflect.*;

/**
 * Java interpreter replacement, i.e., wrapper that uses its own ClassLoader
 * to modify/generate classes as they're requested. You can take this as a template
 * for your own applications.<br>
 * Call this wrapper with
 * <pre>java de.fub.bytecode.util.JavaWrapper &lt;real.class.name&gt; [arguments]</pre>
 * <p>
 * To use your own class loader you can set the "bcel.classloader" system property
 * which defaults to "de.fub.bytecode.util.ClassLoader", e.g., with
 * <pre>java de.fub.bytecode.util.JavaWrapper -Dbcel.classloader=foo.MyLoader &lt;real.class.name&gt; [arguments]</pre>
 * </p>
 *
 * @version $Id: JavaWrapper.java,v 1.3 2001/08/24 13:56:51 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see ClassLoader
 */
public class JavaWrapper {
  private java.lang.ClassLoader loader;

  private static java.lang.ClassLoader getClassLoader() {
    String s = System.getProperty("bcel.classloader");

    if((s == null) || "".equals(s))
      s = "de.fub.bytecode.util.ClassLoader";

    try {
      return (java.lang.ClassLoader)Class.forName(s).newInstance();
    } catch(Exception e) {
      throw new RuntimeException(e.toString());
    }
  }
      
  public JavaWrapper(java.lang.ClassLoader loader) {
    this.loader = loader;
  }

  public JavaWrapper() {
    this(getClassLoader());
  }

  /** Runs the main method of the given class with the arguments passed in argv
   *
   * @param class_name the fully qualified class name
   * @param argv the arguments just as you would pass them directly
   */
  public void runMain(String class_name, String[] argv) throws ClassNotFoundException
  {
    Class   cl    = loader.loadClass(class_name);
    Method method = null;

    try {
      method = cl.getMethod("main",  new Class[] { argv.getClass() });
      
      /* Method main is sane ?
       */
      int   m = method.getModifiers();
      Class r = method.getReturnType();
      
      if(!(Modifier.isPublic(m) && Modifier.isStatic(m)) ||
	 Modifier.isAbstract(m) || (r != Void.TYPE))
	throw new NoSuchMethodException();
    } catch(NoSuchMethodException no) {
      System.out.println("In class " + class_name +
			 ": public static void main(String[] argv) is not defined");
      return;
    }
    
    try {
      method.invoke(null, new Object[] { argv });
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Default main method used as wrapper, expects the fully qualified class name
   * of the real class as the first argument.
   */
  public static void main(String[] argv) throws Exception {
    /* Expects class name as first argument, other arguments are by-passed.
     */
    if(argv.length == 0) {
      System.out.println("Missing class name.");
      return;
    }

    String class_name = argv[0];
    String[] new_argv = new String[argv.length - 1];
    System.arraycopy(argv, 1, new_argv, 0, new_argv.length);

    JavaWrapper wrapper = new JavaWrapper();
    wrapper.runMain(class_name, new_argv);
  }
}

