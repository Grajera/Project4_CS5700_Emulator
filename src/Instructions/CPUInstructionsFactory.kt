package Instructions

class CPUInstructionsFactory {

    // Array of instruction constructors
    private val instructionConstructors = arrayOf(
        ::StoreInstruction,
        ::AddInstruction,
        ::SubtractInstruction,
        ::ReadInstruction,
        ::WriteInstruction,
        ::JumpInstruction,
        ::ReadKeyboardInstruction,
        ::SwitchMemoryInstruction,
        ::SkipIfEqualInstruction,
        ::SkipIfNotEqualInstruction,
        ::SetARegisterInstruction,
        ::SetTRegisterInstruction,
        ::ReadTRegisterInstruction,
        ::ConvertToBaseTenInstruction,
        ::ConvertByteToAsciiInstruction,
        ::DrawInstruction
    )

    /**
     * Creates an instruction based on the provided nibbles.
     *
     * @param nibble0 The first nibble indicating the instruction type.
     * @param nibble1 The second nibble, typically used as an operand.
     * @param nibble2 The third nibble, typically used as an operand.
     * @param nibble3 The fourth nibble, typically used as an operand.
     * @return A new instance of the created instruction.
     */
    fun createInstruction(nibble0: Byte, nibble1: Byte, nibble2: Byte, nibble3: Byte): BasicInstruction {
        // Get the instruction constructor using the first nibble as an index
        val instructionConstructor = instructionConstructors[nibble0.toInt()]

        // Create an instruction instance with the remaining nibbles
        return instructionConstructor(byteArrayOf(nibble1, nibble2, nibble3))
    }
}
