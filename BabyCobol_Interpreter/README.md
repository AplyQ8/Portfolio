# Cobol Interpreter

# BabyCOBOL Interpreter

A simple interpreter for a subset of the COBOL programming language, designed to work with a lightweight dialect we call **BabyCOBOL**.

---

🛠 Technologies Used
ANTLR 4.13.1 – For grammar definition and parsing

C# – Interpreter core and execution logic

.NET Core / .NET 6+ – Runtime environment

---

## 📄 Description

This project is designed to parse and interpret **BabyCOBOL** code using ANTLR and C#.  
It supports a wide range of COBOL-like statements, with partial support for `COPY` and control flow constructs like `GO TO`, `PERFORM`, and `EVALUATE`.

> ⚠️ It is recommended **not to use single-letter variable names**, as they may be misinterpreted as keywords by the interpreter.

---

✅ Implemented COBOL Statements
The interpreter currently supports the following statements and features:

ACCEPT – Read input

ADD – Perform addition

DISPLAY – Print to output

DIVIDE – Division operation

EVALUATE – Case-like control flow

IF – Conditional branching

MOVE – Assignment operation

MULTIPLY – Multiplication

PERFORM – Call procedure or loop

STOP – End execution

SUBTRACT – Subtraction

OCCURS – Define array-like structures

LIKE – Type referencing (e.g., LIKE var-name)

NEXT SENTENCE – Control flow continuation

LOOP – Custom loop implementation

COPY – (Partially implemented)

GO TO – Jump to a label

ALTER – Change transfer of control at runtime

SIGNAL – Runtime signaling

