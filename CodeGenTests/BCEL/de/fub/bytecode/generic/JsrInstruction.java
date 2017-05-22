package de.fub.bytecode.generic;

/** 
 * Super class for JSR - Jump to subroutine
 *
 * @version $Id: JsrInstruction.java,v 1.12 2001/07/03 08:20:18 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public abstract class JsrInstruction extends BranchInstruction
  implements UnconditionalBranch, TypedInstruction, StackProducer
{
  JsrInstruction(short opcode, InstructionHandle target) {
    super(opcode, target);
  }

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
  JsrInstruction(){}

  /** @return return address type
   */
  public Type getType(ConstantPoolGen cp) {
    return new ReturnaddressType(physicalSuccessor());
  }


 /**
  * Returns an InstructionHandle to the physical successor
  * of this JsrInstruction. <B>For this method to work,
  * this JsrInstruction object must not be shared between
  * multiple InstructionHandle objects!</B>
  * Formally, there must not be InstructionHandle objects
  * i, j where i != j and i.getInstruction() == this ==
  * j.getInstruction().
  * @return an InstructionHandle to the "next" instruction that
  * will be executed when RETurned from a subroutine.
  */
  public InstructionHandle physicalSuccessor(){
    InstructionHandle ih = this.target;
		
    // Rewind!
    while(ih.getPrev() != null)
      ih = ih.getPrev();
    
    // Find the handle for "this" JsrInstruction object.
    while(ih.getInstruction() != this)
      ih = ih.getNext();
    
    InstructionHandle toThis = ih;
    
    while(ih != null){
    	ih = ih.getNext();
    	if ((ih != null) && (ih.getInstruction() == this))
        throw new RuntimeException("physicalSuccessor() called on a shared JsrInstruction.");
    }
    
    // Return the physical successor		
    return toThis.getNext();
  }
}
