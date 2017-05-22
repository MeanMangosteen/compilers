package de.fub.bytecode.generic;

/**
 * Denotes a push instruction that produces a literal on the stack
 * such as  SIPUSH, BIPUSH, ICONST, etc.
 *
 * @version $Id: ConstantPushInstruction.java,v 1.3 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>

 * @see ICONST
 * @see SIPUSH
 */
public interface ConstantPushInstruction extends PushInstruction, TypedInstruction {
  public Number getValue();
}

