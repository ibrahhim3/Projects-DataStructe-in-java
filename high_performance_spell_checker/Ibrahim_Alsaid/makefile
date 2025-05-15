# Directory structure
SRC = src
BIN = bin
BUILD = build
LIB = lib
DOC = doc

# Java compiler and runtime
JC = javac
JVM = java

# Main class to run
MAIN_CLASS = SpellChecker

# Source files - assuming all .java files are in src directory
SOURCES = $(wildcard $(SRC)/*.java)

# Default target
all: build

# Build target - compile all Java files
build:
	mkdir -p "$(BUILD)"
	$(JC) -d "$(BUILD)" -cp "$(LIB)/*" $(SOURCES)

# Generate JavaDoc documentation
javadoc:
	mkdir -p "$(DOC)"
	javadoc -d "$(DOC)" -cp "$(LIB)/*" $(SOURCES)

# Prepare bin directory for running
prepare-bin: build
	mkdir -p "$(BIN)"
	cp dictionary.txt "$(BIN)/"

# Run the spell checker
run: prepare-bin
	$(JVM) -cp "$(BUILD):$(LIB)/*" $(MAIN_CLASS) $(ARGS)

# Check for dictionary file presence
check-dictionary:
	@if [ ! -f dictionary.txt ]; then \
		echo "Error: dictionary.txt not found!"; \
		echo "Please make sure dictionary.txt exists in the current directory."; \
		exit 1; \
	fi

# Run with dictionary check
spell-check: check-dictionary run

# Clean compiled files and temporary files
clean:
	rm -rf "$(BUILD)"/* "$(DOC)" "$(BIN)"/*

# Help target
help:
	@echo "Available targets:"
	@echo "  all          - Same as build"
	@echo "  build        - Compile all Java files"
	@echo "  javadoc      - Generate JavaDoc documentation"
	@echo "  run          - Run the spell checker program"
	@echo "  clean        - Remove compiled files and other generated content"
	@echo "  spell-check  - Check for dictionary file and run the program"
	@echo "  help         - Display this help message"
	@echo ""
	@echo "Options:"
	@echo "  ARGS         - Arguments to pass to the program"

.PHONY: all build javadoc prepare-bin run clean check-dictionary spell-check help
