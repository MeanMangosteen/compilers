package de.fub.bytecode.generic;
import de.fub.bytecode.Constants;

/** 
 * Denotes basic type such as int.
 *
 * @version $Id: BasicType.java,v 1.4 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public final class BasicType extends Type {
  /**
   * Constructor for basic types such as int, long, `void'
   *
   * @param type one of T_INT, T_BOOLEAN, ..., T_VOID
   * @see de.fub.bytecode.Constants
   */
  BasicType(byte type) {
    super(type, Constants.SHORT_TYPE_NAMES[type]);

    if((type < Constants.T_BOOLEAN) || (type > Constants.T_VOID))
      throw new ClassGenException("Invalid type: " + type);
  }

  public static final BasicType getType(byte type) {
    switch(type) {
    case Constants.T_VOID:    return VOID;
    case Constants.T_BOOLEAN: return BOOLEAN;
    case Constants.T_BYTE:    return BYTE;
    case Constants.T_SHORT:   return SHORT;
    case Constants.T_CHAR:    return CHAR;
    case Constants.T_INT:     return INT;
    case Constants.T_LONG:    return LONG;
    case Constants.T_DOUBLE:  return DOUBLE;
    case Constants.T_FLOAT:   return FLOAT;

    default:
      throw new ClassGenException("Invalid type: " + type);
    }
  }

  /** @return true if both type objects refer to the same type
   */
  public boolean equals(Object type) {
    return (type instanceof BasicType)?
      ((BasicType)type).type == this.type : false;
  }
}
