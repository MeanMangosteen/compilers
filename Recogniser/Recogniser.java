/***
 * *
 * * Recogniser.java            
 * *
 ***/

/* At this stage, this parser accepts a subset of VC defined	by
 * the following grammar. 
 *
 * You need to modify the supplied parsing methods (if necessary) and 
 * add the missing ones to obtain a parser for the VC language.
 *
 * (17---March---2017)

 program       -> func-decl

// declaration

func-decl     -> void identifier "(" ")" compound-stmt

identifier    -> ID

// statements 
compound-stmt -> "{" stmt* "}" 
stmt          -> continue-stmt
|  expr-stmt
continue-stmt -> continue ";"
expr-stmt     -> expr? ";"

// expressions 
expr                -> assignment-expr
assignment-expr     -> additive-expr
additive-expr       -> multiplicative-expr
|  additive-expr "+" multiplicative-expr
multiplicative-expr -> unary-expr
|  multiplicative-expr "*" unary-expr
unary-expr          -> "-" unary-expr
|  primary-expr

primary-expr        -> identifier
|  INTLITERAL
| "(" expr ")"
*/

package VC.Recogniser;

import VC.Scanner.Scanner;
import VC.Scanner.SourcePosition;
import VC.Scanner.Token;
import static VC.Scanner.Token.*;
import VC.ErrorReporter;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class Recogniser {
	private static final Map<String, Integer[]> first;
	static {
		first = new HashMap<String, Integer[]>();
		first.put("var-decl", new Integer[]{VOID, BOOLEAN, INT, FLOAT});
		first.put("stmt", new Integer[]{LCURLY, IF, FOR, WHILE, BREAK, CONTINUE, RETURN, PLUS, MINUS, NOT, ID, LPAREN, INTLITERAL, FLOATLITERAL,
			BOOLEANLITERAL, STRINGLITERAL, SEMICOLON});
	}
	private Scanner scanner;
	private ErrorReporter errorReporter;
	private Token currentToken;

	public Recogniser (Scanner lexer, ErrorReporter reporter) {
		scanner = lexer;
		errorReporter = reporter;

		currentToken = scanner.getToken();
	}

	// match checks to see f the current token matches tokenExpected.
	// If so, fetches the next token.
	// If not, reports a syntactic error.

	void match(int tokenExpected) throws SyntaxError {
		if (currentToken.kind == tokenExpected) {
			currentToken = scanner.getToken();
		} else {
			syntacticError("\"%\" expected here", Token.spell(tokenExpected));
		}
	}

	// accepts the current token and fetches the next
	void accept() {
		currentToken = scanner.getToken();
	}

	void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
		SourcePosition pos = currentToken.position;
		errorReporter.reportError(messageTemplate, tokenQuoted, pos);
		throw(new SyntaxError());
	}

	boolean currentInFirst(String s) {
		return Arrays.asList(first.get(s)).contains(currentToken.kind);
	}


	// ========================== PROGRAMS ========================

	public void parseProgram() {

		try {
			while (currentToken.kind != Token.EOF) {
				parseDecl();
			}
			if (currentToken.kind != Token.EOF) {
				syntacticError("\"%\" wrong result type for a function", currentToken.spelling);
			}
		}
		catch (SyntaxError s) {  }
	}

	// ========================== DECLARATIONS ========================

	void parseDecl() throws SyntaxError {
		parseType();
		parseIdent();
		if (currentToken.kind == Token.LPAREN) {
			parseFuncDeclNew();
		} else {
			parseVarDeclNew();
		}
	}

	void parseFuncDeclNew() throws SyntaxError {
		// TODO
		parseParaList();
		// TODO
		parseCompoundStmt();
	}

	void parseVarDecl() throws SyntaxError {
		parseType();
		parseInitDeclaratorList();
		match(Token.SEMICOLON);
	}

	void parseVarDeclNew() throws SyntaxError {
		switch(currentToken.kind) {
		case Token.LBRACKET:
			match(Token.LBRACKET);
			if (currentToken.kind == Token.INTLITERAL) {
				match(Token.INTLITERAL);
			}
			match(Token.RBRACKET);
			break;
		}
		if(currentToken.kind == Token.EQ) {
			match(Token.EQ);
			parseInitialiser();
		}
		if (currentToken.kind == Token.COMMA) {
			match(Token.COMMA);
			parseInitDeclaratorList();
		}
		match(Token.SEMICOLON);
		
	}

	void parseFuncDecl() throws SyntaxError {

		parseIdent();
		parseParaList();
		parseCompoundStmt();
	}

	void parseInitDeclarator() throws SyntaxError {
		parseDeclarator();
		if (currentToken.kind == Token.EQ) {
			match(Token.EQ);
			parseInitialiser();
		}
	}

	void parseInitDeclaratorList() throws SyntaxError {
		parseInitDeclarator();
		while (currentToken.kind == Token.COMMA) {
			match(Token.COMMA);
			parseInitDeclarator();
		}
	}

	void parseDeclarator() throws SyntaxError {
		parseIdent();
		if (currentToken.kind == Token.LBRACKET) {
			match(Token.LBRACKET);
			if (currentToken.kind == Token.INTLITERAL) {
				match(Token.INTLITERAL);
			}
			match(Token.RBRACKET);
		}
	}

	void parseInitialiser() throws SyntaxError {
		if (currentToken.kind == Token.LCURLY) {
			match(Token.LCURLY);
			parseExpr();
			while (currentToken.kind == Token.COMMA) {
				match(Token.COMMA);
				parseExpr();
			}
			match(Token.RCURLY);
		} else {
			parseExpr();
		}
	}
	
	// ========================== PRIMITIVE TYPES========================
	void parseType() throws SyntaxError { 
		switch(currentToken.kind) {
		case Token.VOID:
			match(Token.VOID);
			break;
		case Token.BOOLEAN:
			match(Token.BOOLEAN);
			break;
		case Token.INT:
			match(Token.INT);
			break;
		case Token.FLOAT:
			match(Token.FLOAT);
			break;
		default:
			syntacticError("expecting a type", currentToken.spelling);
		}
	}


	// ======================= STATEMENTS ==============================


	void parseCompoundStmt() throws SyntaxError {

		match(Token.LCURLY);
		while (currentInFirst("var-decl")) {
			parseVarDecl();

		}
		parseStmtList();
		match(Token.RCURLY);
	}

	// Here, a new nontermial has been introduced to define { stmt } *
	void parseStmtList() throws SyntaxError {
		while (currentInFirst("stmt")) {
			parseStmt();
		}
	}

	void parseStmt() throws SyntaxError {
		if (currentToken.kind == Token.LCURLY) {
			parseCompoundStmt();
		} else if (currentToken.kind == Token.IF) {
			parseIfStmt();
		} else if (currentToken.kind == Token.FOR) {
			parseForStmt();
		} else if (currentToken.kind == Token.WHILE) {
			parseWhileStmt();
		} else if (currentToken.kind == Token.CONTINUE) {
			parseContinueStmt();
		} else if (currentToken.kind == Token.BREAK) {
			parseBreakStmt();
		} else if (currentToken.kind == Token.RETURN) {
			parseReturnStmt();
		} else {
			parseExprStmt();
		}
	}

	void parseIfStmt() throws SyntaxError {
		match(Token.IF);
		match(Token.LPAREN);
		parseExpr();
		match(Token.RPAREN);
		parseStmt();
		if (currentToken.kind == Token.ELSE) {
			match(Token.ELSE);
			parseStmt();
		}
	}

	void parseForStmt() throws SyntaxError {
		match(Token.FOR);
		match(Token.LPAREN);
		if (currentToken.kind != Token.SEMICOLON) {
			parseExpr();
		}
		match(Token.SEMICOLON);
		if (currentToken.kind != Token.SEMICOLON) {
			parseExpr();
		}
		match(Token.SEMICOLON);
		if (currentToken.kind != Token.RPAREN) {
			parseExpr();
		}
		match(Token.RPAREN);
		parseStmt(); 
	}

	void parseWhileStmt() throws SyntaxError {
		match(Token.WHILE);
		match(Token.LPAREN);
		parseExpr();
		match(Token.RPAREN);
		parseStmt();

	}

	void parseBreakStmt() throws SyntaxError {
		match(Token.BREAK);
		match(Token.SEMICOLON);
	}

	void parseContinueStmt() throws SyntaxError {
		match(Token.CONTINUE);
		match(Token.SEMICOLON);
	}

	void parseReturnStmt() throws SyntaxError {
		match(Token.RETURN);
		if (currentToken.kind != Token.SEMICOLON) {
			parseExpr();
		}
		match(Token.SEMICOLON);
	}

	void parseExprStmt() throws SyntaxError {

		if (currentToken.kind != Token.SEMICOLON) {
			parseExpr();
		}
		match(Token.SEMICOLON);
	}


	// ======================= IDENTIFIERS ======================

	// Call parseIdent rather than match(Token.ID). 
	// In Assignment 3, an Identifier node will be constructed in here.


	void parseIdent() throws SyntaxError {

		if (currentToken.kind == Token.ID) {
			currentToken = scanner.getToken();
		} else 
			syntacticError("identifier expected here", "");
	}

	// ======================= OPERATORS ======================

	// Call acceptOperator rather than accept(). 
	// In Assignment 3, an Operator Node will be constructed in here.

	void acceptOperator() throws SyntaxError {

		currentToken = scanner.getToken();
	}


	// ======================= EXPRESSIONS ======================

	void parseExpr() throws SyntaxError {
		parseAssignExpr();
	}


	void parseAssignExpr() throws SyntaxError {
		parseCondOrExpr();
		while (currentToken.kind == Token.EQ) {
			match(Token.EQ);
			parseCondOrExpr();
		}
	}
	
	void parseCondOrExpr() throws SyntaxError {
		parseCondAndExpr();
		while (currentToken.kind == Token.OROR) {
			match(Token.OROR);
			parseCondAndExpr();
		}
	}

	void parseCondAndExpr() throws SyntaxError {
		parseEqualityExpr();
		while (currentToken.kind == Token.ANDAND) {
			match(Token.ANDAND);
			parseEqualityExpr();
		}
	}

	void parseEqualityExpr() throws SyntaxError {
		parseRelExpr();
		while (currentToken.kind == Token.EQEQ || currentToken.kind == Token.NOTEQ) {
			if (currentToken.kind == Token.EQEQ) {
				match(Token.EQEQ);
			} else {
				match(Token.NOTEQ);
			}
			parseRelExpr();	
		}
	}

	void parseRelExpr() throws SyntaxError {
		parseAdditiveExpr();
		switch(currentToken.kind) {
		case Token.LT:
			match(Token.LT);
			parseAdditiveExpr();
			break;
		case Token.LTEQ:
			match(Token.LTEQ);
			parseAdditiveExpr();
			break;
		case Token.GT:
			match(Token.GT);
			parseAdditiveExpr();
			break;
		case Token.GTEQ:
			match(Token.GTEQ);
			parseAdditiveExpr();
		default:
			break;
		}

	}

	void parseAdditiveExpr() throws SyntaxError {
		parseMultiplicativeExpr();
		while (currentToken.kind == Token.PLUS || currentToken.kind == Token.MINUS) {
			acceptOperator();
			parseMultiplicativeExpr();
		}
	}

	void parseMultiplicativeExpr() throws SyntaxError {
		parseUnaryExpr();
		while (currentToken.kind == Token.MULT || currentToken.kind == Token.DIV) {
			acceptOperator();
			parseUnaryExpr();
		}
	}


	void parseUnaryExpr() throws SyntaxError {
		switch (currentToken.kind) {
			case Token.PLUS:
			case Token.MINUS:
			case Token.NOT:
				acceptOperator();
				parseUnaryExpr();
				break;
			default:
				parsePrimaryExpr();
				break;

		}
	}

	void parsePrimaryExpr() throws SyntaxError {

		switch (currentToken.kind) {
			case Token.ID:
				parseIdent();
				if (currentToken.kind == Token.LBRACKET) {
					match(Token.LBRACKET);
					parseExpr();
					match(Token.RBRACKET);
				} else if (currentToken.kind == Token.LPAREN){
					parseArgList();
				}
				break;
			case Token.LPAREN:
				{
					accept();
					parseExpr();
					match(Token.RPAREN);
				}
				break;
			case Token.INTLITERAL:
				parseIntLiteral();
				break;
			case Token.FLOATLITERAL:
				parseFloatLiteral();
				break;
			case Token.BOOLEANLITERAL:
				parseBooleanLiteral();
				break;
			case Token.STRINGLITERAL:
				parseStringLiteral();
				break;
			default:
				syntacticError("illegal parimary expression", currentToken.spelling);

		}
	}
	

	// ========================== PARAMETERS ========================
	
	void parseParaList() throws SyntaxError {
		match(Token.LPAREN);
		if (currentToken.kind != Token.RPAREN) {
			parseProperParaList();
		}
		match(Token.RPAREN);
	}

	void parseProperParaList() throws SyntaxError {
		parseParaDecl();
		while (currentToken.kind == Token.COMMA) {
			match(Token.COMMA);
			parseParaDecl();
		}
	}

	void parseParaDecl() throws SyntaxError {
		parseType();
		parseDeclarator();
	}

	void parseArgList() throws SyntaxError {
		match(Token.LPAREN);
		if (currentToken.kind != Token.RPAREN) {
			parseProperArgList();
		}
		match(Token.RPAREN);
	}

	void parseProperArgList() throws SyntaxError {
		parseArg();
		while (currentToken.kind == Token.COMMA) {
			match(Token.COMMA);
			parseArg();
		}
	}

	void parseArg() throws SyntaxError {
		parseExpr();
	}


	// ========================== LITERALS ========================

	// Call these methods rather than accept().  In Assignment 3, 
	// literal AST nodes will be constructed inside these methods. 

	void parseIntLiteral() throws SyntaxError {

		if (currentToken.kind == Token.INTLITERAL) {
			currentToken = scanner.getToken();
		} else 
			syntacticError("integer literal expected here", "");
	}

	void parseFloatLiteral() throws SyntaxError {

		if (currentToken.kind == Token.FLOATLITERAL) {
			currentToken = scanner.getToken();
		} else 
			syntacticError("float literal expected here", "");
	}

	void parseBooleanLiteral() throws SyntaxError {

		if (currentToken.kind == Token.BOOLEANLITERAL) {
			currentToken = scanner.getToken();
		} else 
			syntacticError("boolean literal expected here", "");
	}

	void parseStringLiteral() throws SyntaxError {

		if (currentToken.kind == Token.STRINGLITERAL) {
			currentToken = scanner.getToken();
		} else 
			syntacticError("string literal expected here", "");
	}
}
