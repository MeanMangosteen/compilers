;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Fri May 26 14:29:06 AEST 2017

.source BooleanAssign.java
.class public BooleanAssign
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LBooleanAssign; from Label0 to Label1

Label0:
.line 2
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 4
.var 0 is argv [Ljava/lang/String; from Label2 to Label3
.var 1 is vc$ LBooleanAssign; from Label4 to Label3
.var 2 is i Z from Label6 to Label3
.var 3 is j Z from Label3 to Label3

Label2:
.line 6
	new BooleanAssign
	dup
	invokespecial BooleanAssign/<init>()V
	astore_1
Label4:
.line 8
	iconst_1
	istore_2
Label6:
.line 9
	iload_2
	ifeq Label0
	iconst_1
	goto Label1
Label0:
	iconst_0
Label1:
	istore_3
Label3:
.line 11
	return

.end method
