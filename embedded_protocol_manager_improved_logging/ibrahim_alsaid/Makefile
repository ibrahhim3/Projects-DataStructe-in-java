SRC = src
BUILD = build

collect:
	find -name "*.java" > sources.txt

build: collect
	javac -d $(BUILD) @sources.txt

run:
	java -cp build Main.Main $(ARGS)

clean:
	rm -rf build sources.txt
	rm -rf build logs
docs:
	javadoc -d docs @sources.txt
