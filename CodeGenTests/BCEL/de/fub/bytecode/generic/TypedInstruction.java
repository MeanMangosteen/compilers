package de.fub.bytecode.generic;

/**
 * Get the type associated with an instruction, int for ILOAD, or the type
 * of the field of a PUTFIELD instruction, e.g..
 *
 * @version $Id: TypedInstruction.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface TypedInstruction {
  public Type getType(ConstantPoolGen cpg);
}

