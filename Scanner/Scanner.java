/**
 * *	Scanner.java
 **/

package VC.Scanner;

import VC.ErrorReporter;

public final class Scanner {
  private static String[] spellings = new String[] {
    "boolean",
    "break",
    "continue",
    "else",
    "float",
    "for",
    "if",
    "int",
    "return",
    "void",
    "while",
    "+",
    "-",
    "*",
    "/",
    "!",
    "!=",
    "=",
    "==",
    "<",
    "<=",
    ">",
    ">=",
    "&&",
    "||",
    "{",
    "}",
    "(",
    ")",
    "[",
    "]",
    ";",
    ","
  };

    private SourceFile sourceFile;
    private boolean debug;

    private ErrorReporter errorReporter;
    private StringBuffer currentSpelling;
    private char currentChar;
    private SourcePosition sourcePos;

// =========================================================

    public Scanner(SourceFile source, ErrorReporter reporter) {
        sourceFile = source;
        errorReporter = reporter;
        currentChar = sourceFile.getNextChar();
        debug = false;

        // you may initialise your counters for line and column numbers here
    }

    public void enableDebugging() {
        debug = true;
    }

    // accept gets the next character from the source program.

    private void accept() {

        currentChar = sourceFile.getNextChar();
        if (currentChar == '\n') {
            System.out.println("the current char is the linefeed character");
        } else if (currentChar == '\r') {
            System.out.println("the current char is the carridge return character");
        } else {
            System.out.println("accept(): the curr char is now " + currentChar);
        }

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
	int retVal = -1;
        System.out.println("checkSeperators(): entered");
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
	    System.out.println("checkSeperators: recieved invalid token type");
        }
	if (retVal > 0) {
	    currentSpelling.append(spellings[retVal]);
	}
	return retVal;
    }
        
    private int checkOperators() {
        System.out.println("checkOperators(): entered");
    	switch(currentChar) { 
	    case '|':
	        accept();
	        if (currentChar == '|') {
	            accept();
	            return Token.OROR;
	        } else {
	            return Token.ERROR;
	        }
	    case '+':
	        accept();
	        return Token.PLUS;
	    case '-':
	        accept();
	        return Token.MINUS;
	    case '*':
	        accept();
	        return Token.MULT;
	    case '/':
	        accept();
	        return Token.DIV;
	    case '!':
	        accept();
	        if (currentChar == '=') {
	            accept();
	            return Token.NOTEQ;
	        }
	        else {
	            return Token.NOT;
	        }
	    case '=':
	        accept();
	        if (currentChar == '=') {
	            accept();
	            return Token.EQEQ;
	        } else {
	            return Token.EQ;
	        }
	    case '<':
	        accept();
	        if (currentChar == '=') {
	            accept();
	            return Token.LTEQ;
	        } else {
	            return Token.LT;
	        }
	    case '>':
	        accept();
	        if (currentChar == '=') {
	            accept();
	            return Token.GTEQ;
	        } else {
	            return Token.GT;
	        }
	    case '&':
	        accept();
	        if (currentChar == '&') {
	            accept();
	            return Token.ANDAND;
	        } else {
	            return Token.ERROR;
	        }
	   default:
		System.out.println("checkOperators: received invalid token type");
		return -1;
    	}
    }
    
    private int checkIdentifiers() {
        System.out.println("checkIdentifiers(): entered");
        // check for IDs
        // has to start with letter or underscore
        if (currentChar >= 'a' && currentChar <= 'z'
                || currentChar >= 'A' && currentChar <= 'Z'
                || currentChar == '_') {
            accept();
            // TODO: add to spelling
            while (true) {
                // now numbers in ID are valid
                if (currentChar >= 'a' && currentChar <= 'z'
                        || currentChar >= 'A' && currentChar <= 'Z'
                        || currentChar >= '0' && currentChar <= '9'
                        || currentChar == '_') {
                    // TODO: add to speeling
                    accept();
                    // the identifier spells a boolean value, 
                    if (currentSpelling.toString().equals("true") ||
                            currentSpelling.toString().equals("false")) {
                        return Token.ID;
                    }
                } else {
                    // we didn't find a valid char for ID, return
                    return Token.ID;
                }
            }
        } else {
            System.out.println("checkIdentifers: received invalid token type");
            return -1;
        }
    }
    
    private int checkLiterals() {
        System.out.println("checkLiterals(): entered");
       // TODO: this is checking for ID's, it should be checking for string literials 
        boolean isFloat = false;
        boolean isInt = false;
        if (currentChar == '"' ) {
            accept();
            // add to spelling until we find the terminting " keeping eating the chars
            // TODO: handle escaping of quotation marks
            while (currentChar != '"') {
            // TODO: add the spelling 
                accept();
            }
            accept();
            System.out.println("checkLiterals: we've hit the end of a string and now returning");
            return Token.STRINGLITERAL;
        } else {
            // check for all digits
            // check if any dot and any digits
            // check for exp and any digits
            while (currentChar >= '0' && currentChar <= '9') {
                // checking for int or float literals
                accept();
		isInt = true;
                // TODO: add to spelling 
            }
            if (currentChar == '.') {
                // it must be a float
                accept();
                while (true) {
                    // TODO: integrate 'if' into 'while'
                    // find all digits after dot
                    if (currentChar >= '0' && currentChar <= '9') {
                        accept();
                        // TODO;add to spelling
                    } else {
                        isFloat = true;
			isInt = false;
                        break;
                    }
                }
            } 
            if (currentChar == 'e' || currentChar == 'E') {
               // found exponent, must be a float
               accept();
                // find all digits after dot
                if (currentChar >= '0' && currentChar <= '9') {
                    accept();
                    while (true) {
                        if (currentChar >= '0' && currentChar <= '9') {
                            accept();
                            // TODO: add to spelling
                        } else {
                            isFloat = true;
			    isInt = false;
                            break;
                        }
                    }
                } else {
                    // there must be a digit after exponent
                    return Token.ERROR;
                }
            }
	    // TODO: string literal should be here as well;
            if (isFloat) {
                return Token.FLOATLITERAL;
            } else if(isInt) {
                return Token.INTLITERAL;
            } else {
                System.out.println("checkLiterals: received invalid token type");
                return -1;
            }
        }
    }

    // TODO: this should return an int
    private int checkSpecial() {
        System.out.println("checkSpecial(): entered");
            if (currentChar == SourceFile.eof) {
                return Token.EOF;
            } else {
                System.out.println("checkSpecial: received invalid token type");
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
		// TODO: remofe this
		System.out.println("tokenChecker: invalid token type");
		return Token.ERROR;
	}
    }

    private int nextToken() {
        // Tokens: separators, operators, literals, identifiers and keyworods
            int tokenID = -1;
        for (Tokens tokenType: Tokens.values()) {
            tokenID = tokenChecker(tokenType);
            if (tokenID >= 0) {
                return tokenID;
            }
        }
	// TODO: remove this
	System.out.println("nextToken: error in identifying token");
	return -1;
            /*case SourceFile.eof:
=======

            case SourceFile.eof:
>>>>>>> 72ae9d7913625bf0971709a0b36ac8ed30be3374
                currentSpelling.append(Token.spell(Token.EOF));
                return Token.EOF;
            default:
                break;*/
    }

    void skipSpaceAndComments() {
	System.out.println("skipSpaceAndComments(): entered");
	// skipping whitespace
	if (Character.isWhitespace(currentChar)) {
	    accept();
	    skipSpaceAndComments();
	}
        // skipping comments
        if (currentChar == '/') {
            if (inspectChar(1) == '/') {
                System.out.println("skipSpaceAndComents: comment detected"); 
                // if we find '//' remove all chars until we hit LF or CR
                while (currentChar != '\n' && currentChar != '\r') {
                    accept();
                }
                if (currentChar == '\r' && inspectChar(1) == '\n') {
                    // if CRLF then remove both
                    accept();
                    accept();
                } else {
                    // if either only CR or LF remove one char
                    accept();
                }
	    skipSpaceAndComments();
            }
        } else if (currentChar == '\n' || currentChar == '\r') {
            System.out.println("skipSpaceAndComents: there line terminator detected");
            // if the current token is a line terminator remove it
            if (currentChar == '\r' && inspectChar(1) == '\n') {
                System.out.println("skipSpaceAndComents: CRLF detected");
                // if CRLF then remove both
                accept();
                accept();
            } else {
                System.out.println("skipSpaceAndComents: either CR or LF detected");
                // if either only CR or LF remove one char
                // TODO: I have no idea why I need two accepts here
                accept();
            }
	    skipSpaceAndComments();
        } 
        // removing line terminators
    }

    public Token getToken() {
        Token tok;
        int kind;

        System.out.println("getToken: getting new token");

        // skip white space and comments

        skipSpaceAndComments();

        currentSpelling = new StringBuffer("");

        sourcePos = new SourcePosition();

        // You must record the position of the current token somehow

        kind = nextToken();

        // You need three types of information to contstruct a token objects
        // its kings represented as an 'int' (Token.ID, Token.PLUS)
        // its pselling represented as a string ("sum", "+")
        // its position in the program represented as an object of the lcass SourcePosition
        tok = new Token(kind, currentSpelling.toString(), sourcePos);

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
