package de.fub.bytecode.generic;

/**
 * Denote an instruction that may throw a run-time or a linking
 * exception (or both) during execution.  This is not quite the truth
 * as such; because all instructions may throw an
 * java.lang.VirtualMachineError. These exceptions are omitted.
 * 
 * The Lava Language Specification specifies exactly which
 * <i>RUN-TIME</i> and which <i>LINKING</i> exceptions each
 * instruction may throw which is reflected by the implementers.  Due
 * to the structure of the JVM specification, it may be possible that
 * an Instruction implementing this interface returns a Class[] of
 * size 0.
 *
 * Please note that we speak of an "exception" here when we mean any
 * "Throwable" object; so this term is equally used for "Exception"
 * and "Error" objects.
 *
 * @version $Id: ExceptionThrower.java,v 1.2 2001/01/18 16:08:33 dahm Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~ehaase">Enver Haase</A>
 */
public interface ExceptionThrower {
  public java.lang.Class[] getExceptions();
}
