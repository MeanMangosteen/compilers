;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Thu May 25 21:26:18 AEST 2017

.source IntAssign.java
.class public IntAssign
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LIntAssign; from Label0 to Label1

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
.var 0 is argv [Ljava/lang/String; from Label0 to Label1
.var 1 is vc$ LIntAssign; from Label2 to Label1
.var 2 is i I from Label4 to Label1
.var 3 is j I from Label1 to Label1

Label0:
.line 6
	new IntAssign
	dup
	invokespecial IntAssign/<init>()V
	astore_1
Label2:
.line 8
	iconst_1
	istore_2
Label4:
.line 9
	iload_2
	iconst_1
	iadd
	istore_3
Label1:
.line 11
	return

.end method
