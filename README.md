
#  Assignment 4: D5700 ComputerComponents Emulator
In this assignment you will build an emulator for a simple computer system that I invented called the D5700. Emulation is when you take a physical computer system and write software versions of the core hardware components. For example, RAM can be represented in software as a simple array of bytes, a CPU instruction is just a function that can be called, a register is just a variable. The idea of emulation is to take software written for a specific computer and be able to run it on another computer system with a different architecture.

Examples of commercial emulators:
* The Android Emulator - tests Android applications on a PC or Mac before shipping software to physical Android devices.
* Sony, Microsoft, and Nintendo all have developed emulators for their older consoles to support backward compatibility on their current platforms.
* The JVM - a Virtual Machine and an Emulator are basically the same thing. The only difference is that an Emulator mimics a physical system, while a VM might be designed as software from the beginning and does not mimic a physical system. (Technically the D5700 is a virtual machine too)

This project is the biggest one of the semester.
## Objectives
* Learn to properly apply the template method and facade patterns
* Recognize opportunities for other design patterns
* Strengthen UML and Unit Testing Skills

## About The D5700

Start by reading the D5700 documentation found here D5700_Data_Sheet.pdf
That document tells you everything that the computer does and you should use it as reference when designing and implementing the various systems.

## Requirements
### UML (30 pts)
* 1.1 You should do your conceptual model using UML before you write any code.
* 1.2 Your UML diagram should include all classes and should correctly model their relationships, attributes, and methods. Be sure to indicate which methods are public or private.
* 1.3 Your UML should correctly model the design patterns you plan on implementing.
* 1.4 Look for opportunities to use the singleton, template method, facade, factory, and strategy patterns.
* 1.5 Make sure to use the correct syntax for representing your relationships
### Implementation (70 pts)
* 1.1 When the emulator program starts, the user is asked to type in the path to a program to load
  * 1.1.2 When the file is loaded the bytes are copied into the ROM portion of the computer
  * 1.1.3 The loaded program starts executing
* 1.2 Instructions
  * 1.2.1 Each instruction is implemented correctly
    * Use the provided programs to test the instructions.
  * 1.2.2 The program stops when the CPU encounters the instruction 0000.
* 1.3 Programs
  * 1.3.1 Your emulator should be able to execute all of the provided programs
* 1.4 Output
  * You can decide how to implement the emulator's 8x8 screen. You are welcome to use Jetpack Compose or another GUI framework or just simply draw the "screen" to the console (see my example video). A GUI is a little more work (because input will be harder if you build a GUI) but looks cooler, but it will be your preference. If you are unsure, I recommend just writing to the console.
* 1.5 Input
  * Same deal as output. You can just do console input and console output, or you can have users provide input through text input in Jetpack compose
### Unit Tests(30 pts)
* 1.1 Unit tests should be thorough and cover edge cases.
  * It might be wise to write your unit tests as you go so that you know your instructions are working as you write them.

### Example Roms
Example roms can be found in the ROMS folder in this project.
