all: clean generate

clean:
	rm -rf gen-java/

generate:
	mkdir -p gen-java
	proto --java_out=gen-java *.proto
