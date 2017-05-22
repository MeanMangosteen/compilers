package de.fub.bytecode.generic;

/**
 * Denote an instruction that may produce a value on top of the stack
 * (this excludes DUP_X1, e.g.)
 *
 * @version $Id: StackProducer.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface StackProducer {
  /** @return how many words are produced on stack
   */
  public int produceStack(ConstantPoolGen cpg);
}

