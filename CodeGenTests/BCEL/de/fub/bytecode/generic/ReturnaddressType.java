package de.fub.bytecode.generic;
import de.fub.bytecode.Constants;
import de.fub.bytecode.generic.InstructionHandle;

/** 
 * Returnaddress, the type JSR or JSR_W instructions push upon the stack.
 *
 * see vmspec2 §3.3.3
 * @version $Id: ReturnaddressType.java,v 1.3 2001/04/18 08:14:24 dahm Exp $
 * @author  <A HREF="http://www.inf.fu-berlin.de/~ehaase">Enver Haase</A>
 */
public class ReturnaddressType extends Type {

  public static final ReturnaddressType NO_TARGET = new ReturnaddressType();
  private InstructionHandle returnTarget;
 
  /**
   * A Returnaddress [that doesn't know where to return to].
   */
  private ReturnaddressType(){
    super(Constants.T_ADDRESS, "<return address>");
  }
 	
  /**
   * Creates a ReturnaddressType object with a target.
   */
  public ReturnaddressType(InstructionHandle returnTarget) {
    super(Constants.T_ADDRESS, "<return address targeting "+returnTarget+">");
  	this.returnTarget = returnTarget;
  }
	
  /**
   * Returns if the two Returnaddresses refer to the same target.
   */
  public boolean equals(Object rat){
    if(!(rat instanceof ReturnaddressType))
      return false;

    return ((ReturnaddressType)rat).returnTarget.equals(this.returnTarget);
  }	

  /**
   * @return the target of this ReturnaddressType
   */
  public InstructionHandle getTarget(){
    return returnTarget;
  }
}
