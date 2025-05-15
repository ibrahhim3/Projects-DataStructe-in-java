
# Default target - compile all Java files
all:
	javac -d bin src/*.java

# Run the program
run: all
	java -cp bin Main

# Clean compiled files
clean:
	rm -f bin/*.class

.PHONY: all run test clean help
