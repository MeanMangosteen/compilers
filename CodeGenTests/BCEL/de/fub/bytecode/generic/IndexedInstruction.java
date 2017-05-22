package de.fub.bytecode.generic;

/**
 * Denote entity that refers to an index, e.g. local variable instructions,
 * RET, CPInstruction, etc.
 *
 * @version $Id: IndexedInstruction.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface IndexedInstruction {
  public int getIndex();
  public void setIndex(int index);
}

