package Instructions

class CPUInstructionsFactory {

    // Array of instruction constructors
    private val instructionConstructorsMap = mapOf(
        0 to ::StoreInstruction,
        1 to ::AddInstruction,
        2 to ::SubtractInstruction,
        3 to ::ReadInstruction,
        4 to ::WriteInstruction,
        5 to ::JumpInstruction,
        6 to ::ReadKeyboardInstruction,
        7 to ::SwitchMemoryInstruction,
        8 to ::SkipIfEqualInstruction,
        9 to ::SkipIfNotEqualInstruction,
        10 to ::SetARegisterInstruction,
        11 to ::SetTRegisterInstruction,
        12 to ::ReadTRegisterInstruction,
        13 to ::ConvertToBaseTenInstruction,
        14 to ::ConvertByteToAsciiInstruction,
        15 to ::DrawInstruction
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
    fun makeInstructions(nibble0: Byte, nibble1: Byte, nibble2: Byte, nibble3: Byte): BasicInstruction {
        // Get the instruction constructor using the first nibble as an index
        require(instructionConstructorsMap.containsKey(nibble0.toInt())) {
            "Invalid instruction nibble: $nibble0"
        }
        val constructor = instructionConstructorsMap[nibble0.toInt()]!!

        // Create an instruction instance with the remaining nibbles
        return constructor(byteArrayOf(nibble1, nibble2, nibble3))
    }
}
