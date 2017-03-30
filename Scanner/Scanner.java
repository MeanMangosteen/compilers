/**
 * *	Scanner.java
 **/

package VC.Scanner;

import VC.ErrorReporter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public final class Scanner { 

	private SourceFile sourceFile;
	private boolean debug;
	private boolean printDebug = false;
	private boolean currentCharIsEscape = false;
	private boolean skipSpelling = false;

	private ErrorReporter errorReporter;
	private StringBuffer currentSpelling = new StringBuffer(""); 

	private char currentChar;
	private char prevChar;

	private SourcePosition sourcePos = new SourcePosition(1,1,0);
	private SourcePosition tokenPos;
	private SourcePosition errorPos;

	private List<Character> escapeChars = new ArrayList<Character>(Arrays.asList('b', 'f', 'n', 'r', 't', '\'', '"', '\\'));
	private int charsBeforeTab = 0;
	// =========================================================

	public Scanner(SourceFile source, ErrorReporter reporter) {
		sourceFile = source;
		errorReporter = reporter;
		accept();
		debug = false;

		// you may initialise your counters for line and column numbers here
	}

	public void enableDebugging() {
		debug = true;
	}

	private int getTabLength() {
		return 8 - (charsBeforeTab % 8);
	}

	// accept gets the next character from the source program.

	private void accept() {
		if (currentCharIsEscape) {
			if (printDebug) System.out.println("accept(): current char is escape");
			// reset the flag
			currentCharIsEscape = false;
			// current char is backslash, make it actual escape char
			currentChar = sourceFile.getNextChar();
			// TODO: maybe just make this recurision?
			sourcePos.charFinish++;
			charsBeforeTab++;
			// transform that letter to esacpe char literal
			currentChar = getEscapeChar(currentChar);
		}
		prevChar = currentChar;
		currentChar = sourceFile.getNextChar();
		if (printDebug) System.out.println("accept(): the prev char is " + prevChar);
		if (skipSpelling) {
			// reset the flag
			skipSpelling = false;
		} else {
			if (prevChar != 0)
				currentSpelling.append(prevChar);
		}
		if (printDebug) System.out.println("accpet(): current char is " + currentChar);
		// re
		if (currentChar == '\t') {
			tokenPos = new SourcePosition(sourcePos.lineFinish, sourcePos.charStart, sourcePos.charFinish);
			if (printDebug) System.out.println("accept(): tab characterr");
			sourcePos.charFinish += getTabLength();
			charsBeforeTab = 0;
		} else if (currentChar == '\n') {
			tokenPos = new SourcePosition(sourcePos.lineFinish, sourcePos.charStart, sourcePos.charFinish);
			if (printDebug) System.out.println("accept(): newline characterr");
			sourcePos.charStart = 1;
			sourcePos.charFinish = 0;
			sourcePos.lineFinish++;
			charsBeforeTab = 0;
		} else if (currentChar == SourceFile.eof) {
			sourcePos.charStart = sourcePos.charFinish = 1;
			tokenPos = new SourcePosition(sourcePos.lineFinish, 1, 1);
		} else {
			sourcePos.charFinish++;
			charsBeforeTab++;
		}
		if (printDebug) System.out.println("accept(): charStart is " + sourcePos.charStart);
		if (printDebug) System.out.println("accpet(): charFinish is " + sourcePos.charFinish);
		if (printDebug) System.out.println("accpet(): number of chars before tab is " + charsBeforeTab);


		// you may save the lexeme of the current token incrementally here
		// you may also increment your line and column counters here
	}

	// inspectChar returns the n-th character after currentChar
	// in the input stream.
	//
	// If there are fewer than nthChar characters between currentChar
	// and the end of file marker, SourceFile.eof is returned.
	//
	// Both currentChar and the current position in the input stream
	// are *not* changed. Therefore, a subsequent call to accept()
	// will always return the next char after currentChar.

	private char inspectChar(int nthChar) {
		return sourceFile.inspectChar(nthChar);
	}

	private int checkSeperators() {
		if (printDebug) System.out.println("checkSeperators(): entered");
		int retVal = -1;

		switch (currentChar) {
			case '(':
				accept();
				retVal = Token.LPAREN;
				break;
			case ')':
				accept();
				retVal = Token.RPAREN;
				break;
			case '{':
				accept();
				retVal = Token.LCURLY;
				break;
			case '}':
				accept();
				retVal = Token.RCURLY;
				break;
			case '[':
				accept();
				retVal = Token.LBRACKET;
				break;
			case ']':
				accept();
				retVal = Token.RBRACKET;
				break;
			case ';':
				accept();
				retVal = Token.SEMICOLON;
				break;
			case ',':
				accept();
				retVal = Token.COMMA;
				break;
			default:

		}
		return retVal;
	}

	private int checkOperators() {
		if (printDebug) System.out.println("checkOperators(): entered");
		int retVal = -1;
		// TODO: find a way to add spelling 

		switch(currentChar) { 
			case '|':
				if (inspectChar(1) == '|') {
					// accept twice cos two characters
					accept();
					accept();
					retVal = Token.OROR;
				}
				break;
			case '+':
				accept();
				retVal = Token.PLUS;
				break;
			case '-':
				accept();
				retVal = Token.MINUS;
				break;
			case '*':
				accept();
				retVal = Token.MULT;
				break;
			case '/':
				accept();
				retVal = Token.DIV;
				break;
			case '!':
				if (inspectChar(1) == '=') {
					accept(); accept();
					retVal = Token.NOTEQ;
				}
				else {
					retVal = Token.NOT;
					accept();
				}
				break;
			case '=':
				if (inspectChar(1) == '=') {
					accept(); accept();
					retVal = Token.EQEQ;
				} else {
					retVal = Token.EQ;
					accept();
				}
				break;
			case '<':
				if (inspectChar(1) == '=') {
					accept(); accept();
					retVal = Token.LTEQ;
				} else {
					retVal = Token.LT;
					accept();
				}
				break;
			case '>':
				if (inspectChar(1)== '=') {
					accept();accept();
					retVal = Token.GTEQ;
				} else {
					retVal = Token.GT;
					accept();
				}
				break;
			case '&':
				if (inspectChar(1) == '&') {
					accept(); accept();
					retVal = Token.ANDAND;
				}
				break;
			default:

		}
		return retVal;
	}

	private int checkIdentifiers() {
		if (printDebug) System.out.println("checkIndentifiers(): entered");

		// check for IDs
		// has to start with letter or underscore
		if (currentChar >= 'a' && currentChar <= 'z'
				|| currentChar >= 'A' && currentChar <= 'Z'
				|| currentChar == '_') {
			accept();
			while (true) {
				// now numbers in ID are valid
				if (currentChar >= 'a' && currentChar <= 'z'
						|| currentChar >= 'A' && currentChar <= 'Z'
						|| currentChar >= '0' && currentChar <= '9'
						|| currentChar == '_') {
					accept();
					// the identifier spells a boolean value, 
					if (currentSpelling.toString().equals("true") ||
							currentSpelling.toString().equals("false")) {
						return Token.BOOLEANLITERAL;
							}
				} else {
					// we didn't find a valid char for ID, return
					return Token.ID;
				}
			}
		} else {

			return -1;
		}
	}

	private char getEscapeChar(char ch) {

		char retVal = Character.MIN_VALUE;
		if (ch == 'b') {
			retVal = '\b';
		} else if (ch == 'r') {
			retVal = '\r';
		} else if (ch == 'n') {
			retVal = '\n';
		} else if (ch == 't') {
			retVal = '\t';
		} else if (ch == 'f') {
			retVal = '\f';
		} else if (ch == '\'') {
			retVal = '\'';
		} else if (ch == '"') {
			retVal = '"';
		} else if (ch == '\\') {
			retVal = '\\';
		} else {

		}
		return retVal;
	}

	private int checkLiterals() {
		if (printDebug) System.out.println("checkLiterals(): entered");
		boolean isFloat = false;
		boolean isInt = false;
		int retVal = -1;
		if (currentChar == '"' ) {
			// skip the quotation 
			skipSpelling = true;
			accept();
			while (currentChar != '"') {
				// TODO: check for CRLF as well, maybe you might have to accept twice
				if (currentChar == '\n' || currentChar == '\r') {
					errorPos = new SourcePosition(sourcePos.lineFinish-1, tokenPos.charStart, tokenPos.charStart);
					errorReporter.reportError("%: unterminated string", currentSpelling.toString(), errorPos);
					return Token.STRINGLITERAL;
				} else if (currentChar == '\\') {
					if (escapeChars.contains(inspectChar(1))) {
						currentCharIsEscape = true;
					} else {
						String illegal_escape = new StringBuilder().append("").append(currentChar).append(inspectChar(1)).toString();
						errorPos = new SourcePosition(sourcePos.lineFinish, sourcePos.charStart, sourcePos.charFinish);
						errorReporter.reportError("%: illegal escape character", illegal_escape, errorPos);
					}
				}
				accept();
			}
			// move forward once for the extra quotation
			skipSpelling = true;
			accept();
			return Token.STRINGLITERAL;
		} 
		// checking for int digits
		if (currentChar >= '0' && currentChar <= '9') {
			retVal = Token.INTLITERAL;
			accept();
			while (currentChar >= '0' && currentChar <= '9') {
				accept();
			}
		}
		// checking for decimal dot
		if (currentChar == '.') {
			// if int becomes float as soon as there is a dot
			if (inspectChar(1) >= '0' && inspectChar(1) <= '9') {
				// take the dot
				accept();
				retVal = Token.FLOATLITERAL;
				while (currentChar >= '0' && currentChar <= '9') {
					accept();
				}
			} else if (retVal == Token.INTLITERAL) {
				accept();
				retVal = Token.FLOATLITERAL;
			}
			// accept, otherwise we'll get duplicate reading
		}
		// checking for exponentials
		if (currentChar == 'e' || currentChar == 'E') {
			// we definately have a number
			if (inspectChar(2) >= '0' && inspectChar(2) <= '9' && (inspectChar(1) == '+' || inspectChar(1) == '-')) {
				accept();
			}
			if (inspectChar(1) >= '0' && inspectChar(1) <= '9') {
				if (retVal == Token.INTLITERAL || retVal == Token.FLOATLITERAL) {
					accept();
					// look ahead one char, if number accept and add to spelling
					while (currentChar >= '0' && currentChar <= '9' || currentChar == '+' || currentChar == '-') {
						accept();
						// exponent only valid if int or float
						if (retVal == Token.INTLITERAL || retVal == Token.FLOATLITERAL) {
							retVal = Token.FLOATLITERAL;
						}
						// accept, otherwise we'll get duplicate reading
					}
				}
			}
		}
		return retVal;
	}

	// TODO: this should return an int
	private int checkSpecial() {
		if (printDebug) System.out.println("checkSpecial(): entered");

		if (currentChar == SourceFile.eof) {
			currentSpelling.append(Token.spell(Token.EOF));
			return Token.EOF;
		} else {
			return -1;
		}

	}

	private enum Tokens {
		SEPERATORS, OPERATORS, LITERALS,
			IDENTIFIERS, KEYWORDS, SPECIAL
	}

	private int tokenChecker(Tokens tokenType) {
		switch (tokenType) {
			case SEPERATORS:
				return checkSeperators();
			case OPERATORS: 
				return checkOperators();
			case LITERALS:
				return checkLiterals();
			case IDENTIFIERS:
				return checkIdentifiers();
			case KEYWORDS:
				return checkIdentifiers();
			case SPECIAL:
				return checkSpecial();
			default:

				return -1;
		}
	}

	private int nextToken() {
		// Tokens: separators, operators, literals, identifiers and keyworods
		int tokenID = -1;
		for (Tokens tokenType: Tokens.values()) {
			tokenID = tokenChecker(tokenType);
			if (tokenID >= 0) {
				if (currentChar != SourceFile.eof && currentChar != '\t' && currentChar != '\n') {
					tokenPos = new SourcePosition(sourcePos.lineFinish, sourcePos.charStart, sourcePos.charFinish-1);
				}
				return tokenID;
			}
		}
		// tokenPos already set in accept() if newline character
		accept();
		if (currentChar != '\n')
			tokenPos = new SourcePosition(sourcePos.lineFinish, sourcePos.charStart, sourcePos.charFinish-1);

		// TODO: this should never pass
		// erroneous token spelling
		if (currentSpelling.toString().equals("")) {
			currentSpelling.append(currentChar);
		}
		// to flush the errenous curr char out
		//accept();
		return Token.ERROR;
		// TODO: remove this
		/*case SourceFile.eof:
		  =======

		  case SourceFile.eof:
		  >>>>>>> 72ae9d7913625bf0971709a0b36ac8ed30be3374
		  currentSpelling.append(Token.spell(Token.EOF));
		  return Token.EOF;
		  default:
		  break;*/
	}

	boolean isNewline() {
		return false;
	}

	void skipSpaceAndComments() {
		if (printDebug) System.out.println("skipSpaceAndComments(): entered");
		// TODO: handle multiline comments

		// skipping whitespace
		if (Character.isWhitespace(currentChar)) {
			accept();
			skipSpaceAndComments();
		}
		// skipping comments
		if (currentChar == '/') {
			if (inspectChar(1) == '/') {

				// if we find '//' remove all chars until we hit LF or CR
				while (currentChar != '\n' && currentChar != '\r') {
					accept();
				}
				// we've hit the new line, now remove the newline
				if (currentChar == '\r' && inspectChar(1) == '\n') {
					// if CRLF then remove both
					accept();
					accept();
				} else {
					// if either only CR or LF remove one char
					accept();
				}
				skipSpaceAndComments();
			} else if (inspectChar(1) == '*') {
				// multiline comment

				accept(); accept();
				while (true) {
					if (currentChar == '*' && inspectChar(1) == '/') {

						accept(); accept();
						skipSpaceAndComments();
						break;
					} else if (currentChar == SourceFile.eof) {
						errorPos = new SourcePosition(sourcePos.lineFinish-1, tokenPos.charFinish, tokenPos.charFinish);
						errorReporter.reportError("unterminated comment", "/**/", errorPos);
						break;
					}
					accept();
				}
			}
		} else if (currentChar == '\n' || currentChar == '\r') {

			// if the current token is a line terminator remove it
			if (currentChar == '\r' && inspectChar(1) == '\n') {

				// if CRLF then remove both
				accept();
				accept();
			} else {

				// if either only CR or LF remove one char
				// TODO: I have no idea why I need two accepts here
				accept();
			}
			skipSpaceAndComments();
		} 
		// removing line terminators
	}

	public Token getToken() {
		// this would possibly be whitespace or the start of the next token
		if (printDebug) System.out.println("getToken(): the current char is " + currentChar);
		Token tok;
		int kind;


		skipSpaceAndComments();
		if (printDebug) System.out.println("getToken(): exited skipSpaceAndComments():" );
		
		// gotten rid of white space start token spelling
		currentSpelling = new StringBuffer("");

		sourcePos.charStart = sourcePos.charFinish;

		errorReporter = new ErrorReporter();

		// skip white space and comments

		// You must record the position of the current token somehow

		kind = nextToken();

		// You need three types of information to contstruct a token objects
		// its kings represented as an 'int' (Token.ID, Token.PLUS)
		// its pselling represented as a string ("sum", "+")
		// its position in the program represented as an object of the lcass SourcePosition
		tok = new Token(kind, currentSpelling.toString(), tokenPos);

		// * do not remove these three lines
		if (debug)
			System.out.println(tok);
		return tok;
	}

}

// QUESTION:
// To what extent do we remove all whitespace?
//      if we have the int 33 and the float 4.4
//      and we remove white space to become 334.4
//      how do we now recognize these as two tokens.
