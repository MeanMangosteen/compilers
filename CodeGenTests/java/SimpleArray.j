;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Sat May 27 14:32:49 AEST 2017

.source SimpleArray.java
.class public SimpleArray
.super java/lang/Object


.method public <init>()V
.limit stack 1
.limit locals 1
.var 0 is this LSimpleArray; from Label0 to Label1

Label0:
.line 2
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 4
.limit locals 5
.var 0 is argv [Ljava/lang/String; from Label0 to Label1
.var 1 is vc$ LSimpleArray; from Label2 to Label1
.var 2 is a [I from Label4 to Label1
.var 3 is b [Z from Label6 to Label1

Label0:
.line 6
	new SimpleArray
	dup
	invokespecial SimpleArray/<init>()V
	astore_1
Label2:
.line 8
	iconst_3
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
	astore_2
Label4:
.line 9
	iconst_5
	newarray boolean
	astore_3
Label6:
.line 12
	aload_2
	iconst_2
	iconst_4
	iastore
.line 13
	getstatic java.lang.System.out Ljava/io/PrintStream;
	aload_2
	iconst_2
	iaload
	invokevirtual java/io/PrintStream/println(I)V
Label1:
.line 14
	return

.end method
