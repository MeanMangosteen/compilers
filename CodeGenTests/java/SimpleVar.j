;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Thu May 25 21:27:57 AEST 2017

.source SimpleVar.java
.class public SimpleVar
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LSimpleVar; from Label0 to Label1

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
.var 1 is vc$ LSimpleVar; from Label4 to Label3
.var 2 is i F from Label6 to Label3
.var 3 is j Z from Label3 to Label3

Label2:
.line 6
	new SimpleVar
	dup
	invokespecial SimpleVar/<init>()V
	astore_1
Label4:
.line 8
	fconst_1
	fstore_2
Label6:
.line 9
	fload_2
	ldc 0.5
	fcmpl
	ifne Label0
	iconst_1
	goto Label1
Label0:
	iconst_0
Label1:
	istore_3
Label3:
.line 12
	return

.end method