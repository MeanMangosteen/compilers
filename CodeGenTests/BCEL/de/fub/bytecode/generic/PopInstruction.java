package de.fub.bytecode.generic;

/**
 * Denotes an unparameterized instruction to pop a value on top from the stack,
 * such as ISTORE, POP, PUTSTATIC.
 *
 * @version $Id: PopInstruction.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see ISTORE
 * @see POP
 */
public interface PopInstruction extends StackConsumer {
}

