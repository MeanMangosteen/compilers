/**
 * Checker.java   
 * Sun Apr 24 15:57:55 AEST 2016
 **/

package VC.Checker;

import VC.ASTs.*;
import VC.Scanner.SourcePosition;
import VC.ErrorReporter;
import VC.StdEnvironment;

public final class Checker implements Visitor {

	private String errMesg[] = {
			"*0: main function is missing",                            
			"*1: return type of main is not int",                    

			// defined occurrences of identifiers
			// for global, local and parameters
			"*2: identifier redeclared",                             
			"*3: identifier declared void",                         
			"*4: identifier declared void[]",                      

			// applied occurrences of identifiers
			"*5: identifier undeclared",                          

			// assignments
			"*6: incompatible type for =",                       
			"*7: invalid lvalue in assignment",                 

			// types for expressions 
			"*8: incompatible type for return",                
			"*9: incompatible type for this binary operator", 
			"*10: incompatible type for this unary operator",

			// scalars
			"*11: attempt to use an array/function as a scalar", 

			// arrays
			"*12: attempt to use a scalar/function as an array",
			"*13: wrong type for element in array initialiser",
			"*14: invalid initialiser: array initialiser for scalar",   
			"*15: invalid initialiser: scalar initialiser for array",  
			"*16: excess elements in array initialiser",              
			"*17: array subscript is not an integer",                
			"*18: array size missing",                              

			// functions
			"*19: attempt to reference a scalar/array as a function",

			// conditional expressions in if, for and while
			"*20: if conditional is not boolean",                    
			"*21: for conditional is not boolean",                  
			"*22: while conditional is not boolean",               

			// break and continue
			"*23: break must be in a while/for",                  
			"*24: continue must be in a while/for",              

			// parameters 
			"*25: too many actual parameters",                  
			"*26: too few actual parameters",                  
			"*27: wrong type for actual parameter",           

			// reserved for errors that I may have missed (J. Xue)
			"*28: misc 1",
			"*29: misc 2",

			// the following two checks are optional 
			"*30: statement(s) not reached",     
			"*31: missing return statement",    
	};


	private SymbolTable idTable;
	private static SourcePosition dummyPos = new SourcePosition();
	private ErrorReporter reporter;

	// Checks whether the source program, represented by its AST, 
	// satisfies the language's scope rules and type rules.
	// Also decorates the AST as follows:
	//  (1) Each applied occurrence of an identifier is linked to
	//      the corresponding declaration of that identifier.
	//  (2) Each expression and variable is decorated by its type.

	public Checker (ErrorReporter reporter) {
		this.reporter = reporter;
		this.idTable = new SymbolTable ();
		establishStdEnvironment();
	}

	public void check(AST ast) {
		ast.visit(this, null);
	}


	// auxiliary methods

	private void declareVariable(Ident ident, Decl decl) {

		IdEntry entry = idTable.retrieveOneLevel(ident.spelling);

		if (entry == null) {
			; // no problem
		} else
			reporter.reportError(errMesg[2] + ": %", ident.spelling, ident.position);
		idTable.insert(ident.spelling, decl);
	}


	// Programs

	public Object visitProgram(Program ast, Object o) {
		ast.FL.visit(this, null);

		return null;
	}

	// Statements

	public Object visitCompoundStmt(CompoundStmt ast, Object o) {
		idTable.openScope();

		ast.DL.visit(this, o);
		ast.SL.visit(this, o);

		idTable.closeScope();
		return null;
	}

	public Object visitStmtList(StmtList ast, Object o) {
		ast.S.visit(this, o);
		if (ast.S instanceof ReturnStmt && ast.SL instanceof StmtList)
			reporter.reportError(errMesg[30], "", ast.SL.position);
		ast.SL.visit(this, o);
		return null;
	}


	public Object visitExprStmt(ExprStmt ast, Object o) {
		ast.E.visit(this, o);
		return null;
	}

	public Object visitEmptyStmt(EmptyStmt ast, Object o) {
		return null;
	}

	public Object visitEmptyStmtList(EmptyStmtList ast, Object o) {
		return null;
	}

	// Expressions

	// Returns the Type denoting the type of the expression. Does
	// not use the given object.


	public Object visitEmptyExpr(EmptyExpr ast, Object o) {
		ast.type = StdEnvironment.errorType;
		return ast.type;
	}

	public Object visitBooleanExpr(BooleanExpr ast, Object o) {
		ast.type = StdEnvironment.booleanType;
		return ast.type;
	}

	public Object visitIntExpr(IntExpr ast, Object o) {
		ast.type = StdEnvironment.intType;
		return ast.type;
	}

	public Object visitFloatExpr(FloatExpr ast, Object o) {
		ast.type = StdEnvironment.floatType;
		return ast.type;
	}

	public Object visitStringExpr(StringExpr ast, Object o) {
		ast.type = StdEnvironment.stringType;
		return ast.type;
	}

	public Object visitVarExpr(VarExpr ast, Object o) {
		ast.type = (Type) ast.V.visit(this, null);
		if (ast.type == null) {
			ast.type = StdEnvironment.errorType;
		}
		return ast.type;
	}

	// Declarations

	// Always returns null. Does not use the given object.

	public Object visitFuncDecl(FuncDecl ast, Object o) {
		idTable.insert (ast.I.spelling, ast); 

		// Your code goes here

		ast.PL.visit(this, ast);
		// HINT
		// Pass ast as the 2nd argument (as done below) so that the
		// formal parameters of the function an be extracted from ast when the
		// function body is later visited

		ast.S.visit(this, ast);
		return null;
	}

	public Object visitDeclList(DeclList ast, Object o) {
		ast.D.visit(this, null);
		ast.DL.visit(this, null);
		return null;
	}

	public Object visitEmptyDeclList(EmptyDeclList ast, Object o) {
		return null;
	}
	
	public void checkVarTypes(Decl ast) {
		/* casting depending on varible type */
		Expr varExp;
		if(ast instanceof GlobalVarDecl) {
			varExp = ((GlobalVarDecl) ast).E;
		} else if (ast instanceof LocalVarDecl) {
			varExp = ((LocalVarDecl) ast).E;
		} else {
			throw new java.lang.RuntimeException("Need either global or local variable type"
					+ "when checking variable types");
		}
		
		/* visit expression with current as so initExpr can see decl type*/
		varExp.visit(this, ast);
		
		/* error checking if variable is of type array */
		if (ast.T.isArrayType() ) {
			ArrayType arrType = (ArrayType) ast.T;
				/* array decls have to have { } or no initialiser */
				if (!(varExp instanceof InitExpr || varExp instanceof EmptyExpr)) {
					reporter.reportError(errMesg[15] + ": %", ast.I.spelling, ast.position);
				} else if (varExp instanceof EmptyExpr) {
					/* if there is no { } initialiser, then there has to be a size specified */
					if (arrType.E.isEmptyExpr()) {
						reporter.reportError(errMesg[18] + ": %", ast.I.spelling, ast.I.position);
					}
				}
		}

		/* variables can not be of type void, or arrays of type void */
		if (ast.T.isVoidType()) {
			reporter.reportError(errMesg[3] + ": %", ast.I.spelling,  ast.position);
		} else if (ast.T.isArrayType()) {
			if (((ArrayType) ast.T).T.isVoidType())
				reporter.reportError(errMesg[4] + ": %", ast.I.spelling, ast.I.position);
		}
		

		if (!ast.T.isArrayType()) {
			if (!ast.T.assignable(varExp.type)) {
				reporter.reportError(errMesg[6] + ": %", ast.I.spelling, ast.I.position);
			}
		}
	}

	public Object visitGlobalVarDecl(GlobalVarDecl ast, Object o) {
		declareVariable(ast.I, ast);
		
		checkVarTypes(ast);
		
		return null;
		// fill the rest
	}

	public Object visitLocalVarDecl(LocalVarDecl ast, Object o) {
		declareVariable(ast.I, ast);

		checkVarTypes(ast);

/*		 TODO: check assignment types 
		if (ast.T.isVoidType()) {
			reporter.reportError(errMesg[3] + ": %", ast.I.spelling,  ast.position);
		} else if (ast.T.isArrayType()) {
			if (((ArrayType) ast.T).T.isVoidType())
				reporter.reportError(errMesg[4] + ": %", ast.I.spelling, ast.I.position);
		}
			Decl identDecl = (Decl) ast.I.decl;

			if(!identDecl.T.assignable(ast.E.type)) {
				reporter.reportError(errMesg[6] + ": %", ast.I.spelling, ast.I.position);
			}
		 TODO: check if this is correct 
*/		return null;
		// fill the rest
	}
	
	/* TODO */
	public Object visitExpr(Expr ast) {
		return ast.type.visit(this, null);
	}

	// Parameters

	// Always returns null. Does not use the given object.

	public Object visitParaList(ParaList ast, Object o) {
		ast.P.visit(this, null);
		ast.PL.visit(this, null);
		return null;
	}

	public Object visitParaDecl(ParaDecl ast, Object o) {
		declareVariable(ast.I, ast);

		if (ast.T.isVoidType()) {
			reporter.reportError(errMesg[3] + ": %", ast.I.spelling, ast.I.position);
		} else if (ast.T.isArrayType()) {
			if (((ArrayType) ast.T).T.isVoidType())
				reporter.reportError(errMesg[4] + ": %", ast.I.spelling, ast.I.position);
		}
		return null;
	}

	public Object visitEmptyParaList(EmptyParaList ast, Object o) {
		return null;
	}

	// Arguments

	// Your visitor methods for arguments go here

	// Types 

	// Returns the type predefined in the standard environment. 

	public Object visitErrorType(ErrorType ast, Object o) {
		return StdEnvironment.errorType;
	}

	public Object visitBooleanType(BooleanType ast, Object o) {
		return StdEnvironment.booleanType;
	}

	public Object visitIntType(IntType ast, Object o) {
		return StdEnvironment.intType;
	}

	public Object visitFloatType(FloatType ast, Object o) {
		return StdEnvironment.floatType;
	}

	public Object visitStringType(StringType ast, Object o) {
		return StdEnvironment.stringType;
	}

	public Object visitVoidType(VoidType ast, Object o) {
		return StdEnvironment.voidType;
	}

	// Literals, Identifiers and Operators

	public Object visitIdent(Ident I, Object o) {
		Decl binding = idTable.retrieve(I.spelling);
		if (binding != null) {
			I.decl = binding;
		} else {
				reporter.reportError(errMesg[5] + ": %", I.spelling, I.position);
		}
		return binding;
	}

	public Object visitBooleanLiteral(BooleanLiteral SL, Object o) {
		return StdEnvironment.booleanType;
	}

	public Object visitIntLiteral(IntLiteral IL, Object o) {
		return StdEnvironment.intType;
	}

	public Object visitFloatLiteral(FloatLiteral IL, Object o) {
		return StdEnvironment.floatType;
	}

	public Object visitStringLiteral(StringLiteral IL, Object o) {
		return StdEnvironment.stringType;
	}

	public Object visitOperator(Operator O, Object o) {
		return null;
	}

	// Creates a small AST to represent the "declaration" of each built-in
	// function, and enters it in the symbol table.

	private FuncDecl declareStdFunc (Type resultType, String id, List pl) {

		FuncDecl binding;

		binding = new FuncDecl(resultType, new Ident(id, dummyPos), pl, 
				new EmptyStmt(dummyPos), dummyPos);
		idTable.insert (id, binding);
		return binding;
	}

	// Creates small ASTs to represent "declarations" of all 
	// build-in functions.
	// Inserts these "declarations" into the symbol table.

	private final static Ident dummyI = new Ident("x", dummyPos);

	private void establishStdEnvironment () {

		// Define four primitive types
		// errorType is assigned to ill-typed expressions

		StdEnvironment.booleanType = new BooleanType(dummyPos);
		StdEnvironment.intType = new IntType(dummyPos);
		StdEnvironment.floatType = new FloatType(dummyPos);
		StdEnvironment.stringType = new StringType(dummyPos);
		StdEnvironment.voidType = new VoidType(dummyPos);
		StdEnvironment.errorType = new ErrorType(dummyPos);

		// enter into the declarations for built-in functions into the table

		StdEnvironment.getIntDecl = declareStdFunc( StdEnvironment.intType,
				"getInt", new EmptyParaList(dummyPos)); 
		StdEnvironment.putIntDecl = declareStdFunc( StdEnvironment.voidType,
				"putInt", new ParaList(
						new ParaDecl(StdEnvironment.intType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 
		StdEnvironment.putIntLnDecl = declareStdFunc( StdEnvironment.voidType,
				"putIntLn", new ParaList(
						new ParaDecl(StdEnvironment.intType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 
		StdEnvironment.getFloatDecl = declareStdFunc( StdEnvironment.floatType,
				"getFloat", new EmptyParaList(dummyPos)); 
		StdEnvironment.putFloatDecl = declareStdFunc( StdEnvironment.voidType,
				"putFloat", new ParaList(
						new ParaDecl(StdEnvironment.floatType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 
		StdEnvironment.putFloatLnDecl = declareStdFunc( StdEnvironment.voidType,
				"putFloatLn", new ParaList(
						new ParaDecl(StdEnvironment.floatType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 
		StdEnvironment.putBoolDecl = declareStdFunc( StdEnvironment.voidType,
				"putBool", new ParaList(
						new ParaDecl(StdEnvironment.booleanType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 
		StdEnvironment.putBoolLnDecl = declareStdFunc( StdEnvironment.voidType,
				"putBoolLn", new ParaList(
						new ParaDecl(StdEnvironment.booleanType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 

		StdEnvironment.putStringLnDecl = declareStdFunc( StdEnvironment.voidType,
				"putStringLn", new ParaList(
						new ParaDecl(StdEnvironment.stringType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 

		StdEnvironment.putStringDecl = declareStdFunc( StdEnvironment.voidType,
				"putString", new ParaList(
						new ParaDecl(StdEnvironment.stringType, dummyI, dummyPos),
						new EmptyParaList(dummyPos), dummyPos)); 

		StdEnvironment.putLnDecl = declareStdFunc( StdEnvironment.voidType,
				"putLn", new EmptyParaList(dummyPos));

	}

	@Override
	public Object visitEmptyExprList(EmptyExprList ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitEmptyArgList(EmptyArgList ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitIfStmt(IfStmt ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitWhileStmt(WhileStmt ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitForStmt(ForStmt ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitBreakStmt(BreakStmt ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitContinueStmt(ContinueStmt ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitReturnStmt(ReturnStmt ast, Object o) {
		FuncDecl fd = (FuncDecl) o;
		Type retType = (Type) ast.E.visit(this, null);
		
		/* if there is no return expression
		 * then make sure func return type is void
		 */
		if (ast.E instanceof EmptyExpr) {
			if (!fd.T.isVoidType()) {
				reporter.reportError(errMesg[8] + ": ", null, ast.position);
			}
		} else if (!fd.T.assignable(retType)) {
			reporter.reportError(errMesg[8] + ": ", null, ast.position);
		}
		
		/* TODO: do we need to return anything? */
		return null;
	}

	@Override
	public Object visitEmptyCompStmt(EmptyCompStmt ast, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitUnaryExpr(UnaryExpr ast, Object o) {
		/* get the get type */
		Type t = (Type) ast.E.visit(this, null);
		/* make expr is scalar */
		checkScalar(ast.E);
		/* assign type to current ast */
		ast.type = t;

		/* determine if type appropriate for unary expression 
		 * don't need the switch stmt, but there for sanity
		 */
		switch(ast.O.spelling) {
		case("+"):
		case("-"):
		case("!"):
			if (!(ast.E.type instanceof IntType || 
					ast.E.type instanceof FloatType)) {
				reporter.reportError(errMesg[10] + ": %", ast.O.spelling, ast.position);
			}
			break;
		default:
			reporter.reportError(errMesg[10] + ": %", ast.O.spelling, ast.position);
			ast.type = StdEnvironment.errorType;
		}
		
		return ast.type;
	}

	@Override
	public Object visitBinaryExpr(BinaryExpr ast, Object o) {
		Type t1 = (Type) ast.E1.visit(this, null);
		Type t2 = (Type) ast.E2.visit(this, null);
		
		checkScalar(ast.E1);
		checkScalar(ast.E2);
		
		if (t1.assignable(t2)) {
			ast.type = t1;
		} else {
			reporter.reportError(errMesg[9] + ": %", ast.O.spelling, ast.position);
			ast.type = StdEnvironment.errorType;
		}

		return ast.type;
	}
	
	/* HELPER: determines whether the expr is scalar 
	 * throws an error if not scalar type
	 */
	public void checkScalar(Expr ast) {
		Decl decl;
		Var var;
		Ident i;
		Type type;

		if (ast instanceof VarExpr) {
			var = ((VarExpr) ast).V;
			i = ((SimpleVar) var).I;
			decl = (Decl) visitIdent(i, null);
			type = decl.T;
			if (decl instanceof FuncDecl ||
					type instanceof ArrayType) {
				reporter.reportError(errMesg[11] + ": %", i.spelling, i.position);
			}
		}
	}


	@Override
	/* for array initialisation */
	public Object visitInitExpr(InitExpr ast, Object o) {
		Decl varDecl = (Decl) o;
		
		/* Only arrays deal with arrays */
		if (!varDecl.T.isArrayType()) {
				reporter.reportError(errMesg[14] + ": %", varDecl.I.spelling, ast.position);
				return StdEnvironment.errorType;
		}

		ArrayType arrType = (ArrayType) varDecl.T;

		/* get the original size */
		/* can only be an IntExpr as per VC grammar */
		IntExpr origSize = (IntExpr) arrType.E;
		/* set size to 0 for counting in recursion */
		arrType.E = new IntExpr(new IntLiteral("0", dummyPos), dummyPos);
		/* this will go to ExprList */
		ast.type = (Type) ast.IL.visit(this, arrType);
		
		/* if there was initially an original size, set it back
		 * otherwise do nothing, size has been set in recursion
		 */
		if (!origSize.isEmptyExpr()) {
			IntExpr calcSize = (IntExpr) arrType.E;
			/* if there was a specified size the check 
			 * if it is large enough by comparing it 
			 * to the calculated size determined through recursion
			 */
			if(isArraySmall(origSize, calcSize)) {
				reporter.reportError(errMesg[16] + ": %", varDecl.I.spelling, ast.position);
			}
			/* set is back to original size */
			arrType.E = origSize;
		}
		
		/* TODO: 
		 * Need to find a way to report an error if an element
		 * if of the wrong type, and also its position
		 * if we do it in this function then we don't access
		 * to the positions.
		 */
		return ast.type;
	}
	
	void incrementArraySize(IntExpr size) {
		Integer currSize = Integer.parseInt(size.IL.spelling);
		currSize++;
		size.IL.spelling = Integer.toString(currSize);
	}
	
	/* takes in the original size and the calculated size
	 * from recursion and determines whether is original
	 * size is greater or equal to calc size
	 */
	boolean isArraySmall(IntExpr origSize, IntExpr calcSize) {
		Integer origSizeInt;
		Integer calcSizeInt;
		
		origSizeInt = Integer.parseInt(origSize.IL.spelling);
		calcSizeInt = Integer.parseInt(calcSize.IL.spelling);
		
		if (origSizeInt >= calcSizeInt) {
			/* array is not too small return false */
			return false;
		} else {
			/* array is too small return true */
			return true;
		}
	}

	@Override
	/* takes in a expected to which it will check 
	 * all elements in initialiser list are of same type
	 */
	public Object visitExprList(ExprList ast, Object o) {
		ArrayType array = (ArrayType) o;
		IntExpr arraySizeExpr = (IntExpr) array.E;
		Type expectedType = array.T;
		Type elementType = (Type) ast.E.visit(this, null);
		if (!expectedType.assignable(elementType)) {
				reporter.reportError(errMesg[13] + ": at position %", arraySizeExpr.IL.spelling, ast.E.position);
		}
		incrementArraySize((IntExpr) array.E);
		ast.EL.visit(this, o);
		return null;
	}

	@Override
	public Object visitArrayExpr(ArrayExpr ast, Object o) {
		if (!(ast.E instanceof IntExpr)) {
				reporter.reportError(errMesg[17] + ": ", null, ast.E.position);
		}
		/* this visits SimpleVar */
		ast.type = (Type) ast.V.visit(this, null);
		SimpleVar arrVar = (SimpleVar) ast.V;
		if (arrVar == null) { 
			ast.type = StdEnvironment.errorType;
		} else {
			Type arrVarDeclType = (Type) ((Decl) arrVar.I.decl).T;
			if (arrVar.I.decl instanceof FuncDecl || !arrVarDeclType.isArrayType()) {
				reporter.reportError(errMesg[12] + ": %", arrVar.I.spelling, arrVar.I.position);
				ast.type = StdEnvironment.errorType;
			}
		}
		/* get the expr inside square brackets, i.e arr[expr] */
		ast.E.visit(this, null);
		return ast.type;
	}

	@Override
	public Object visitCallExpr(CallExpr ast, Object o) {
		FuncDecl fd = (FuncDecl) ast.I.visit(this, null);
		ast.AL.visit(this, null);
		/* TODO: make helper funciton to check arg types */
		
		return null;
	}

	@Override
	public Object visitAssignExpr(AssignExpr ast, Object o) {
		/* TODO: check if E1 will always be an ident, and print error accoridngly */
		Ident varIdent = null;
		ast.E1.visit(this, o);
		ast.E2.visit(this, o);
		
		/* making sure expression are scalar */
		checkScalar(ast.E1);
		checkScalar(ast.E2);

		/* making sure first expr is an identifier */
		if (!(ast.E1 instanceof VarExpr || ast.E1 instanceof ArrayExpr)) {
			reporter.reportError(errMesg[7] + ": ", null,  ast.position);
			ast.type = StdEnvironment.errorType;
			return StdEnvironment.errorType;
		}

		Type t1;
		if (ast.E1 instanceof VarExpr) {
			t1 = ((VarExpr) ast.E1).type;
		} else {
			/* must be ArrayExpr */
			//t1 = (((SimpleVar) (((ArrayExpr) ast.E1).V)).I.decl;
			t1 = ((ArrayType) ((ArrayExpr) ast.E1).type).T;
		}
		/* making sure ident and expr to assing are of same type */
		if (t1.assignable(ast.E2.type)) { 
			ast.type = ast.E1.type;
		} else {
			/* TODO: make helper function to get ident,
			 * so we have spelling for this Error
			 */
			reporter.reportError(errMesg[6] + ": ", null,  ast.position);
			ast.type = StdEnvironment.errorType;
		}
		
		/* we always return types for expr visit methods */
		return ast.type;
	}
	

	@Override
	public Object visitArgList(ArgList ast, Object o) {
		ast.A.visit(this, null);
		ast.AL.visit(this, null);
		return null;
	}

	@Override
	public Object visitArg(Arg ast, Object o) {
		Type t = (Type) ast.type.visit(this, null);
		if (t != null) {
			/* TODO: this seems dodge */
			ast.type = t;
		} else {
			ast.type = StdEnvironment.errorType;
		}

		return null;
	}

	@Override
	public Object visitArrayType(ArrayType ast, Object o) {
		Type t = (Type) ast.T.visit(this, o);
		ast.E.visit(this, o);
		return t;
	}

	@Override
	public Object visitSimpleVar(SimpleVar ast, Object o) {
		Decl binding = (Decl) visitIdent(ast.I, o);
		if (binding == null) {
			return null;
		} 
		ast.type = binding.T;
		return ast.type;
	}


}
