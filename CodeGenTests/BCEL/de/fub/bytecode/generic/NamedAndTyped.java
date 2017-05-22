package de.fub.bytecode.generic;

/**
 * Denote entity that has both name and type. This is true for local variables,
 * methods and fields.
 *
 * @version $Id: NamedAndTyped.java,v 1.3 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface NamedAndTyped {
  public String getName();
  public Type   getType();
  public void   setName(String name);
  public void   setType(Type type);

}

