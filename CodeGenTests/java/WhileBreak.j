;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Fri May 26 23:38:58 AEST 2017

.source WhileBreak.java
.class public WhileBreak
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LWhileBreak; from Label0 to Label1

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
.var 0 is argv [Ljava/lang/String; from Label4 to Label0
.var 1 is vc$ LWhileBreak; from Label6 to Label0
.var 2 is i I from Label3 to Label0

Label4:
.line 6
	new WhileBreak
	dup
	invokespecial WhileBreak/<init>()V
	astore_1
Label6:
.line 8
	iconst_0
	istore_2
Label3:
.line 10
	iload_2
	bipush 10
	if_icmpeq Label0
.line 11
	getstatic java.lang.System.out Ljava/io/PrintStream;
	iload_2
	invokevirtual java/io/PrintStream/println(I)V
.line 13
	iload_2
	iconst_5
	if_icmpne Label1
.line 14
	goto Label0
Label1:
.line 16
	iload_2
	iconst_1
	iadd
	istore_2
	goto Label3
Label0:
.line 19
	return

.end method
