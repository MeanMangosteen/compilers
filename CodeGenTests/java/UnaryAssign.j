;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Fri May 26 16:25:24 AEST 2017

.source UnaryAssign.java
.class public UnaryAssign
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LUnaryAssign; from Label0 to Label1

Label0:
.line 2
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 3
.limit locals 6
.var 0 is argv [Ljava/lang/String; from Label2 to Label3
.var 1 is vc$ LUnaryAssign; from Label4 to Label3
.var 2 is i I from Label6 to Label3
.var 3 is j I from Label8 to Label3
.var 4 is k Z from Label10 to Label3
.var 5 is l Z from Label12 to Label3

Label2:
.line 6
	new UnaryAssign
	dup
	invokespecial UnaryAssign/<init>()V
	astore_1
Label4:
.line 8
	iconst_1
	istore_2
Label6:
.line 10
	iconst_0
	istore 4
Label10:
.line 13
	iload_2
	ineg
	istore_3
Label8:
.line 14
	iload_3
	istore_3
.line 15
	iload 4
	ifne Label0
	iconst_1
	goto Label1
Label0:
	iconst_0
Label1:
	istore 5
Label12:
.line 17
	getstatic java.lang.System.out Ljava/io/PrintStream;
	new java/lang/StringBuilder
	dup
	invokespecial java/lang/StringBuilder/<init>()V
	ldc "j: "
	invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;
	iload_3
	invokevirtual java/lang/StringBuilder/append(I)Ljava/lang/StringBuilder;
	ldc " l: "
	invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;
	iload 5
	invokevirtual java/lang/StringBuilder/append(Z)Ljava/lang/StringBuilder;
	invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
Label3:
.line 19
	return

.end method
