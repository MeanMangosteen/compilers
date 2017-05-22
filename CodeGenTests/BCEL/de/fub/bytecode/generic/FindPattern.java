package de.fub.bytecode.generic;

import de.fub.bytecode.Constants;
import de.fub.bytecode.classfile.*;
import gnu.regexp.*;

/** 
 * This class is an utility to search for given patterns, i.e., regular expressions
 * in an instruction list. This can be used in order to implement a
 * peep hole optimizer that looks for code patterns and replaces them with
 * faster equivalents.
 *
 * This class internally uses the package
 * <a href="http://www.cacas.org/~wes/java/">
 * gnu.regexp</a> to search for regular expressions.
 *
 * @version $Id: FindPattern.java,v 1.5 2001/10/11 11:59:27 dahm Exp $
 * @author  <A HREF="http://www.berlin.de/~markus.dahm/">M. Dahm</A>
 * @see     Instruction
 * @see     InstructionList
 * @see     CodeConstraint
 */
public class FindPattern {
  private static final int OFFSET     = 32767; // char + OFFSET is outside of LATIN-1
  private static final int NO_OPCODES = 256;   // Potential number, some are not used

  private static final String[] patterns = {
    "instruction", "branchinstruction", "if_icmp__", "if__", "push",
    "iload__", "aload__", "fload__", "dload__", "lload__",
    "istore__", "astore__", "fstore__", "dstore__", "lstore__",
    "invokeinstruction", "returninstruction", "ifinstruction"
  };

  private static String[] pattern_map; // filled in by static initializer, see below

  private InstructionList     il;
  private String              il_string;    // instruction list as string
  private InstructionHandle[] handles;      // map instruction list to array
  private int                 match_length; // Number of matched instructions
  private int                 matched_from; // Index of match in instruction list

  /**
   * @param il instruction list to search for given patterns
   */
  public FindPattern(InstructionList il) {
    this.il = il;
    reread();
  }

  /**
   * Rereads the instruction list, e.g., after you've altered the list upon a match.
   */
  public final void reread() {
    int    size  = il.getLength();
    char[] buf   = new char[size]; // Create a string with length equal to il length
    handles      = il.getInstructionHandles();

    match_length = -1; // reset match_length

    // Map opcodes to characters
    for(int i=0; i < size; i++)
      buf[i] = makeChar(handles[i].getInstruction().getOpcode());

    il_string = new String(buf);
  }

  /**
   * Map symbolic strings like `branchinstruction' or `'a regular expression string
   * such as (a|b|z) (where a,b,c whil be non-printable characters in LATIN-1)
   *
   * @param pattern instruction pattern in lower case
   * @return encoded string for a pattern such as "BranchInstruction".
   */
  private static final String getPattern(String pattern) {
    // Check for abbreviations
    for(int i=0; i < patterns.length; i++) {
      if(pattern.equals(patterns[i]))
	return pattern_map[i]; // return the string mapped to that name
    }

    // Check for opcode names
    for(short i=0; i < NO_OPCODES; i++)
      if(pattern.equals(Constants.OPCODE_NAMES[i]))
	return new String(new char[] { makeChar(i) });

    return null; // Failed to match
  }

  /**
   * Replace all occurences of `something' with the appropiate pattern, the `' chars
   * are used as an escape sequence.
   * Other characters than the escaped one will be ignored, in particular the meta
   * characters used for regular expression such as *, +, [, etc.
   *
   * @param pattern The pattern to compile
   * @return complete regular expression string
   */
  private static final String makePattern(String pattern) {
    String       lower      = pattern.toLowerCase();
    StringBuffer buf        = new StringBuffer();
    int          size       = pattern.length();
    boolean      in_pattern = false; // remember current state
    StringBuffer collect    = null;

    try {
      for(int i=0; i < size; i++) {
	char ch = lower.charAt(i);
	
	switch(ch) {
	case '`': // Start of instruction pattern
	  if(in_pattern)
	    throw new ClassGenException("` within `' block.");

	  collect    = new StringBuffer();
	  in_pattern = true; // remember current state
	  break;

	case '\'': // end of instruction pattern
	  if(!in_pattern)
	    throw new ClassGenException("' without starting `.");
	  
	  in_pattern = false;
	  String str = collect.toString(); // String within the `'
	  String pat = getPattern(str);

	  if(pat == null)
	    throw new ClassGenException("Unknown instruction pattern: \"" + str +
					"\"" + " at index " + i);
	  buf.append(pat);
	  break;

	default:
	  if(in_pattern)
	    collect.append(ch);
	  else
	    buf.append(ch); // Just append it (meta character)
	}
      }
    } catch(StringIndexOutOfBoundsException e) {
      e.printStackTrace();
    }

    return buf.toString();
  }

  /**
   * Search for the given pattern in the InstructionList. You may use the following
   * special expressions in your pattern string which match instructions that belong
   * to the denoted class. The `' are an escape and <b>must not</b> be omitted.
   *
   * You can use the Instruction names directly:
   *
   * `ILOAD_1', `GOTO', 'NOP', etc..
   *
   * For convenience there exist some abbreviations for instructions that belong
   * to the same group (underscores _ are used as some kind of wildcards):
   *
   * `Instruction', `BranchInstruction', `InvokeInstruction', `ReturnInstruction',
   * `IfInstruction' correspond to their classes.
   *
   * `IF_ICMP__', `IF__', where __ stands for EQ, LE, etc.
   * `xLOAD__', `xSTORE__', where x stands for I, D, F, L or A. __ is 0..3 or empty
   * `PUSH' stands for any LDC, xCONST__, SIPUSH or BIPUSH instruction
   *
   * You <B>must</B> put the `' around these words or they can't be matched correctly.
   *
   * For the rest the usual (PERL) pattern matching rules apply.<P>
   * Example pattern:
   * <pre>
     search("(`BranchInstruction')`NOP'((`IF_ICMP__'|`GOTO')+`ISTORE__'`Instruction')*");
   * </pre>
   *
   *
   * @param pattern the instruction pattern to search for, case is ignored
   * @param from where to start the search in the instruction list
   * @param constraint optional CodeConstraint to check the found code pattern for
   * given constraints
   * @return instruction handle or `null' if the matching fails
   */
  public final InstructionHandle search(String pattern, InstructionHandle from,
					CodeConstraint constraint) {
    String search = makePattern(pattern);
    int  start    = -1;

    match_length = matched_from = -1; // reset

    for(int i=0; i < handles.length; i++) {
      if(handles[i] == from) {
	start = i; // Where to start search from (index)
	break;
      }
    }

    if(start == -1)
      throw new ClassGenException("Instruction handle " + from + 
				  " not found in instruction list.");

    try {
      RE      regex = new RE(search);
      REMatch r     = regex.getMatch(il_string, start);
      
      if(r != null) {
	matched_from = r.getStartIndex();
	match_length = (r.getEndIndex() - matched_from);

	if((constraint == null) || constraint.checkCode(getMatch()))
	  return handles[matched_from];
      }
    } catch(REException e) {
      System.err.println(e);
    }

    return null;
  }

  /**
   * Start search beginning from the start of the given instruction list.
   *
   * @param pattern the instruction pattern to search for, case is ignored
   * @return instruction handle or `null' if the matching fails
   */
  public final InstructionHandle search(String pattern) {
    return search(pattern, il.getStart(), null);
  }

  /**
   * Start search beginning from `from'.
   *
   * @param pattern the instruction pattern to search for, case is ignored
   * @param from where to start the search in the instruction list
   * @return instruction handle or `null' if the matching fails
   */
  public final InstructionHandle search(String pattern, InstructionHandle from) {
    return search(pattern, from, null);
  }

  /**
   * Start search beginning from the start of the given instruction list.
   * Check found matches with the constraint object.
   *
   * @param pattern the instruction pattern to search for, case is ignored
   * @param constraint constraints to be checked on matching code
   * @return instruction handle or `null' if the match failed
   */
  public final InstructionHandle search(String pattern, CodeConstraint constraint) {
    return search(pattern, il.getStart(), constraint);
  }

  /**
   * @return number of matched instructions, or -1 if the match did not succeed
   */
  public final int getMatchLength() { return match_length; }

  /**
   * @return the matched piece of code as an array of instruction (handles)
   */
  public final InstructionHandle[] getMatch() {
    if(match_length == -1)
      throw new ClassGenException("Nothing matched.");

    InstructionHandle[] match = new InstructionHandle[match_length];
    System.arraycopy(handles, matched_from, match, 0, match_length);

    return match;
  }

  /**
   * Defines a new instruction list. Automatically calls
   * <a href="#reread()">reread()</a> to update the object.
   *
   * @param il the new instuction list
   */
  public final void setInstructionList(InstructionList il) {
    this.il = il;
    reread();
  }

  /**
   * Internal debugging routines.
   */
  private static final String pattern2string(String pattern) {
    return pattern2string(pattern, true);
  }

  private static final String pattern2string(String pattern, boolean make_string) {
    StringBuffer buf = new StringBuffer();

    for(int i=0; i < pattern.length(); i++) {
      char ch = pattern.charAt(i);

      if(ch >= OFFSET) {
	if(make_string)
	  buf.append(Constants.OPCODE_NAMES[ch - OFFSET]);
	else
	  buf.append((int)(ch - OFFSET));
      }
      else
	buf.append(ch);
    }

    return buf.toString();
  }


  /* Static initializer.
   */
  static {

    String instruction_pattern, binstruction_pattern, if_icmp_pattern, if_pattern,
      push_pattern, iload_pattern, aload_pattern, fload_pattern,
      dload_pattern, lload_pattern, istore_pattern, astore_pattern,
      fstore_pattern, dstore_pattern, lstore_pattern, invoke_pattern, return_pattern,
      if_pattern2;
    
    StringBuffer buf;

    /* Make instruction string
     */
    buf = new StringBuffer("(");
    
    for(short i=0; i < NO_OPCODES; i++) {
      if(Constants.NO_OF_OPERANDS[i] != Constants.UNDEFINED) { // Not an invalid opcode
	buf.append(makeChar(i));

	if(i < NO_OPCODES - 1)
	  buf.append('|');
      }
    }
    buf.append(')');

    instruction_pattern = buf.toString();

    /* Make BranchInstruction string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.IFEQ, Constants.LOOKUPSWITCH);
    buf.append('|');
    appendPatterns(buf, Constants.IFNULL, Constants.JSR_W);
    buf.append(')');
    binstruction_pattern = buf.toString();
 
    /* Make IF_ICMP__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.IF_ICMPEQ, Constants.IF_ICMPLE);
    buf.append(')');
    if_icmp_pattern = buf.toString();

    /* Make IF__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.IFEQ, Constants.IFLE);
    buf.append(')');
    if_pattern = buf.toString();

    /* Make PUSH pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.ACONST_NULL,Constants. LDC2_W);
    buf.append(')');
    push_pattern = buf.toString();

    /* Make ILOAD__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.ILOAD_0, Constants.ILOAD_3);
    buf.append('|');
    buf.append(makeChar(Constants.ILOAD));
    buf.append(')');
    iload_pattern = buf.toString();

    /* Make ALOAD__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.ALOAD_0, Constants.ALOAD_3);
    buf.append('|');
    buf.append(makeChar(Constants.ALOAD));
    buf.append(')');
    aload_pattern = buf.toString();

    /* Make FLOAD__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.FLOAD_0, Constants.FLOAD_3);
    buf.append('|');
    buf.append(makeChar(Constants.FLOAD));
    buf.append(')');
    fload_pattern = buf.toString();

    /* Make DLOAD__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.DLOAD_0, Constants.DLOAD_3);
    buf.append('|');
    buf.append(makeChar(Constants.DLOAD));
    buf.append(')');
    dload_pattern = buf.toString();

    /* Make LLOAD__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.LLOAD_0, Constants.LLOAD_3);
    buf.append('|');
    buf.append(makeChar(Constants.LLOAD));
    buf.append(')');
    lload_pattern = buf.toString();

    /* Make ISTORE__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.ISTORE_0, Constants.ISTORE_3);
    buf.append('|');
    buf.append(makeChar(Constants.ISTORE));
    buf.append(')');
    istore_pattern = buf.toString();

    /* Make ASTORE__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.ASTORE_0, Constants.ASTORE_3);
    buf.append('|');
    buf.append(makeChar(Constants.ASTORE));
    buf.append(')');
    astore_pattern = buf.toString();

    /* Make FSTORE__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.FSTORE_0, Constants.FSTORE_3);
    buf.append('|');
    buf.append(makeChar(Constants.FSTORE));
    buf.append(')');
    fstore_pattern = buf.toString();

    /* Make DSTORE__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.DSTORE_0, Constants.DSTORE_3);
    buf.append('|');
    buf.append(makeChar(Constants.DSTORE));
    buf.append(')');
    dstore_pattern = buf.toString();

    /* Make LSTORE__ pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.LSTORE_0, Constants.LSTORE_3);
    buf.append('|');
    buf.append(makeChar(Constants.LSTORE));
    buf.append(')');
    lstore_pattern = buf.toString();

    /* Make INVOKE pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.INVOKEVIRTUAL, Constants.INVOKEINTERFACE);
    buf.append(')');
    invoke_pattern = buf.toString();

    /* Make RETURN pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.IRETURN, Constants.RETURN);
    buf.append(')');
    return_pattern = buf.toString();

    /* Make IfInstruction pattern string
     */
    appendPatterns(buf = new StringBuffer("("), Constants.IFEQ, Constants.IF_ACMPNE);
    buf.append('|');
    buf.append(makeChar(Constants.IFNULL));
    buf.append('|');
    buf.append(makeChar(Constants.IFNONNULL));
    buf.append(')');
    if_pattern2 = buf.toString();

    pattern_map = new String[] {
      instruction_pattern, binstruction_pattern, if_icmp_pattern, if_pattern,
      push_pattern, iload_pattern, aload_pattern, fload_pattern,
      dload_pattern, lload_pattern, istore_pattern, astore_pattern,
      fstore_pattern, dstore_pattern, lstore_pattern, invoke_pattern, return_pattern,
      if_pattern2
    };
  }

  /**
   * Convert opcode number to char.
   */
  private static final char makeChar(short opcode) {
    return (char)(opcode + OFFSET);
  }

  /**
   * Append instructions characters starting from `start' to `to'.
   */
  private final static void appendPatterns(StringBuffer buf, short from, short to) {
    for(short i=from; i <= to; i++) {
      buf.append(makeChar(i));

      if(i < to)
	buf.append('|');
    }
  }

  /**
   * @return the inquired instruction list
   */
  public final InstructionList getInstructionList() { return il; }
}
