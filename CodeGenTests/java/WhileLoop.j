;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Fri May 26 22:47:21 AEST 2017

.source WhileLoop.java
.class public WhileLoop
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LWhileLoop; from Label0 to Label1

Label0:
.line 2
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 3
.var 0 is argv [Ljava/lang/String; from Label2 to Label0
.var 1 is vc$ LWhileLoop; from Label4 to Label0
.var 2 is i I from Label1 to Label0

Label2:
.line 6
	new WhileLoop
	dup
	invokespecial WhileLoop/<init>()V
	astore_1
Label4:
.line 8
	iconst_0
	istore_2
Label1:
.line 10
	iload_2
	bipush 10
	if_icmpeq Label0
.line 11
	getstatic java.lang.System.out Ljava/io/PrintStream;
	iload_2
	invokevirtual java/io/PrintStream/println(I)V
.line 12
	iload_2
	iconst_1
	iadd
	istore_2
	goto Label1
Label0:
.line 15
	return

.end method
