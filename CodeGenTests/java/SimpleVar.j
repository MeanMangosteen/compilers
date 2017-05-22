;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Mon May 22 11:31:01 AEST 2017

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
.var 0 is argv [Ljava/lang/String; from Label0 to Label1
.var 1 is vc$ LSimpleVar; from Label2 to Label1
.var 2 is i Ljava/lang/Integer; from Label4 to Label1
.var 3 is j Ljava/lang/Integer; from Label1 to Label1

Label0:
.line 6
	new SimpleVar
	dup
	invokespecial SimpleVar/<init>()V
	astore_1
Label2:
.line 8
	iconst_1
	invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
	astore_2
Label4:
.line 10
	aload_2
	invokevirtual java/lang/Integer/intValue()I
	iconst_1
	iadd
	invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
	astore_3
Label1:
.line 12
	return

.end method
