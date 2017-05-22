package de.fub.bytecode.generic;

/**
 * Implement this interface if you're interested in changes to an InstructionList object
 * and register yourself with addObserver().
 *
 * @version $Id: InstructionListObserver.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface InstructionListObserver {
  public void notify(InstructionList list);
}

