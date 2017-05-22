package de.fub.bytecode.generic;

/**
 * Denote an instruction that may consume a value from the stack.
 *
 * @version $Id: StackConsumer.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface StackConsumer {
  /** @return how many words are consumed from stack
   */
  public int consumeStack(ConstantPoolGen cpg);
}

