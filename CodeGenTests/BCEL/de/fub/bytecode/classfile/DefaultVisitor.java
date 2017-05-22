package de.fub.bytecode.classfile;

/**
 * The DefaultVisitor class is deprecated; use DescendingVisitor instead
 *
 * @version $Id: DefaultVisitor.java,v 1.8 2001/08/15 14:47:50 dahm Exp $
 * @author <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A> 
 * @deprecated Use DescendingVisitor instead.
 * @see de.fub.bytecode.classfile.DescendingVisitor
 */
public class DefaultVisitor extends DescendingVisitor{
  public DefaultVisitor(JavaClass clazz, Visitor visitor) {
    super(clazz, visitor);
  }
}
