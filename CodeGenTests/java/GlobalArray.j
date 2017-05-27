;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Sat May 27 21:06:30 AEST 2017

.source GlobalArray.java
.class public GlobalArray
.super java/lang/Object

.field static a [I
.field static b [F
.field static c [Z

.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LGlobalArray; from Label0 to Label1

Label0:
.line 6
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 3
.limit locals 3
.var 0 is argv [Ljava/lang/String; from Label4 to Label2
.var 1 is vc$ LGlobalArray; from Label6 to Label2
.var 2 is i I from Label3 to Label2
.var 2 is i I from Label1 to Label0

Label4:
.line 10
	new GlobalArray
	dup
	invokespecial GlobalArray/<init>()V
	astore_1
Label6:
.line 12
	getstatic GlobalArray.b [F
	iconst_0
	ldc 3.1
	fastore
.line 14
	iconst_0
	istore_2
Label1:
	iload_2
	iconst_2
	if_icmpge Label0
.line 15
	getstatic java.lang.System.out Ljava/io/PrintStream;
	getstatic GlobalArray.b [F
	iload_2
	faload
	invokevirtual java/io/PrintStream/println(F)V
.line 14
	iinc 2 1
	goto Label1
Label0:
.line 18
	iconst_0
	istore_2
Label3:
	iload_2
	iconst_4
	if_icmpge Label2
.line 19
	getstatic java.lang.System.out Ljava/io/PrintStream;
	getstatic GlobalArray.a [I
	iload_2
	iaload
	invokevirtual java/io/PrintStream/println(I)V
.line 18
	iinc 2 1
	goto Label3
Label2:
.line 23
	return

.end method

.method static <clinit>()V
.limit stack 4
.limit locals 0

.line 2
	iconst_4
	newarray int
	dup
	iconst_0
	iconst_1
	iastore
	dup
	iconst_1
	iconst_2
	iastore
	dup
	iconst_2
	iconst_3
	iastore
	dup
	iconst_3
	iconst_4
	iastore
	putstatic GlobalArray.a [I
.line 3
	iconst_3
	newarray float
	putstatic GlobalArray.b [F
.line 4
	iconst_0
	newarray boolean
	putstatic GlobalArray.c [Z
	return

.end method
