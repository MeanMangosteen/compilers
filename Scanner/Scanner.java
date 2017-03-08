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
    }
        
    private in checkOperators() {
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
	        if (currentChar == "=") {
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
    	}
    }
    
    private int checkLiterals() {
        boolean isFloat = false;
        // check for IDs
        // has to start with letter or underscore
        if (currentChar >= 'a' && currentChar <= 'z'
                || currentChar >= 'A' && currentChar <= 'Z'
                || currentChar == '_') {
            accept();
            while true {
                // now numbers in ID are valid
                if (currentChar >= 'a' && currentChar <= 'z'
                        || currentChar >= 'A' && currentChar <= 'Z'
                        || currentChar >= '0' && currentChar <= '9'
                        || currentChar == '_') {
                    accept();
                } else {
                    // we didn't find a valid char for ID, return
                    return Token.ID;
                }
            }
        // TODO: remember exponent can be after dot as well
        } else {
            // check for all digits
            // check if any dot and any digits
            // check for exp and any digits
            while (currentChar >= '0' && currentChar <= '9') {
                // checking for int or float literals
                accept();
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
                        break;
                    }
                }
            } 
            if (currentChar == 'e' || currentChar == 'E') {
               // found exponent, must be a float
               accept()
                // find all digits after dot
                if (currentChar >= '0' && currentChar <= '9') {
                    accept();
                    while (true) {
                        if (currentChar >= '0' && currentChar <= '9') {
                            accept();
                            // TODO: add to spelling
                        } else {
                            isFloat = true;
                            break;
                        }
                    }
                } else {
                    // there must be a digit after exponent
                    return Token.ERROR;
                }
            }
            if (isFloat) {
                return Token.FLOATLITERAL;
            else {
                return Token.INTLITERAL;
            }
    }
    private int nextToken() {
        // Tokens: separators, operators, literals, identifiers and keyworods



            // TODO: make fucntion for chekcing flaot (is there one in Token.java?)
            case '.':
                //  attempting to recognise a float

            

                // ....
            case SourceFile.eof:
                currentSpelling.append(Token.spell(Token.EOF));
                return Token.EOF;
            default:
                break;
        }

        accept();
        return Token.ERROR;
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
