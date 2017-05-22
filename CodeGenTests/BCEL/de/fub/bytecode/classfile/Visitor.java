package de.fub.bytecode.classfile;

/**
 * Interface to make use of the Visitor pattern programming style.
 * I.e. a class that implements this interface can traverse the contents of
 * a Java class just by calling the `accept' method which all classes have.
 *
 * Implemented by wish of 
 * <A HREF="http://www.inf.fu-berlin.de/~bokowski">Boris Bokowski</A>.
 *
 * @version $Id: Visitor.java,v 1.3 2001/08/15 14:47:50 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface Visitor {
  public void visitCode(Code obj);    
  public void visitCodeException(CodeException obj);    
  public void visitConstantClass(ConstantClass obj);    
  public void visitConstantDouble(ConstantDouble obj);    
  public void visitConstantFieldref(ConstantFieldref obj);    
  public void visitConstantFloat(ConstantFloat obj);    
  public void visitConstantInteger(ConstantInteger obj);    
  public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref obj);    
  public void visitConstantLong(ConstantLong obj);    
  public void visitConstantMethodref(ConstantMethodref obj);    
  public void visitConstantNameAndType(ConstantNameAndType obj);    
  public void visitConstantPool(ConstantPool obj);    
  public void visitConstantString(ConstantString obj);    
  public void visitConstantUtf8(ConstantUtf8 obj);    
  public void visitConstantValue(ConstantValue obj);    
  public void visitDeprecated(Deprecated obj);    
  public void visitExceptionTable(ExceptionTable obj);    
  public void visitField(Field obj);    
  public void visitInnerClass(InnerClass obj);    
  public void visitInnerClasses(InnerClasses obj);    
  public void visitJavaClass(JavaClass obj);    
  public void visitLineNumber(LineNumber obj);    
  public void visitLineNumberTable(LineNumberTable obj);    
  public void visitLocalVariable(LocalVariable obj);    
  public void visitLocalVariableTable(LocalVariableTable obj);    
  public void visitMethod(Method obj);    
  public void visitSourceFile(SourceFile obj);    
  public void visitSynthetic(Synthetic obj);    
  public void visitUnknown(Unknown obj);    
  public void visitStackMap(StackMap obj);
  public void visitStackMapEntry(StackMapEntry obj);
}
