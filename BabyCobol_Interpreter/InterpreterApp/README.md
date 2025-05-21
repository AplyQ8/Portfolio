Done by
Kirill Fedorov
Grigory Baranov
Danila Koryugin
To compile the new antlr rules file:

0. Ensure java is installed on your machine
1. cd ../InterpreterApp/InterpreterModules/antlr
2. run java -jar antlr-4.13.1-complete.jar -Dlanguage=CSharp -visitor cobol.g4 CobolTokens.g4