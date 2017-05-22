package de.fub.bytecode.generic;

/** 
 * Thrown on internal errors. Extends RuntimeException so it hasn't to be declared
 * in the throws clause every time.
 *
 * @version $Id: ClassGenException.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class ClassGenException extends RuntimeException {
  public ClassGenException() { super(); }
  public ClassGenException(String s) { super(s); }
}

