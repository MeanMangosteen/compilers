package de.fub.bytecode.generic;

/**
 * Imnplement this interface if you're interested in changes to a FieldGen object
 * and register yourself with addObserver().
 *
 * @version $Id: FieldObserver.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface FieldObserver {
  public void notify(FieldGen field);
}

