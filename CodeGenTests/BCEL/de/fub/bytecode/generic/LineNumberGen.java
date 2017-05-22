package de.fub.bytecode.generic;

import de.fub.bytecode.Constants;
import de.fub.bytecode.classfile.*;

/** 
 * This class represents a line number within a method, i.e., give an instruction
 * a line number corresponding to the source code line.
 *
 * @version $Id: LineNumberGen.java,v 1.4 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     LineNumber
 * @see     MethodGen
 */
public class LineNumberGen implements InstructionTargeter, Cloneable {
  private InstructionHandle ih;
  private int               src_line;

  /**
   * Create a line number.
   *
   * @param ih instruction handle to reference
   * @return new line number object
   */
  public LineNumberGen(InstructionHandle ih, int src_line) {
    setInstruction(ih);
    setSourceLine(src_line);
  }

  /**
   * @return true, if ih is target of this line number
   */
  public boolean containsTarget(InstructionHandle ih) {
    return this.ih == ih;
  }

  /**
   * @param old_ih old target
   * @param new_ih new target
   */
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
    if(old_ih != ih)
      throw new ClassGenException("Not targeting " + old_ih + ", but " + ih + "}");
    else
      setInstruction(new_ih);
  }

  /**
   * Get LineNumber attribute.
   *
   * This relies on that the instruction list has already been dumped to byte code or
   * or that the `setPositions' methods has been called for the instruction list.
   *
   * @param cp constant pool
   * @deprecated cp not needed
   */
  public LineNumber getLineNumber(ConstantPoolGen cp) {
    return getLineNumber();
  }

  /**
   * Get LineNumber attribute .
   *
   * This relies on that the instruction list has already been dumped to byte code or
   * or that the `setPositions' methods has been called for the instruction list.
   */
  public LineNumber getLineNumber() {
    return new LineNumber(ih.getPosition(), src_line);
  }

  public void setInstruction(InstructionHandle ih) {
    BranchInstruction.notifyTarget(this.ih, ih, this);

    this.ih = ih;
  }

  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      System.err.println(e);
      return null;
    }
  }

  public InstructionHandle getInstruction()               { return ih; }
  public void              setSourceLine(int src_line)    { this.src_line = src_line; }
  public int               getSourceLine()                { return src_line; }
}
