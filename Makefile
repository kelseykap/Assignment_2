# Makefile for CSC2002S Assignment 2

JAVAC=/usr/bin/javac

SRCDIR = ./src/
BINDIR = ./bin/
DOCDIR = ./doc/

JFLAGS = -g -d $(BINDIR) -cp $(SRCDIR)

COMPILE = $(JAVAC) $(JFLAGS)

EMPTY = 

JAVA_FILES = $(subst $(SRCDIR), $(EMPTY), $(wildcard $(SRCDIR)*.java))

ALL_FILES = $(JAVA_FILES)

CLASS_FILES = $(ALL_FILES:.java=.class)

default: $(addprefix $(BINDIR), $(CLASS_FILES))

$(BINDIR)%.class : $(SRCDIR)%.java
	$(COMPILE) $<

INPUT_FILE=attachments/sample_input.txt

INPUT=./attachments/$(INPUT_FILE)

run:
	java -cp $(BINDIR) TreeGrow $(INPUT_FILE)

runseq:
	java -cp $(BINDIR) TreeGrowSeq $(INPUT_FILE)

docs:
	find $(SRCDIR) -type f -name "*.java" | xargs javadoc -d $(DOCDIR) 

tar:
	tar --exclude=./outputs/* --exclude='./.git' -czvf Assignment1_parrelel.tar.gz ./src ./data ./doc ./bin ./outputs ./Makefile ./Report/CSC2001_Ass3_HashTables.pdfs

clean: 
	rm -rf $(BINDIR)*.class
