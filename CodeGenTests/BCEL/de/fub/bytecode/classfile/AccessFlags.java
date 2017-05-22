package de.fub.bytecode.classfile;
import  de.fub.bytecode.Constants;

/**
 * Super class for all objects that have modifiers like private, final, ...
 * I.e. classes, fields, and methods.
 *
 * @version $Id: AccessFlags.java,v 1.5 2001/05/21 15:10:53 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class AccessFlags {
  protected int access_flags;
  
  public AccessFlags() {}

  /**
   * @param a inital access flags
   */
  public AccessFlags(int a) {
    access_flags = a;
  }

  /** 
   * @return Access flags of the object.
   */ 
  public final int getAccessFlags() { return access_flags; }

  /**
   * @param access_flags Access flags of the object. 
   */
  public final void setAccessFlags(int access_flags) {
    this.access_flags = access_flags;
  }

  private final void setFlag(int flag, boolean set) {
    if((access_flags & flag) != 0) { // Flag is set already
      if(!set) // Delete flag ?
	access_flags ^= flag;
    } else {   // Flag not set
      if(set)  // Set flag ?
	access_flags |= flag;
    }
  }

  public final void isPublic(boolean flag) { setFlag(Constants.ACC_PUBLIC, flag); }
  public final boolean isPublic() {
    return (access_flags & Constants.ACC_PUBLIC) != 0;
  }

  public final void isPrivate(boolean flag) { setFlag(Constants.ACC_PRIVATE, flag); }
  public final boolean isPrivate() {
    return (access_flags & Constants.ACC_PRIVATE) != 0;
  }

  public final void isProtected(boolean flag) { setFlag(Constants.ACC_PROTECTED, flag); }
  public final boolean isProtected() {
    return (access_flags & Constants.ACC_PROTECTED) != 0;
  }

  public final void isStatic(boolean flag) { setFlag(Constants.ACC_STATIC, flag); }
  public final boolean isStatic() {
    return (access_flags & Constants.ACC_STATIC) != 0;
  }

  public final void isFinal(boolean flag) { setFlag(Constants.ACC_FINAL, flag); }
  public final boolean isFinal() {
    return (access_flags & Constants.ACC_FINAL) != 0;
  }

  public final void isSynchronized(boolean flag) { setFlag(Constants.ACC_SYNCHRONIZED, flag); }
  public final boolean isSynchronized() {
    return (access_flags & Constants.ACC_SYNCHRONIZED) != 0;
  }

  public final void isVolatile(boolean flag) { setFlag(Constants.ACC_VOLATILE, flag); }
  public final boolean isVolatile() {
    return (access_flags & Constants.ACC_VOLATILE) != 0;
  }

  public final void isTransient(boolean flag) { setFlag(Constants.ACC_TRANSIENT, flag); }
  public final boolean isTransient() {
    return (access_flags & Constants.ACC_TRANSIENT) != 0;
  }

  public final void isNative(boolean flag) { setFlag(Constants.ACC_NATIVE, flag); }
  public final boolean isNative() {
    return (access_flags & Constants.ACC_NATIVE) != 0;
  }

  public final void isInterface(boolean flag) { setFlag(Constants.ACC_INTERFACE, flag); }
  public final boolean isInterface() {
    return (access_flags & Constants.ACC_INTERFACE) != 0;
  }

  public final void isAbstract(boolean flag) { setFlag(Constants.ACC_ABSTRACT, flag); }
  public final boolean isAbstract() {
    return (access_flags & Constants.ACC_ABSTRACT) != 0;
  }

  public final void isStrictfp(boolean flag) { setFlag(Constants.ACC_STRICT, flag); }
  public final boolean isStrictfp() {
    return (access_flags & Constants.ACC_STRICT) != 0;
  }
}
