;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Thu May 25 22:56:07 AEST 2017

.source StrAssign.java
.class public StrAssign
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LStrAssign; from Label0 to Label1

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
.var 0 is argv [Ljava/lang/String; from Label0 to Label1
.var 1 is vc$ LStrAssign; from Label2 to Label1
.var 2 is j Ljava/lang/String; from Label1 to Label1

Label0:
.line 6
	new StrAssign
	dup
	invokespecial StrAssign/<init>()V
	astore_1
Label2:
.line 8
	ldc "hello world! THis is a realllllllllllllllllllllllllllyyyyyyyyyyyyyyyyyyyyyyyyyyy loooooooooooooooooooonnnnnnnnnnnnnnnnnnnnnnnnnggggggggggggggggggggggggggggggggggggggggggggggggggg Strinnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnngggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg"
	astore_2
Label1:
.line 10
	return

.end method
