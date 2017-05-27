;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Fri May 26 19:29:59 AEST 2017

.source IfAssign.java
.class public IfAssign
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LIfAssign; from Label0 to Label1

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
.var 0 is argv [Ljava/lang/String; from Label2 to Label1
.var 1 is vc$ LIfAssign; from Label4 to Label1
.var 2 is i I from Label1 to Label1
.var 2 is i I from Label8 to Label0
.var 3 is j Z from Label10 to Label1

Label2:
.line 6
	new IfAssign
	dup
	invokespecial IfAssign/<init>()V
	astore_1
Label4:
.line 9
	iconst_0
	istore_3
Label10:
.line 11
	iload_3
	ifeq Label0
.line 12
	iconst_1
	istore_2
Label8:
	goto Label1
Label0:
.line 16
	iconst_3
	istore_2
Label1:
.line 19
	return

.end method
