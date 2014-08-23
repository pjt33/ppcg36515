all:
	javac *.java
	$(MAKE) -C resources/players all

clean:
	rm *.class
	$(MAKE) -C resources/players clean
