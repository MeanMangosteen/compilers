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

    private int nextToken() {
        // Tokens: separators, operators, literals, identifiers and keyworods

        switch (currentChar) {
            // TODO: make function for checking separators
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

            // TODO: make fucntion for chekcing flaot (is there one in Token.java?)
            case '.':
                //  attempting to recognise a float

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
