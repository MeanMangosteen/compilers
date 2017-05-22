SUBDIRS	:= de/fub/bytecode
JC      := jikes #javac
JFLAGS  := +E # -g
JAVADOC := javadoc
SOURCES := $(wildcard *.java)
CLASSES := $(SOURCES:.java=.class)
NAMES   := $(SOURCES:.java=)

#
# New rule for type dependent compilation
#
%.class: %.java
	@rm -f $@
	$(JC) $(JFLAGS) $<

all: 	subdirs $(CLASSES)

subdirs:
	for i in $(SUBDIRS) ;\
	do\
		(cd $$i ; $(MAKE) JC=$(JC) JFLAGS="$(JFLAGS)")\
	done


clean:
	for i in $(SUBDIRS) ;\
	do\
		(cd $$i ; $(MAKE) allclean)\
	done
	rm -f core *.class

always:

docs: always
	-mkdir docs 2> /dev/null
	$(JAVADOC) -windowtitle "BCEL API documentation" \
	-doctitle "BCEL API documentation" -version -author \
	-d docs de.fub.bytecode.classfile\
	de.fub.bytecode.util\
	de.fub.bytecode.generic\
	de.fub.bytecode\
	$(NAMES)

jar:	listclass.class Manifest
	jar cvfm BCEL.jar Manifest `find de gnu -name \*.class` listclass.class
