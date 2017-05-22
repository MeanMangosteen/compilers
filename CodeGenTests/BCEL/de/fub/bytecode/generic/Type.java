package de.fub.bytecode.generic;

import de.fub.bytecode.Constants;
import de.fub.bytecode.classfile.*;
import java.io.*;
import java.util.Vector;

/** 
 * Abstract super class for all possible java types, namely basic types
 * such as int, object types like String and array types, e.g. int[]
 *
 * @version $Id: Type.java,v 1.12 2001/05/30 12:01:08 ehaase Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class Type {
  protected byte   type;
  protected String signature; // signature for the type

  /** Predefined constants
   */
  public static final BasicType     VOID         = new BasicType(Constants.T_VOID);
  public static final BasicType     BOOLEAN      = new BasicType(Constants.T_BOOLEAN);
  public static final BasicType     INT          = new BasicType(Constants.T_INT);
  public static final BasicType     SHORT        = new BasicType(Constants.T_SHORT);
  public static final BasicType     BYTE         = new BasicType(Constants.T_BYTE);
  public static final BasicType     LONG         = new BasicType(Constants.T_LONG);
  public static final BasicType     DOUBLE       = new BasicType(Constants.T_DOUBLE);
  public static final BasicType     FLOAT        = new BasicType(Constants.T_FLOAT);
  public static final BasicType     CHAR         = new BasicType(Constants.T_CHAR);
  public static final ObjectType    OBJECT       = new ObjectType("java.lang.Object");
  public static final ObjectType    STRING       = new ObjectType("java.lang.String");
  public static final ObjectType    STRINGBUFFER = new ObjectType("java.lang.StringBuffer");
  public static final ObjectType    THROWABLE    = new ObjectType("java.lang.Throwable");
  public static final Type[]        NO_ARGS      = new Type[0];
  public static final ReferenceType NULL         = new ReferenceType();
  public static final Type          UNKNOWN      = new Type(Constants.T_UNKNOWN,
							    "<unknown object>"){};

  protected Type(byte t, String s) {
    type      = t;
    signature = s;
  }

  /**
   * @return signature for given type.
   */
  public String getSignature() { return signature; }

  /**
   * @return type as defined in Constants
   */
  public byte getType() { return type; }

  /**
   * @return stack size of this type (2 for long and double, 0 for void, 1 otherwise)
   */
  public int getSize() {
    switch(type) {
    case Constants.T_DOUBLE:
    case Constants.T_LONG: return 2;
    case Constants.T_VOID: return 0;
    default:     return 1;
    }
  }

  /**
   * @return Type string, e.g. `int[]'
   */
  public String toString() {
    return ((this.equals(Type.NULL) || (type >= Constants.T_UNKNOWN)))? signature :
      Utility.signatureToString(signature, false);
  }

  /**
   * Convert type to Java method signature, e.g. int[] f(java.lang.String x)
   * becomes (Ljava/lang/String;)[I
   *
   * @param return_type what the method returns
   * @param arg_types what are the argument types
   * @return method signature for given type(s).
   */
  public static String getMethodSignature(Type return_type, Type[] arg_types) { 
    StringBuffer buf = new StringBuffer("(");
    int length = (arg_types == null)? 0 : arg_types.length;

    for(int i=0; i < length; i++)
      buf.append(arg_types[i].getSignature());

    buf.append(')');
    buf.append(return_type.getSignature());

    return buf.toString();
  }

  private static int consumed_chars=0; // Remember position in string, see getArgumentTypes

  /**
   * Convert signature to a Type object.
   * @param signature signature string such as Ljava/lang/String;
   * @return type object
   */
  public static final Type getType(String signature)
    throws StringIndexOutOfBoundsException
  {
    byte type = Utility.typeOfSignature(signature);

    if(type <= Constants.T_VOID) {
      consumed_chars = 1;
      return BasicType.getType(type);
    } else if(type == Constants.T_ARRAY) {
      int dim=0;
      do { // Count dimensions
	dim++;
      } while(signature.charAt(dim) == '[');

      // Recurse, but just once, if the signature is ok
      Type t = getType(signature.substring(dim));

      consumed_chars += dim; // update counter

      return new ArrayType(t, dim);
    } else { // type == T_REFERENCE
      int index = signature.indexOf(';'); // Look for closing `;'

      if(index < 0)
	throw new ClassFormatError("Invalid signature: " + signature);
	
      consumed_chars = index + 1; // "Lblabla;" `L' and `;' are removed

      return new ObjectType(signature.substring(1, index).replace('/', '.'));
    }
  }

  /**
   * Convert return value of a method (signature) to a Type object.
   *
   * @param signature signature string such as (Ljava/lang/String;)V
   * @return return type
   */
  public static Type getReturnType(String signature) {
    try {
      // Read return type after `)'
      int index = signature.lastIndexOf(')') + 1;
      return getType(signature.substring(index));
    } catch(StringIndexOutOfBoundsException e) { // Should never occur
      throw new ClassFormatError("Invalid method signature: " + signature);
    }
  }

  /**
   * Convert arguments of a method (signature) to an array of Type objects.
   * @param signature signature string such as (Ljava/lang/String;)V
   * @return array of argument types
   */
  public static Type[] getArgumentTypes(String signature) {
    Vector vec = new Vector();
    int    index;
    Type[] types;

    try { // Read all declarations between for `(' and `)'
      if(signature.charAt(0) != '(')
	throw new ClassFormatError("Invalid method signature: " + signature);

      index = 1; // current string position

      while(signature.charAt(index) != ')') {
	vec.addElement(getType(signature.substring(index)));
	index += consumed_chars; // update position
      }
    } catch(StringIndexOutOfBoundsException e) { // Should never occur
      throw new ClassFormatError("Invalid method signature: " + signature);
    }
	
    types = new Type[vec.size()];
    vec.copyInto(types);
    return types;
  }
}
