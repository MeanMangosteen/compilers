/*
 * Parser.java            
 *
 * This parser for a subset of the VC language is intended to 
 *  demonstrate how to create the AST nodes, including (among others): 
 *  [1] a list (of statements)
 *  [2] a function
 *  [3] a statement (which is an expression statement), 
 *  [4] a unary expression
 *  [5] a binary expression
 *  [6] terminals (identifiers, integer literals and operators)
 *
 * In addition, it also demonstrates how to use the two methods start 
 * and finish to determine the position information for the start and 
 * end of a construct (known as a phrase) corresponding an AST node.
 *
 * NOTE THAT THE POSITION INFORMATION WILL NOT BE MARKED. HOWEVER, IT CAN BE
 * USEFUL TO DEBUG YOUR IMPLEMENTATION.
 *
 * (09-|-April-|-2016)


program       -> func-decl
func-decl     -> type identifier "(" ")" compound-stmt
type          -> void
identifier    -> ID
// statements
compound-stmt -> "{" stmt* "}" 
stmt          -> expr-stmt
expr-stmt     -> expr? ";"
// expressions 
expr                -> additive-expr
additive-expr       -> multiplicative-expr
                    |  additive-expr "+" multiplicative-expr
                    |  additive-expr "-" multiplicative-expr
multiplicative-expr -> unary-expr
	            |  multiplicative-expr "*" unary-expr
	            |  multiplicative-expr "/" unary-expr
unary-expr          -> "-" unary-expr
		    |  primary-expr

primary-expr        -> identifier
 		    |  INTLITERAL
		    | "(" expr ")"
 */

package VC.Parser;

import VC.Scanner.Scanner;
import VC.Scanner.SourcePosition;
import VC.Scanner.Token;

import VC.ErrorReporter;
import VC.ASTs.*;

import static VC.Scanner.Token.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Parser {
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
	private SourcePosition previousTokenPosition;
	private SourcePosition dummyPos = new SourcePosition();

	public Parser (Scanner lexer, ErrorReporter reporter) {
		scanner = lexer;
		errorReporter = reporter;

		previousTokenPosition = new SourcePosition();

		currentToken = scanner.getToken();
	}

	boolean currentInFirst(String s) {
		return Arrays.asList(first.get(s)).contains(currentToken.kind);
	}

	// match checks to see f the current token matches tokenExpected.
	// If so, fetches the next token.
	// If not, reports a syntactic error.

	void match(int tokenExpected) throws SyntaxError {
		if (currentToken.kind == tokenExpected) {
			previousTokenPosition = currentToken.position;
			currentToken = scanner.getToken();
		} else {
			syntacticError("\"%\" expected here", Token.spell(tokenExpected));
		}
	}

	void accept() {
		previousTokenPosition = currentToken.position;
		currentToken = scanner.getToken();
	}

	void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
		SourcePosition pos = currentToken.position;
		errorReporter.reportError(messageTemplate, tokenQuoted, pos);
		throw(new SyntaxError());
	}

	// start records the position of the start of a phrase.
	// This is defined to be the position of the first
	// character of the first token of the phrase.

	void start(SourcePosition position) {
		position.lineStart = currentToken.position.lineStart;
		position.charStart = currentToken.position.charStart;
	}

	// finish records the position of the end of a phrase.
	// This is defined to be the position of the last
	// character of the last token of the phrase.

	void finish(SourcePosition position) {
		position.lineFinish = previousTokenPosition.lineFinish;
		position.charFinish = previousTokenPosition.charFinish;
	}

	void copyStart(SourcePosition from, SourcePosition to) {
		to.lineStart = from.lineStart;
		to.charStart = from.charStart;
	}

	// ========================== PROGRAMS ========================

	public Program parseProgram() {

		Program programAST = null;

		SourcePosition programPos = new SourcePosition();
		start(programPos);

		try {
			while (currentToken.kind != Token.EOF) {
				List l = parseDecl();
			}
			finish(programPos);
			programAST = new Program(dlAST, programPos); 
			if (currentToken.kind != Token.EOF) {
				syntacticError("\"%\" unknown type", currentToken.spelling);
			}
		}
		catch (SyntaxError s) { return null; }
		return programAST;
	}

	// ========================== DECLARATIONS ========================

	List parseDecl() throws SyntaxError {
		SourcePosition declPos = new SourcePosition();
		Type t = parseType();
		Ident i = parseIdent();
		if (currentToken.kind == Token.LPAREN) {
			parseFuncDeclNew(t, i, declPos);
		} else {
			parseVarDeclNew();
		}
	}
	
	List FuncDeclNew(Type t, Ident i, SourcePosition s) throws SyntaxError {
		List paraList = parseParaList();
		Stmt cmpStmt = parseCompoundStmt();
		finish(s);
		List fDecl = new FuncDecl(t, i, paraList, cmpStmt, s);
		return fDecl;
		
	}

	List parseFuncDeclList() throws SyntaxError {
		List dlAST = null;
		Decl dAST = null;

		SourcePosition funcPos = new SourcePosition();
		start(funcPos);

		dAST = parseFuncDecl();

		if (currentToken.kind == Token.VOID) {
			dlAST = parseFuncDeclList();
			finish(funcPos);
			dlAST = new DeclList(dAST, dlAST, funcPos);
		} else if (dAST != null) {
			finish(funcPos);
			dlAST = new DeclList(dAST, new EmptyDeclList(dummyPos), funcPos);
		}
		if (dlAST == null) 
			dlAST = new EmptyDeclList(dummyPos);

		return dlAST;
	}

	Decl parseFuncDecl() throws SyntaxError {

		Decl fAST = null; 

		SourcePosition funcPos = new SourcePosition();
		start(funcPos);

		Type tAST = parseType();
		Ident iAST = parseIdent();
		List fplAST = parseParaList();
		Stmt cAST = parseCompoundStmt();
		finish(funcPos);
		fAST = new FuncDecl(tAST, iAST, fplAST, cAST, funcPos);
		return fAST;
	}

	//  ======================== TYPES ==========================

	Type parseType() throws SyntaxError {
		Type typeAST = null;

		SourcePosition typePos = new SourcePosition();
		start(typePos);

		accept();
		finish(typePos);
		typeAST = new VoidType(typePos);

		return typeAST;
	}

	// ======================= STATEMENTS ==============================

	Stmt parseCompoundStmt() throws SyntaxError {
		Stmt cAST = null; 

		SourcePosition stmtPos = new SourcePosition();
		start(stmtPos);

		match(Token.LCURLY);

		// Insert code here to build a DeclList node for variable declarations
		List slAST = parseStmtList();
		match(Token.RCURLY);
		finish(stmtPos);

		/* In the subset of the VC grammar, no variable declarations are
		 * allowed. Therefore, a block is empty iff it has no statements.
		 */
		if (slAST instanceof EmptyStmtList) 
			cAST = new EmptyCompStmt(stmtPos);
		else
			cAST = new CompoundStmt(new EmptyDeclList(dummyPos), slAST, stmtPos);
		return cAST;
	}


	List parseStmtList() throws SyntaxError {
		List slAST = null; 

		SourcePosition stmtPos = new SourcePosition();
		start(stmtPos);

		if (currentToken.kind != Token.RCURLY) {
			Stmt sAST = parseStmt();
			{
				if (currentToken.kind != Token.RCURLY) {
					slAST = parseStmtList();
					finish(stmtPos);
					slAST = new StmtList(sAST, slAST, stmtPos);
				} else {
					finish(stmtPos);
					slAST = new StmtList(sAST, new EmptyStmtList(dummyPos), stmtPos);
				}
			}
		}
		else
			slAST = new EmptyStmtList(dummyPos);

		return slAST;
	}

	Stmt parseStmt() throws SyntaxError {
		Stmt sAST = null;

		sAST = parseExprStmt();

		return sAST;
	}

	Stmt parseExprStmt() throws SyntaxError {
		Stmt sAST = null;

		SourcePosition stmtPos = new SourcePosition();
		start(stmtPos);

		if (currentToken.kind == Token.ID
				|| currentToken.kind == Token.INTLITERAL
				|| currentToken.kind == Token.LPAREN) {
			Expr eAST = parseExpr();
			match(Token.SEMICOLON);
			finish(stmtPos);
			sAST = new ExprStmt(eAST, stmtPos);
		} else {
			match(Token.SEMICOLON);
			finish(stmtPos);
			sAST = new ExprStmt(new EmptyExpr(dummyPos), stmtPos);
		}
		return sAST;
	}


	// ======================= PARAMETERS =======================

	List parseParaList() throws SyntaxError {
		List formalsAST = null;

		SourcePosition formalsPos = new SourcePosition();
		start(formalsPos);

		match(Token.LPAREN);
		match(Token.RPAREN);
		finish(formalsPos);
		formalsAST = new EmptyParaList (formalsPos);

		return formalsAST;
	}


	// ======================= EXPRESSIONS ======================


	Expr parseExpr() throws SyntaxError {
		Expr exprAST = null;
		exprAST = parseAdditiveExpr();
		return exprAST;
	}

	Expr parseAdditiveExpr() throws SyntaxError {
		Expr exprAST = null;

		SourcePosition addStartPos = new SourcePosition();
		start(addStartPos);

		exprAST = parseMultiplicativeExpr();
		while (currentToken.kind == Token.PLUS
				|| currentToken.kind == Token.MINUS) {
			Operator opAST = acceptOperator();
			Expr e2AST = parseMultiplicativeExpr();

			SourcePosition addPos = new SourcePosition();
			copyStart(addStartPos, addPos);
			finish(addPos);
			exprAST = new BinaryExpr(exprAST, opAST, e2AST, addPos);
		}
		return exprAST;
	}

	Expr parseMultiplicativeExpr() throws SyntaxError {

		Expr exprAST = null;

		SourcePosition multStartPos = new SourcePosition();
		start(multStartPos);

		exprAST = parseUnaryExpr();
		while (currentToken.kind == Token.MULT
				|| currentToken.kind == Token.DIV) {
			Operator opAST = acceptOperator();
			Expr e2AST = parseUnaryExpr();
			SourcePosition multPos = new SourcePosition();
			copyStart(multStartPos, multPos);
			finish(multPos);
			exprAST = new BinaryExpr(exprAST, opAST, e2AST, multPos);
		}
		return exprAST;
	}

	Expr parseUnaryExpr() throws SyntaxError {

		Expr exprAST = null;

		SourcePosition unaryPos = new SourcePosition();
		start(unaryPos);

		switch (currentToken.kind) {
		case Token.MINUS:
		{
			Operator opAST = acceptOperator();
			Expr e2AST = parseUnaryExpr();
			finish(unaryPos);
			exprAST = new UnaryExpr(opAST, e2AST, unaryPos);
		}
		break;

		default:
			exprAST = parsePrimaryExpr();
			break;

		}
		return exprAST;
	}

	Expr parsePrimaryExpr() throws SyntaxError {

		Expr exprAST = null;

		SourcePosition primPos = new SourcePosition();
		start(primPos);

		switch (currentToken.kind) {

		case Token.ID:
			Ident iAST = parseIdent();
			finish(primPos);
			Var simVAST = new SimpleVar(iAST, primPos);
			exprAST = new VarExpr(simVAST, primPos);
			break;

		case Token.LPAREN:
		{
			accept();
			exprAST = parseExpr();
			match(Token.RPAREN);
		}
		break;

		case Token.INTLITERAL:
			IntLiteral ilAST = parseIntLiteral();
			finish(primPos);
			exprAST = new IntExpr(ilAST, primPos);
			break;

		default:
			syntacticError("illegal primary expression", currentToken.spelling);

		}
		return exprAST;
	}

	// ========================== ID, OPERATOR and LITERALS ========================

	Ident parseIdent() throws SyntaxError {

		Ident I = null; 

		if (currentToken.kind == Token.ID) {
			previousTokenPosition = currentToken.position;
			String spelling = currentToken.spelling;
			I = new Ident(spelling, previousTokenPosition);
			currentToken = scanner.getToken();
		} else 
			syntacticError("identifier expected here", "");
		return I;
	}

	// acceptOperator parses an operator, and constructs a leaf AST for it

	Operator acceptOperator() throws SyntaxError {
		Operator O = null;

		previousTokenPosition = currentToken.position;
		String spelling = currentToken.spelling;
		O = new Operator(spelling, previousTokenPosition);
		currentToken = scanner.getToken();
		return O;
	}


	IntLiteral parseIntLiteral() throws SyntaxError {
		IntLiteral IL = null;

		if (currentToken.kind == Token.INTLITERAL) {
			String spelling = currentToken.spelling;
			accept();
			IL = new IntLiteral(spelling, previousTokenPosition);
		} else 
			syntacticError("integer literal expected here", "");
		return IL;
	}

	FloatLiteral parseFloatLiteral() throws SyntaxError {
		FloatLiteral FL = null;

		if (currentToken.kind == Token.FLOATLITERAL) {
			String spelling = currentToken.spelling;
			accept();
			FL = new FloatLiteral(spelling, previousTokenPosition);
		} else 
			syntacticError("float literal expected here", "");
		return FL;
	}

	BooleanLiteral parseBooleanLiteral() throws SyntaxError {
		BooleanLiteral BL = null;

		if (currentToken.kind == Token.BOOLEANLITERAL) {
			String spelling = currentToken.spelling;
			accept();
			BL = new BooleanLiteral(spelling, previousTokenPosition);
		} else 
			syntacticError("boolean literal expected here", "");
		return BL;
	}

}

