package de.fub.bytecode.classfile;

/**
 * Denote class to have an accept method();
 *
 * @version $Id: Node.java,v 1.1 2001/06/11 11:37:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface Node {
  public void accept(Visitor obj);    
}
