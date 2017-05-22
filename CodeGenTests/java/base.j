;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Mon May 22 12:29:46 AEST 2017

.source base.java
.class public base
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this Lbase; from Label0 to Label1

Label0:
.line 2
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 2
.var 0 is argv [Ljava/lang/String; from Label0 to Label1
.var 1 is vc$ Lbase; from Label1 to Label1

Label0:
.line 6
	new base
	dup
	invokespecial base/<init>()V
	astore_1
Label1:
.line 8
	return

.end method
