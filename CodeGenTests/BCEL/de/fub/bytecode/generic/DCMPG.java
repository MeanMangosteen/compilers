package de.fub.bytecode.generic;

/** 
 * DCMPG - Compare doubles: value1 > value2
 * <PRE>Stack: ..., value1.word1, value1.word2, value2.word1, value2.word2 -&gt;</PRE>
 *        ..., result
 *
 * @version $Id: DCMPG.java,v 1.7 2001/05/09 09:26:57 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 */
public class DCMPG extends Instruction
  implements TypedInstruction, StackProducer, StackConsumer {

  public DCMPG() {
    super(de.fub.bytecode.Constants.DCMPG, (short)1);
  }

  /** @return Type.DOUBLE
   */
  public Type getType(ConstantPoolGen cp) {
    return Type.DOUBLE;
  }


  /**
   * Call corresponding visitor method(s). The order is:
   * Call visitor methods of implemented interfaces first, then
   * call methods according to the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitTypedInstruction(this);
    v.visitStackProducer(this);
    v.visitStackConsumer(this);
    v.visitDCMPG(this);
  }
}
