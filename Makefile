JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=Tree.class ReadData.class WriteData.class Sequential.class Parallel.class SunExposureApp.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

JAVAS=Tree.java ReadData.java WriteData.java Sequential.java Parallel.java SunExposureApp.java
JAVA_FILES=$(JAVAS:%.java=$(SRCDIR)/%.java)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

run:
	java -cp bin SunExposureApp ${ARGS}

javadoc: $(CLASS_FILES)
	javadoc -d $(DOCDIR) $(JAVA_FILES)