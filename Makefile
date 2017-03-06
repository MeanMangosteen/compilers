# A simple Makefile for UNSW cs3161/cs9102 programming languages and compilers
# Written by Chris Hall
#
# Place this Makefile inside your VC/ folder, so your directory layout
# should look like:
#
#   VC/
#     ErrorReporter.java
#     vc.java
#     Makefile
#     Scanner/
#       <a bunch of .vc files>
#       <a bunch of .sol files>
#       <a bunch of .java files>
#
# if you change directory to VC/
#   $ cd VC/
#
# you can then use the below make commands
#   $ make run

.POSIX:

# `make` is the same as `make run`
all: run

# This is the rule for actually doing the compile of vc.java into vc.class
../vc.class:
	CLASSPATH=../ javac vc.java

# `make clean` will remove "tokens.out" and any .class files (compiled java)
clean:
	find . -iname '*.class' -delete
	rm -rf tokens.out

# `make run` will compile and run your scanner
run: clean ../vc.class
	CLASSPATH=../ java VC.vc Scanner/tokens.vc

# `make tokens.out` will compile and run your scanner, writing all output to
# "tokens.out"
tokens.out: clean ../vc.class
	CLASSPATH=../ java VC.vc Scanner/tokens.vc > tokens.out

.PHONY: all clean run

