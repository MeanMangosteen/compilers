import de.fub.bytecode.classfile.*;
import de.fub.bytecode.generic.*;
import de.fub.bytecode.Constants;
import de.fub.bytecode.*;

/**
 * Read class file(s) and examine all of its methods, determining the
 * maximum stack depth used by analyzing control flow.
 *
 * @version $Id: maxstack.java,v 1.3 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public final class maxstack {
  public static void main(String[] argv) { 
    try {
      for(int i = 0; i < argv.length; i++) {
	String    class_name = argv[i];
	JavaClass java_class = Repository.lookupClass(class_name);

	if(java_class == null) // Look for .class file?
	  java_class = new ClassParser(class_name).parse();

	ConstantPoolGen cp      = new ConstantPoolGen(java_class.getConstantPool());
   	Method[]        methods = java_class.getMethods();
	
	for(int j = 0; j < methods.length; j++) {
	  Method    m  = methods[j];

	  if(!(m.isAbstract() || m.isNative())) {
	    MethodGen mg = new MethodGen(m, argv[i], cp);

	    int compiled_stack  = mg.getMaxStack();
	    int compiled_locals = mg.getMaxLocals();
	    mg.setMaxStack(); // Recompute value
	    mg.setMaxLocals();
	    int computed_stack  = mg.getMaxStack();
	    int computed_locals = mg.getMaxLocals();

	    mg.getInstructionList().dispose(); // Reuse instruction handles
	    
	    System.out.println(m);
	    
	    if(computed_stack == compiled_stack)
	      System.out.println("Stack ok(" + computed_stack + ")");
	    else
	      System.out.println("\nCompiled stack size " + compiled_stack +
			       " computed size " + computed_stack);
	    
	    if(computed_locals == compiled_locals)
	      System.out.println("Locals ok(" + computed_locals + ")");
	    else
	      System.out.println("\nCompiled locals " + compiled_locals +
				 " computed size " + computed_locals);
	  }
	}
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
