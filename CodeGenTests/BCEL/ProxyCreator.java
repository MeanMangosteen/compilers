import java.io.*;
import de.fub.bytecode.classfile.*;
import de.fub.bytecode.generic.*;
import de.fub.bytecode.util.ByteSequence;
import de.fub.bytecode.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.zip.*;

/**
 * Dynamically creates and uses a proxy for <tt>java.awt.event.ActionListener</tt>
 * via the classloader mechanism if called with
 * <pre>java de.fub.bytecode.util.JavaWrapper ProxyCreator</pre>
 *
 * The trick is to encode the byte code we need into the class name
 * using the Utility.encode() method. This will result however in big
 * ugly class name, so for many cases it will be more sufficient to
 * put some clever creation code into the class loader.<br> This is
 * comparable to the mechanism provided via
 * <tt>java.lang.reflect.Proxy</tt>, but much more flexible.
 *
 * @version $Id: ProxyCreator.java,v 1.6 2001/09/03 09:30:25 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see de.fub.bytecode.util.JavaWrapper
 * @see de.fub.bytecode.util.ClassLoader
 * @see Utility
 */
public class ProxyCreator {
  /** Load class and create instance
   */
  public static Object createProxy(String pack, String class_name) {
    try {
      Class cl = Class.forName(pack + "$$BCEL$$" + class_name);
      return cl.newInstance();
    } catch(Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /** Create JavaClass object for a simple proxy for an java.awt.event.ActionListener
   * that just prints the passed arguments, load and use it via the class loader
   * mechanism.
   */
  public static void main(String[] argv) throws Exception {
    ClassLoader loader = ProxyCreator.class.getClassLoader();

    // instanceof won't work here ...
    if(loader.getClass().toString().equals("class de.fub.bytecode.util.ClassLoader")) {
      // Real class name will be set by the class loader
      ClassGen cg = new ClassGen("foo", "java.lang.Object", "", Constants.ACC_PUBLIC,
				 new String[] {"java.awt.event.ActionListener"});
     
      // That's important, otherwise newInstance() won't work
      cg.addEmptyConstructor(Constants.ACC_PUBLIC);

      InstructionList    il      = new InstructionList();
      ConstantPoolGen    cp      = cg.getConstantPool();
      InstructionFactory factory = new InstructionFactory(cg);

      int out     = cp.addFieldref("java.lang.System", "out",
				   "Ljava/io/PrintStream;");
      int println = cp.addMethodref("java.io.PrintStream", "println",
				  "(Ljava/lang/Object;)V");
      MethodGen mg = new MethodGen(Constants.ACC_PUBLIC, Type.VOID,
				   new Type[] {
				     new ObjectType("java.awt.event.ActionEvent")
				   }, null, "actionPerformed", "foo", il, cp);

      // System.out.println("actionPerformed:" + event);
      il.append(new GETSTATIC(out));
      il.append(factory.createNew("java.lang.StringBuffer"));
      il.append(InstructionConstants.DUP);
      il.append(new PUSH(cp, "actionPerformed:"));
      il.append(factory.createInvoke("java.lang.StringBuffer", "<init>", Type.VOID,
				     new Type[] {Type.STRING}, Constants.INVOKESPECIAL));

      il.append(new ALOAD(1));
      il.append(factory.createAppend(Type.OBJECT));
      il.append(new INVOKEVIRTUAL(println));
      il.append(InstructionConstants.RETURN);

      mg.stripAttributes(true);
      mg.setMaxStack();
      mg.setMaxLocals();
      cg.addMethod(mg.getMethod());

      byte[] bytes = cg.getJavaClass().getBytes();

      System.out.println("Uncompressed class: " + bytes.length);

      String s = Utility.encode(bytes, true);
      System.out.println("Encoded class: " + s.length());

      System.out.print("Creating proxy ... ");
      ActionListener a = (ActionListener)createProxy("foo.bar.", s);
      System.out.println("Done. Now calling actionPerformed()");
      
      a.actionPerformed(new ActionEvent(a, ActionEvent.ACTION_PERFORMED, "hello"));
    } else
      System.err.println("Call me with java de.fub.bytecode.util.JavaWrapper ProxyCreator");
  }

}
