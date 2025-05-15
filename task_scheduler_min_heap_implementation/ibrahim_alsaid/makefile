# Makefile for Task Scheduling Assignment

# Java compiler
JC = javac

# Java runtime
JVM = java

# Source files directory
SRCDIR = src

# Class output directory
BINDIR = bin

# Clean target - removes compiled files
clean:
	rm -rf $(BINDIR)

# Build target - compiles Java files
build:
	mkdir -p $(BINDIR)
	$(JC) -d $(BINDIR) $(SRCDIR)/*.java

# Run target - executes the program with arguments
run:
	$(JVM) -cp $(BINDIR) TaskManager $(ARGS)

.PHONY: clean build run
