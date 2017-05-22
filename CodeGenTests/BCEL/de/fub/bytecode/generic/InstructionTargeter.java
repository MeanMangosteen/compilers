package de.fub.bytecode.generic;

/**
 * Denote that a class targets InstructionHandles within an InstructionList. Namely
 * the following implementers:
 *
 * @see BranchHandle
 * @see LocalVariableGen
 * @see CodeExceptionGen
 * @version $Id: InstructionTargeter.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public interface InstructionTargeter {
  public boolean containsTarget(InstructionHandle ih);
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih);
}
