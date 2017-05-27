;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Sat May 27 16:48:16 AEST 2017

.source GlobalVar.java
.class public GlobalVar
.super java/lang/Object

.field static i I

.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LGlobalVar; from Label0 to Label1

Label0:
.line 4
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 2
.var 0 is argv [Ljava/lang/String; from Label0 to Label1
.var 1 is vc$ LGlobalVar; from Label2 to Label1

Label0:
.line 8
	new GlobalVar
	dup
	invokespecial GlobalVar/<init>()V
	astore_1
Label2:
.line 10
	getstatic java.lang.System.out Ljava/io/PrintStream;
	getstatic GlobalVar.i I
	invokevirtual java/io/PrintStream/println(I)V
Label1:
.line 13
	return

.end method

.method static <clinit>()V
.limit stack 1
.limit locals 0

.line 2
	iconst_2
	putstatic GlobalVar.i I
	return

.end method
