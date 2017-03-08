/**
 * *	Scanner.java
 **/

package VC.Scanner;

import VC.ErrorReporter;

public final class Scanner {

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
        switch (currentChar) {
        case '(':
            accept();
            return Token.LPAREN;
        case ')':
            accept();
            return Token.RPAREN;
        case '{':
            accept();
            return Token.LCURLY;
        case '}':
            accept();
            return Token.RCURLY;
        case '[':
            accept();
            return Token.LBRACKET;
        case ']':
            accept();
            return Token.RBRACKET;
	default:
	    System.out.println("checkSeperators: recieved invalid token type");
	    return -1;
        }
    }
        
    private int checkOperators() {
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
    private void checkSpecial() {
                
        }
    
    private enum Tokens {
            SEPERATORS, OPERATORS, LITERALS,
            IDENTIFIERS, KEYWORDS
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
    }

    public Token getToken() {
        Token tok;
        int kind;

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
