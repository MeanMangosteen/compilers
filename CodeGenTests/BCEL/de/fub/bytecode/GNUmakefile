SUBDIRS	:= classfile generic util

include Makefile.common

all: 	subdirs

subdirs:
	for i in $(SUBDIRS) ;\
	do\
		(cd $$i ; $(MAKE) JC=$(JC) JFLAGS="$(JFLAGS)")\
	done


allclean: clean
	for i in $(SUBDIRS) ;\
	do\
		(cd $$i ; $(MAKE) clean)\
	done
