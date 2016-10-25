compile: 
	javac -cp bin -d bin `find src -iname "*.java"`
clear-bin:
	rm -r ./bin/*
