import java.io.*;
import de.fub.bytecode.classfile.*;
import de.fub.bytecode.*;
import de.fub.bytecode.generic.*;

/**
 * Test BCEL if an input file is identical to the outfile generated
 * with BCEL. Of course there may some small differences, e.g., because
 * BCEL generates local variable tables by default.
 *
 * Try to:
 * <pre>
% java id <someclass>
% java listclass -code <someclass> &gt; foo
% java listclass -code <someclass>.clazz &gt; bar
% diff foo bar | more
 * <pre>
 *
 * @version $Id: id.java,v 1.2 2001/10/11 11:59:26 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class id {
  public static void main(String[] argv) throws Exception { 
    JavaClass clazz = null;

    if((clazz = Repository.lookupClass(argv[0])) == null)
      clazz = new ClassParser(argv[0]).parse(); // May throw IOException

    ClassGen cg = new ClassGen(clazz);

    Method[] methods = clazz.getMethods();

    for(int i=0; i < methods.length; i++) {
      MethodGen mg = new MethodGen(methods[i], cg.getClassName(), cg.getConstantPool());
      cg.replaceMethod(methods[i], mg.getMethod());
    }

    Field[] fields = clazz.getFields();

    for(int i=0; i < fields.length; i++) {
      FieldGen fg =  new FieldGen(fields[i], cg.getConstantPool());
      cg.replaceField(fields[i], fg.getField());
    }

    cg.getJavaClass().dump(clazz.getClassName() + ".clazz");
  }
}
