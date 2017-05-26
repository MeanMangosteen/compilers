.class public CodeGenTests/vc/SimpleVC
.super java/lang/Object
	
	
	; standard class static initializer 
.method static <clinit>()V
	
	
	; set limits used by this method
.limit locals 0
.limit stack 50
	return
.end method
	
	; standard constructor initializer 
.method public <init>()V
.limit stack 1
.limit locals 1
	aload_0
	invokespecial java/lang/Object/<init>()V
	return
.end method
.method public static main([Ljava/lang/String;)V
L0:
.var 0 is argv [Ljava/lang/String; from L0 to L1
.var 1 is vc$ LCodeGenTests/vc/SimpleVC; from L0 to L1
	new CodeGenTests/vc/SimpleVC
	dup
	invokenonvirtual CodeGenTests/vc/SimpleVC/<init>()V
	astore_1
.var 2 is i I from L0 to L1
	iconst_1
	iconst_1
	iadd
	istore_2
	iload_2
	invokestatic VC/lang/System/putIntLn(I)V
	return
L1:
	return
	
	; set limits used by this method
.limit locals 3
.limit stack 2
.end method
