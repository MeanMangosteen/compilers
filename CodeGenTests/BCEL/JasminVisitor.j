;; Produced by JasminVisitor (BCEL)
;; http://bcel.sourceforge.net/
;; Thu Jul 26 13:15:27 CEST 2001

.source JasminVisitor.java
.class public JasminVisitor
.super java/lang/Object
.implements de/fub/bytecode/classfile/Visitor

.field private clazz Lde/fub/bytecode/classfile/JavaClass;
.field private out Ljava/io/PrintWriter;
.field private class_name Ljava/lang/String;
.field private cp Lde/fub/bytecode/generic/ConstantPoolGen;
.field private method Lde/fub/bytecode/classfile/Method;
.field private map Ljava/util/Hashtable;

.method public disassemble()V
.limit stack 4
.limit locals 1
.var 0 is this LJasminVisitor; from Label0 to Label1

Label0:
.line 33
	new de/fub/bytecode/classfile/DefaultVisitor
	dup
	aload_0
	getfield JasminVisitor.clazz Lde/fub/bytecode/classfile/JavaClass;
	aload_0
	invokespecial de/fub/bytecode/classfile/DefaultVisitor/<init>(Lde/fub/bytecode/classfile/JavaClass;Lde/fub/bytecode/classfile/Visitor;)V
	invokevirtual de/fub/bytecode/classfile/DescendingVisitor/visit()V
.line 34
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	invokevirtual java/io/PrintWriter/close()V
Label1:
.line 35
	return

.end method

.method public visitJavaClass(Lde/fub/bytecode/classfile/JavaClass;)V
.limit stack 5
.limit locals 4
.var 0 is this LJasminVisitor; from Label2 to Label3
.var 1 is arg0 Lde/fub/bytecode/classfile/JavaClass; from Label2 to Label3

Label2:
.line 38
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc ";; Produced by JasminVisitor (BCEL)"
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 39
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc ";; http://bcel.sourceforge.net/"
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 40
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc ";; "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	new java/util/Date
	dup
	invokespecial java/util/Date/<init>()V
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/Object;)Ljava/lang/StringBuffer;
	ldc "\n"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 42
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc ".source "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_1
	invokevirtual de/fub/bytecode/classfile/JavaClass/getSourceFileName()Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 43
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "."
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/AccessFlags/getAccessFlags()I
	invokestatic de/fub/bytecode/classfile/Utility/classOrInterface(I)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/AccessFlags/getAccessFlags()I
	iconst_1
	invokestatic de/fub/bytecode/classfile/Utility/accessToString(IZ)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/JavaClass/getClassName()Ljava/lang/String;
	bipush 46
	bipush 47
	invokevirtual java/lang/String/replace(CC)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 46
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc ".super "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_1
	invokevirtual de/fub/bytecode/classfile/JavaClass/getSuperclassName()Ljava/lang/String;
	bipush 46
	bipush 47
	invokevirtual java/lang/String/replace(CC)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 48
	aload_1
	invokevirtual de/fub/bytecode/classfile/JavaClass/getInterfaceNames()[Ljava/lang/String;
	astore_2
.line 50
	iconst_0
	istore_3
	goto Label0
Label1:
.line 51
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc ".implements "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_2
	iload_3
	aaload
	bipush 46
	bipush 47
	invokevirtual java/lang/String/replace(CC)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 50
	iinc 3 1
Label0:
.line 50
	iload_3
	aload_2
	arraylength
	if_icmplt Label1
.line 53
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc "\n"
	invokevirtual java/io/PrintWriter/print(Ljava/lang/String;)V
Label3:
.line 54
	return

.end method

.method public visitField(Lde/fub/bytecode/classfile/Field;)V
.limit stack 3
.limit locals 2
.var 0 is this LJasminVisitor; from Label1 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/Field; from Label1 to Label0

Label1:
.line 57
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc ".field "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/AccessFlags/getAccessFlags()I
	invokestatic de/fub/bytecode/classfile/Utility/accessToString(I)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/FieldOrMethod/getName()Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/FieldOrMethod/getSignature()Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/print(Ljava/lang/String;)V
.line 59
	aload_1
	invokevirtual de/fub/bytecode/classfile/FieldOrMethod/getAttributes()[Lde/fub/bytecode/classfile/Attribute;
	arraylength
	ifne Label0
.line 60
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc "\n"
	invokevirtual java/io/PrintWriter/print(Ljava/lang/String;)V
Label0:
.line 61
	return

.end method

.method public visitConstantValue(Lde/fub/bytecode/classfile/ConstantValue;)V
.limit stack 4
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label1
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantValue; from Label0 to Label1

Label0:
.line 64
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc " = "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_1
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/Object;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
Label1:
.line 65
	return

.end method

.method private final printEndMethod(Lde/fub/bytecode/classfile/Attribute;)V
.limit stack 4
.limit locals 3
.var 0 is this LJasminVisitor; from Label1 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/Attribute; from Label1 to Label0

Label1:
.line 75
	aload_0
	getfield JasminVisitor.method Lde/fub/bytecode/classfile/Method;
	invokevirtual de/fub/bytecode/classfile/FieldOrMethod/getAttributes()[Lde/fub/bytecode/classfile/Attribute;
	astore_2
.line 77
	aload_1
	aload_2
	aload_2
	arraylength
	iconst_1
	isub
	aaload
	if_acmpne Label0
.line 78
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc ".end method"
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
Label0:
.line 79
	return

.end method

.method public visitDeprecated(Lde/fub/bytecode/classfile/Deprecated;)V
.limit stack 2
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label1
.var 1 is arg0 Lde/fub/bytecode/classfile/Deprecated; from Label0 to Label1

Label0:
.line 81
	aload_0
	aload_1
	invokespecial JasminVisitor/printEndMethod(Lde/fub/bytecode/classfile/Attribute;)V
Label1:
.line 81
	return

.end method

.method public visitSynthetic(Lde/fub/bytecode/classfile/Synthetic;)V
.limit stack 2
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label1
.var 1 is arg0 Lde/fub/bytecode/classfile/Synthetic; from Label0 to Label1

Label0:
.line 82
	aload_0
	aload_1
	invokespecial JasminVisitor/printEndMethod(Lde/fub/bytecode/classfile/Attribute;)V
Label1:
.line 82
	return

.end method

.method public visitMethod(Lde/fub/bytecode/classfile/Method;)V
.limit stack 3
.limit locals 3
.var 0 is this LJasminVisitor; from Label2 to Label1
.var 1 is arg0 Lde/fub/bytecode/classfile/Method; from Label2 to Label1

Label2:
.line 85
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "\n.method "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/AccessFlags/getAccessFlags()I
	invokestatic de/fub/bytecode/classfile/Utility/accessToString(I)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/FieldOrMethod/getName()Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_1
	invokevirtual de/fub/bytecode/classfile/FieldOrMethod/getSignature()Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 88
	aload_0
	aload_1
	putfield JasminVisitor.method Lde/fub/bytecode/classfile/Method;
.line 90
	aload_1
	invokevirtual de/fub/bytecode/classfile/FieldOrMethod/getAttributes()[Lde/fub/bytecode/classfile/Attribute;
	astore_2
.line 91
	aload_2
	ifnull Label0
	aload_2
	arraylength
	ifne Label1
Label0:
.line 92
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc ".end method"
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
Label1:
.line 93
	return

.end method

.method public visitExceptionTable(Lde/fub/bytecode/classfile/ExceptionTable;)V
.limit stack 5
.limit locals 4
.var 0 is this LJasminVisitor; from Label2 to Label3
.var 1 is arg0 Lde/fub/bytecode/classfile/ExceptionTable; from Label2 to Label3

Label2:
.line 96
	aload_1
	invokevirtual de/fub/bytecode/classfile/ExceptionTable/getExceptionNames()[Ljava/lang/String;
	astore_2
.line 97
	iconst_0
	istore_3
	goto Label0
Label1:
.line 98
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc ".throws "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_2
	iload_3
	aaload
	bipush 46
	bipush 47
	invokevirtual java/lang/String/replace(CC)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 97
	iinc 3 1
Label0:
.line 97
	iload_3
	aload_2
	arraylength
	if_icmplt Label1
.line 100
	aload_0
	aload_1
	invokespecial JasminVisitor/printEndMethod(Lde/fub/bytecode/classfile/Attribute;)V
Label3:
.line 101
	return

.end method

.method public visitCode(Lde/fub/bytecode/classfile/Code;)V
.limit stack 5
.limit locals 17
.var 0 is this LJasminVisitor; from Label31 to Label32
.var 1 is arg0 Lde/fub/bytecode/classfile/Code; from Label31 to Label32

Label31:
.line 106
	iconst_0
	istore_2
.line 108
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc ".limit stack "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_1
	invokevirtual de/fub/bytecode/classfile/Code/getMaxStack()I
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 109
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc ".limit locals "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_1
	invokevirtual de/fub/bytecode/classfile/Code/getMaxLocals()I
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 111
	new de/fub/bytecode/generic/MethodGen
	dup
	aload_0
	getfield JasminVisitor.method Lde/fub/bytecode/classfile/Method;
	aload_0
	getfield JasminVisitor.class_name Ljava/lang/String;
	aload_0
	getfield JasminVisitor.cp Lde/fub/bytecode/generic/ConstantPoolGen;
	invokespecial de/fub/bytecode/generic/MethodGen/<init>(Lde/fub/bytecode/classfile/Method;Ljava/lang/String;Lde/fub/bytecode/generic/ConstantPoolGen;)V
	astore_3
.line 112
	aload_3
	invokevirtual de/fub/bytecode/generic/MethodGen/getInstructionList()Lde/fub/bytecode/generic/InstructionList;
	astore 4
.line 113
	aload 4
	invokevirtual de/fub/bytecode/generic/InstructionList/getInstructionHandles()[Lde/fub/bytecode/generic/InstructionHandle;
	astore 5
.line 118
	aload_0
	new java/util/Hashtable
	dup
	invokespecial java/util/Hashtable/<init>()V
	putfield JasminVisitor.map Ljava/util/Hashtable;
.line 120
	iconst_0
	istore 6
	goto Label0
Label5:
.line 121
	aload 5
	iload 6
	aaload
	instanceof de/fub/bytecode/generic/BranchHandle
	ifeq Label1
.line 122
	aload 5
	iload 6
	aaload
	invokevirtual de/fub/bytecode/generic/InstructionHandle/getInstruction()Lde/fub/bytecode/generic/Instruction;
	checkcast de/fub/bytecode/generic/BranchInstruction
	astore 7
.line 124
	aload 7
	instanceof de/fub/bytecode/generic/Select
	ifeq Label2
.line 125
	aload 7
	checkcast de/fub/bytecode/generic/Select
	invokevirtual de/fub/bytecode/generic/Select/getTargets()[Lde/fub/bytecode/generic/InstructionHandle;
	astore 8
.line 127
	iconst_0
	istore 9
	goto Label3
Label4:
.line 128
	aload_0
	aload 8
	iload 9
	aaload
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "Label"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	iload_2
	iinc 2 1
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc ":"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.line 127
	iinc 9 1
Label3:
.line 127
	iload 9
	aload 8
	arraylength
	if_icmplt Label4
Label2:
.line 131
	aload 7
	invokevirtual de/fub/bytecode/generic/BranchInstruction/getTarget()Lde/fub/bytecode/generic/InstructionHandle;
	astore 8
.line 132
	aload_0
	aload 8
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "Label"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	iload_2
	iinc 2 1
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc ":"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
Label1:
.line 120
	iinc 6 1
Label0:
.line 120
	iload 6
	aload 5
	arraylength
	if_icmplt Label5
.line 136
	aload_3
	invokevirtual de/fub/bytecode/generic/MethodGen/getLocalVariables()[Lde/fub/bytecode/generic/LocalVariableGen;
	astore 6
.line 137
	iconst_0
	istore 7
	goto Label6
Label7:
.line 138
	aload 6
	iload 7
	aaload
	invokevirtual de/fub/bytecode/generic/LocalVariableGen/getStart()Lde/fub/bytecode/generic/InstructionHandle;
	astore 8
.line 139
	aload_0
	aload 8
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "Label"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	iload_2
	iinc 2 1
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc ":"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.line 140
	aload 6
	iload 7
	aaload
	invokevirtual de/fub/bytecode/generic/LocalVariableGen/getEnd()Lde/fub/bytecode/generic/InstructionHandle;
	astore 8
.line 141
	aload_0
	aload 8
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "Label"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	iload_2
	iinc 2 1
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc ":"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.line 137
	iinc 7 1
Label6:
.line 137
	iload 7
	aload 6
	arraylength
	if_icmplt Label7
.line 144
	aload_3
	invokevirtual de/fub/bytecode/generic/MethodGen/getExceptionHandlers()[Lde/fub/bytecode/generic/CodeExceptionGen;
	astore 7
.line 145
	iconst_0
	istore 8
	goto Label8
Label9:
.line 146
	aload 7
	iload 8
	aaload
	astore 9
.line 147
	aload 9
	invokevirtual de/fub/bytecode/generic/CodeExceptionGen/getStartPC()Lde/fub/bytecode/generic/InstructionHandle;
	astore 10
.line 149
	aload_0
	aload 10
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "Label"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	iload_2
	iinc 2 1
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc ":"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.line 150
	aload 9
	invokevirtual de/fub/bytecode/generic/CodeExceptionGen/getEndPC()Lde/fub/bytecode/generic/InstructionHandle;
	astore 10
.line 151
	aload_0
	aload 10
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "Label"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	iload_2
	iinc 2 1
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc ":"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.line 152
	aload 9
	invokevirtual de/fub/bytecode/generic/CodeExceptionGen/getHandlerPC()Lde/fub/bytecode/generic/InstructionHandle;
	astore 10
.line 153
	aload_0
	aload 10
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc "Label"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	iload_2
	iinc 2 1
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc ":"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.line 145
	iinc 8 1
Label8:
.line 145
	iload 8
	aload 7
	arraylength
	if_icmplt Label9
.line 156
	aload_3
	invokevirtual de/fub/bytecode/generic/MethodGen/getLineNumbers()[Lde/fub/bytecode/generic/LineNumberGen;
	astore 8
.line 157
	iconst_0
	istore 9
	goto Label10
Label11:
.line 158
	aload 8
	iload 9
	aaload
	invokevirtual de/fub/bytecode/generic/LineNumberGen/getInstruction()Lde/fub/bytecode/generic/InstructionHandle;
	astore 10
.line 159
	aload_0
	aload 10
	new java/lang/StringBuffer
	dup
	ldc_w ".line "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload 8
	iload 9
	aaload
	invokevirtual de/fub/bytecode/generic/LineNumberGen/getSourceLine()I
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial JasminVisitor/put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.line 157
	iinc 9 1
Label10:
.line 157
	iload 9
	aload 8
	arraylength
	if_icmplt Label11
.line 164
	iconst_0
	istore 9
	goto Label12
Label13:
.line 165
	aload 6
	iload 9
	aaload
	astore 10
.line 166
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc_w ".var "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 10
	invokevirtual de/fub/bytecode/generic/LocalVariableGen/getIndex()I
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc_w " is "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 10
	invokevirtual de/fub/bytecode/generic/LocalVariableGen/getName()Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 10
	invokevirtual de/fub/bytecode/generic/LocalVariableGen/getType()Lde/fub/bytecode/generic/Type;
	invokevirtual de/fub/bytecode/generic/Type/getSignature()Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc_w " from "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_0
	aload 10
	invokevirtual de/fub/bytecode/generic/LocalVariableGen/getStart()Lde/fub/bytecode/generic/InstructionHandle;
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc_w " to "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_0
	aload 10
	invokevirtual de/fub/bytecode/generic/LocalVariableGen/getEnd()Lde/fub/bytecode/generic/InstructionHandle;
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 164
	iinc 9 1
Label12:
.line 164
	iload 9
	aload 6
	arraylength
	if_icmplt Label13
.line 172
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc "\n"
	invokevirtual java/io/PrintWriter/print(Ljava/lang/String;)V
.line 174
	iconst_0
	istore 9
	goto Label14
Label26:
.line 175
	aload 5
	iload 9
	aaload
	astore 10
.line 176
	aload 10
	invokevirtual de/fub/bytecode/generic/InstructionHandle/getInstruction()Lde/fub/bytecode/generic/Instruction;
	astore 11
.line 177
	aload_0
	getfield JasminVisitor.map Ljava/util/Hashtable;
	aload 10
	invokevirtual java/util/Hashtable/get(Ljava/lang/Object;)Ljava/lang/Object;
	checkcast java/lang/String
	astore 12
.line 179
	aload 12
	ifnull Label15
.line 180
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	aload 12
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
Label15:
.line 182
	aload 11
	instanceof de/fub/bytecode/generic/BranchInstruction
	ifeq Label16
.line 183
	aload 11
	instanceof de/fub/bytecode/generic/Select
	ifeq Label17
.line 184
	aload 11
	checkcast de/fub/bytecode/generic/Select
	astore 13
.line 185
	aload 13
	invokevirtual de/fub/bytecode/generic/Select/getMatchs()[I
	astore 14
.line 186
	aload 13
	invokevirtual de/fub/bytecode/generic/Select/getTargets()[Lde/fub/bytecode/generic/InstructionHandle;
	astore 15
.line 188
	aload 13
	instanceof de/fub/bytecode/generic/TABLESWITCH
	ifeq Label18
.line 189
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc_w "\ttableswitch "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 14
	iconst_0
	iaload
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 14
	aload 14
	arraylength
	iconst_1
	isub
	iaload
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 192
	iconst_0
	istore 16
	goto Label19
Label20:
.line 193
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc_w "\t\t"
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_0
	aload 15
	iload 16
	aaload
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 192
	iinc 16 1
Label19:
.line 192
	iload 16
	aload 15
	arraylength
	if_icmplt Label20
	goto Label21
Label18:
.line 196
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc_w "\tlookupswitch "
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 198
	iconst_0
	istore 16
	goto Label22
Label23:
.line 199
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc_w "\t\t"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 14
	iload 16
	iaload
	invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;
	ldc_w " : "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_0
	aload 15
	iload 16
	aaload
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 198
	iinc 16 1
Label22:
.line 198
	iload 16
	aload 15
	arraylength
	if_icmplt Label23
Label21:
.line 202
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc_w "\t\tdefault: "
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload_0
	aload 13
	invokevirtual de/fub/bytecode/generic/BranchInstruction/getTarget()Lde/fub/bytecode/generic/InstructionHandle;
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
	goto Label24
Label17:
.line 204
	aload 11
	checkcast de/fub/bytecode/generic/BranchInstruction
	astore 13
.line 205
	aload 13
	invokevirtual de/fub/bytecode/generic/BranchInstruction/getTarget()Lde/fub/bytecode/generic/InstructionHandle;
	astore 10
.line 206
	aload_0
	aload 10
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	astore 12
.line 207
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc_w "\t"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	getstatic de.fub.bytecode.Constants.OPCODE_NAMES [Ljava/lang/String;
	aload 13
	invokevirtual de/fub/bytecode/generic/Instruction/getOpcode()S
	aaload
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc " "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 12
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
Label24:
	goto Label25
Label16:
.line 211
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	ldc_w "\t"
	invokespecial java/lang/StringBuffer/<init>(Ljava/lang/String;)V
	aload 11
	aload_0
	getfield JasminVisitor.cp Lde/fub/bytecode/generic/ConstantPoolGen;
	invokevirtual de/fub/bytecode/generic/ConstantPoolGen/getConstantPool()Lde/fub/bytecode/classfile/ConstantPool;
	invokevirtual de/fub/bytecode/generic/Instruction/toString(Lde/fub/bytecode/classfile/ConstantPool;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
Label25:
.line 174
	iinc 9 1
Label14:
.line 174
	iload 9
	aload 5
	arraylength
	if_icmplt Label26
.line 214
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	ldc "\n"
	invokevirtual java/io/PrintWriter/print(Ljava/lang/String;)V
.line 216
	iconst_0
	istore 9
	goto Label27
Label30:
.line 217
	aload 7
	iload 9
	aaload
	astore 10
.line 218
	aload 10
	invokevirtual de/fub/bytecode/generic/CodeExceptionGen/getCatchType()Lde/fub/bytecode/generic/ObjectType;
	astore 11
.line 219
	aload 11
	ifnonnull Label28
	ldc_w "all"
	goto Label29
Label28:
	aload 11
	invokevirtual de/fub/bytecode/generic/ObjectType/getClassName()Ljava/lang/String;
	bipush 46
	bipush 47
	invokevirtual java/lang/String/replace(CC)Ljava/lang/String;
Label29:
	astore 12
.line 222
	aload_0
	getfield JasminVisitor.out Ljava/io/PrintWriter;
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	ldc_w ".catch "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 12
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc_w " from "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_0
	aload 10
	invokevirtual de/fub/bytecode/generic/CodeExceptionGen/getStartPC()Lde/fub/bytecode/generic/InstructionHandle;
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc_w " to "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_0
	aload 10
	invokevirtual de/fub/bytecode/generic/CodeExceptionGen/getEndPC()Lde/fub/bytecode/generic/InstructionHandle;
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc_w " using "
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_0
	aload 10
	invokevirtual de/fub/bytecode/generic/CodeExceptionGen/getHandlerPC()Lde/fub/bytecode/generic/InstructionHandle;
	invokespecial JasminVisitor/get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/io/PrintWriter/println(Ljava/lang/String;)V
.line 216
	iinc 9 1
Label27:
.line 216
	iload 9
	aload 7
	arraylength
	if_icmplt Label30
.line 227
	aload_0
	aload_1
	invokespecial JasminVisitor/printEndMethod(Lde/fub/bytecode/classfile/Attribute;)V
Label32:
.line 228
	return

.end method

.method private final get(Lde/fub/bytecode/generic/InstructionHandle;)Ljava/lang/String;
.limit stack 4
.limit locals 3
.var 0 is this LJasminVisitor; from Label0 to Label1
.var 1 is arg0 Lde/fub/bytecode/generic/InstructionHandle; from Label0 to Label1

Label0:
.line 232
	new java/util/StringTokenizer
	dup
	aload_0
	getfield JasminVisitor.map Ljava/util/Hashtable;
	aload_1
	invokevirtual java/util/Hashtable/get(Ljava/lang/Object;)Ljava/lang/Object;
	checkcast java/lang/String
	ldc "\n"
	invokespecial java/util/StringTokenizer/<init>(Ljava/lang/String;Ljava/lang/String;)V
	invokevirtual java/util/StringTokenizer/nextToken()Ljava/lang/String;
	astore_2
.line 233
	aload_2
	iconst_0
	aload_2
	invokevirtual java/lang/String/length()I
	iconst_1
	isub
	invokevirtual java/lang/String/substring(II)Ljava/lang/String;
Label1:
	areturn

.end method

.method private final put(Lde/fub/bytecode/generic/InstructionHandle;Ljava/lang/String;)V
.limit stack 4
.limit locals 4
.var 0 is this LJasminVisitor; from Label4 to Label1
.var 1 is arg0 Lde/fub/bytecode/generic/InstructionHandle; from Label4 to Label1
.var 2 is arg1 Ljava/lang/String; from Label4 to Label1

Label4:
.line 237
	aload_0
	getfield JasminVisitor.map Ljava/util/Hashtable;
	aload_1
	invokevirtual java/util/Hashtable/get(Ljava/lang/Object;)Ljava/lang/Object;
	checkcast java/lang/String
	astore_3
.line 239
	aload_3
	ifnonnull Label0
.line 240
	aload_0
	getfield JasminVisitor.map Ljava/util/Hashtable;
	aload_1
	aload_2
	invokevirtual java/util/Hashtable/put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
	pop
	goto Label1
Label0:
.line 242
	aload_2
	ldc "Label"
	invokevirtual java/lang/String/startsWith(Ljava/lang/String;)Z
	ifne Label2
	aload_3
	aload_2
	invokevirtual java/lang/String/endsWith(Ljava/lang/String;)Z
	ifeq Label3
Label2:
.line 243
	return
Label3:
.line 245
	aload_0
	getfield JasminVisitor.map Ljava/util/Hashtable;
	aload_1
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	aload_3
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc "\n"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload_2
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokevirtual java/util/Hashtable/put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
	pop
Label1:
.line 247
	return

.end method

.method public visitCodeException(Lde/fub/bytecode/classfile/CodeException;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/CodeException; from Label0 to Label0

Label0:
.line 249
	return

.end method

.method public visitConstantClass(Lde/fub/bytecode/classfile/ConstantClass;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantClass; from Label0 to Label0

Label0:
.line 250
	return

.end method

.method public visitConstantDouble(Lde/fub/bytecode/classfile/ConstantDouble;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantDouble; from Label0 to Label0

Label0:
.line 251
	return

.end method

.method public visitConstantFieldref(Lde/fub/bytecode/classfile/ConstantFieldref;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantFieldref; from Label0 to Label0

Label0:
.line 252
	return

.end method

.method public visitConstantFloat(Lde/fub/bytecode/classfile/ConstantFloat;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantFloat; from Label0 to Label0

Label0:
.line 253
	return

.end method

.method public visitConstantInteger(Lde/fub/bytecode/classfile/ConstantInteger;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantInteger; from Label0 to Label0

Label0:
.line 254
	return

.end method

.method public visitConstantInterfaceMethodref(Lde/fub/bytecode/classfile/ConstantInterfaceMethodref;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantInterfaceMethodref; from Label0 to Label0

Label0:
.line 255
	return

.end method

.method public visitConstantLong(Lde/fub/bytecode/classfile/ConstantLong;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantLong; from Label0 to Label0

Label0:
.line 256
	return

.end method

.method public visitConstantMethodref(Lde/fub/bytecode/classfile/ConstantMethodref;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantMethodref; from Label0 to Label0

Label0:
.line 257
	return

.end method

.method public visitConstantNameAndType(Lde/fub/bytecode/classfile/ConstantNameAndType;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantNameAndType; from Label0 to Label0

Label0:
.line 258
	return

.end method

.method public visitConstantPool(Lde/fub/bytecode/classfile/ConstantPool;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantPool; from Label0 to Label0

Label0:
.line 259
	return

.end method

.method public visitConstantString(Lde/fub/bytecode/classfile/ConstantString;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantString; from Label0 to Label0

Label0:
.line 260
	return

.end method

.method public visitConstantUtf8(Lde/fub/bytecode/classfile/ConstantUtf8;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/ConstantUtf8; from Label0 to Label0

Label0:
.line 261
	return

.end method

.method public visitInnerClass(Lde/fub/bytecode/classfile/InnerClass;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/InnerClass; from Label0 to Label0

Label0:
.line 262
	return

.end method

.method public visitInnerClasses(Lde/fub/bytecode/classfile/InnerClasses;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/InnerClasses; from Label0 to Label0

Label0:
.line 263
	return

.end method

.method public visitLineNumber(Lde/fub/bytecode/classfile/LineNumber;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/LineNumber; from Label0 to Label0

Label0:
.line 264
	return

.end method

.method public visitLineNumberTable(Lde/fub/bytecode/classfile/LineNumberTable;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/LineNumberTable; from Label0 to Label0

Label0:
.line 265
	return

.end method

.method public visitLocalVariable(Lde/fub/bytecode/classfile/LocalVariable;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/LocalVariable; from Label0 to Label0

Label0:
.line 266
	return

.end method

.method public visitLocalVariableTable(Lde/fub/bytecode/classfile/LocalVariableTable;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/LocalVariableTable; from Label0 to Label0

Label0:
.line 267
	return

.end method

.method public visitSourceFile(Lde/fub/bytecode/classfile/SourceFile;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/SourceFile; from Label0 to Label0

Label0:
.line 268
	return

.end method

.method public visitUnknown(Lde/fub/bytecode/classfile/Unknown;)V
.limit stack 0
.limit locals 2
.var 0 is this LJasminVisitor; from Label0 to Label0
.var 1 is arg0 Lde/fub/bytecode/classfile/Unknown; from Label0 to Label0

Label0:
.line 269
	return

.end method

.method public static main([Ljava/lang/String;)V
.limit stack 5
.limit locals 9
.var 0 is arg0 [Ljava/lang/String; from Label8 to Label7

Label8:
.line 272
	aconst_null
	astore_1
.line 273
.line 274
	new de/fub/bytecode/ClassPath
	dup
	invokespecial de/fub/bytecode/ClassPath/<init>()V
	astore_3
Label10:
.line 276
.line 277
	aload_0
	arraylength
	ifne Label0
.line 278
	getstatic java.lang.System.err Ljava/io/PrintStream;
	ldc_w "disassemble: No input files specified"
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	goto Label1
Label0:
.line 281
	iconst_0
	istore 4
	goto Label2
Label6:
.line 282
	aload_0
	iload 4
	aaload
	ldc_w ".class"
	invokevirtual java/lang/String/endsWith(Ljava/lang/String;)Z
	ifeq Label3
.line 283
	new de/fub/bytecode/classfile/ClassParser
	dup
	aload_0
	iload 4
	aaload
	invokespecial de/fub/bytecode/classfile/ClassParser/<init>(Ljava/lang/String;)V
	astore_1
	goto Label4
Label3:
.line 285
	aload_3
	aload_0
	iload 4
	aaload
	invokevirtual de/fub/bytecode/ClassPath/getInputStream(Ljava/lang/String;)Ljava/io/InputStream;
	astore 5
.line 286
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	aload_0
	iload 4
	aaload
	bipush 46
	bipush 47
	invokevirtual java/lang/String/replace(CC)Ljava/lang/String;
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc_w ".class"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	astore 6
.line 288
	new de/fub/bytecode/classfile/ClassParser
	dup
	aload 5
	aload 6
	invokespecial de/fub/bytecode/classfile/ClassParser/<init>(Ljava/io/InputStream;Ljava/lang/String;)V
	astore_1
Label4:
.line 291
	aload_1
	invokevirtual de/fub/bytecode/classfile/ClassParser/parse()Lde/fub/bytecode/classfile/JavaClass;
	astore_2
.line 293
	aload_2
	invokevirtual de/fub/bytecode/classfile/JavaClass/getClassName()Ljava/lang/String;
	astore 5
.line 294
	aload 5
	bipush 46
	invokevirtual java/lang/String/lastIndexOf(I)I
	istore 6
.line 295
	aload 5
	iconst_0
	iload 6
	iconst_1
	iadd
	invokevirtual java/lang/String/substring(II)Ljava/lang/String;
	bipush 46
	getstatic java.io.File.separatorChar C
	invokevirtual java/lang/String/replace(CC)Ljava/lang/String;
	astore 7
.line 296
	aload 5
	iload 6
	iconst_1
	iadd
	invokevirtual java/lang/String/substring(I)Ljava/lang/String;
	astore 5
.line 298
	aload 7
	ldc_w ""
	invokevirtual java/lang/String/equals(Ljava/lang/Object;)Z
	ifne Label5
.line 299
	new java/io/File
	dup
	aload 7
	invokespecial java/io/File/<init>(Ljava/lang/String;)V
	astore 8
.line 300
	aload 8
	invokevirtual java/io/File/mkdirs()Z
	pop
Label5:
.line 303
	new java/io/FileOutputStream
	dup
	new java/lang/StringBuffer
	dup
	invokespecial java/lang/StringBuffer/<init>()V
	aload 7
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	aload 5
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	ldc_w ".j"
	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;
	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;
	invokespecial java/io/FileOutputStream/<init>(Ljava/lang/String;)V
	astore 8
.line 304
	new JasminVisitor
	dup
	aload_2
	aload 8
	invokespecial JasminVisitor/<init>(Lde/fub/bytecode/classfile/JavaClass;Ljava/io/OutputStream;)V
	invokevirtual JasminVisitor/disassemble()V
.line 281
	iinc 4 1
Label2:
.line 281
	iload 4
	aload_0
	arraylength
Label11:
	if_icmplt Label6
Label1:
	goto Label7
Label12:
	astore 4
.line 308
	aload 4
	invokevirtual java/lang/Throwable/printStackTrace()V
Label7:
.line 310
	return

.catch java/lang/Exception from Label10 to Label11 using Label12
.end method

.method public <init>(Lde/fub/bytecode/classfile/JavaClass;Ljava/io/OutputStream;)V
.limit stack 4
.limit locals 3
.var 0 is this LJasminVisitor; from Label0 to Label1
.var 1 is arg0 Lde/fub/bytecode/classfile/JavaClass; from Label0 to Label1
.var 2 is arg1 Ljava/io/OutputStream; from Label0 to Label1

Label0:
.line 22
	aload_0
	invokespecial java/lang/Object/<init>()V
.line 23
	aload_0
	aload_1
	putfield JasminVisitor.clazz Lde/fub/bytecode/classfile/JavaClass;
.line 24
	aload_0
	new java/io/PrintWriter
	dup
	aload_2
	invokespecial java/io/PrintWriter/<init>(Ljava/io/OutputStream;)V
	putfield JasminVisitor.out Ljava/io/PrintWriter;
.line 25
	aload_0
	aload_1
	invokevirtual de/fub/bytecode/classfile/JavaClass/getClassName()Ljava/lang/String;
	putfield JasminVisitor.class_name Ljava/lang/String;
.line 26
	aload_0
	new de/fub/bytecode/generic/ConstantPoolGen
	dup
	aload_1
	invokevirtual de/fub/bytecode/classfile/JavaClass/getConstantPool()Lde/fub/bytecode/classfile/ConstantPool;
	invokespecial de/fub/bytecode/generic/ConstantPoolGen/<init>(Lde/fub/bytecode/classfile/ConstantPool;)V
	putfield JasminVisitor.cp Lde/fub/bytecode/generic/ConstantPoolGen;
Label1:
.line 27
	return

.end method
