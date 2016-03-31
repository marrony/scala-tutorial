all: $(addprefix bin/, $(patsubst %.scala, %.class, $(wildcard *.scala)))

bin/%.class: %.scala
	mkdir -p bin
	scalac -feature -deprecation $< -d bin/

run:
	scala -cp bin/ $(MAIN)

clean:
	rm -rf bin

