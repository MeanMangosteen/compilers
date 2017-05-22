package de.fub.bytecode.generic;

/**
 * BranchHandle is returned by specialized InstructionList.append() whenever a
 * BranchInstruction is appended. This is useful when the target of this
 * instruction is not known at time of creation and must be set later
 * via setTarget().
 *
 * @see InstructionHandle
 * @see Instruction
 * @see InstructionList
 * @version $Id: BranchHandle.java,v 1.2 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public final class BranchHandle extends InstructionHandle {
  private BranchInstruction bi; // An alias in fact, but saves lots of casts

  private BranchHandle(BranchInstruction i) {
    super(i);
    bi = i;
  }

  /** Factory methods.
   */
  private static BranchHandle bh_list = null; // List of reusable handles

  static final BranchHandle getBranchHandle(BranchInstruction i) {
    if(bh_list == null)
      return new BranchHandle(i);
    else {
      BranchHandle bh = bh_list;
      bh_list = (BranchHandle)bh.next;

      bh.setInstruction(i);

      return bh;
    }
  }
  
  /** Handle adds itself to the list of resuable handles.
   */
  protected void addHandle() {
    next    = bh_list;
    bh_list = this;
  }

  /* Override InstructionHandle methods: delegate to branch instruction.
   * Through this overriding all access to the private i_position field should
   * be prevented.
   */
  public int getPosition() { return bi.position; }

  void setPosition(int pos) {
    i_position = bi.position = pos;
  }

  protected int updatePosition(int offset, int max_offset) {
    int x = bi.updatePosition(offset, max_offset);
    i_position = bi.position;
    return x;
  }

  /**
   * Pass new target to instruction.
   */
  public void setTarget(InstructionHandle ih) {
    bi.setTarget(ih);
  }

  /**
   * Update target of instruction.
   */
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
    bi.updateTarget(old_ih, new_ih);
  }

  /**
   * @return target of instruction.
   */
  public InstructionHandle getTarget() {
    return bi.getTarget();
  }

  /** 
   * Set new contents. Old instruction is disposed and may not be used anymore.
   */
  public void setInstruction(Instruction i) {
    super.setInstruction(i);

    if(!(i instanceof BranchInstruction))
      throw new ClassGenException("Assigning " + i +
				  " to branch handle which is not a branch instruction");

    bi = (BranchInstruction)i;
  }
}

