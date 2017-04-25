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

    // Your code goes here

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
    return ast.type;
  }

  // Declarations

  // Always returns null. Does not use the given object.

  public Object visitFuncDecl(FuncDecl ast, Object o) {
    idTable.insert (ast.I.spelling, ast); 

    // Your code goes here

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

  public Object visitGlobalVarDecl(GlobalVarDecl ast, Object o) {
    declareVariable(ast.I, ast);

    // fill the rest
  }

  public Object visitLocalVarDecl(LocalVarDecl ast, Object o) {
    declareVariable(ast.I, ast);

    // fill the rest
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
    if (binding != null)
      I.decl = binding;
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


}
