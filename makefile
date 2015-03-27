SRCPATH = ./src/Elizabeth/
CLSSPATH = ./classes/

Elizabeth: 
		   javac -d $(CLSSPATH) -classpath $(CLSSPATH) $(SRCPATH)*.java

all: Elizabeth


clean: 
	   rm $(CLSSPATH)*

