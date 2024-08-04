package Instructions

class CPUInstructionsFactory {
    private val instructions = arrayOf(
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

    fun createInstruction(nibble0: Byte, nibble1: Byte, nibble2: Byte, nibble3: Byte): BasicInstruction {
        val instructionConstructor = instructions[nibble0.toInt()]

        val instruction = instructionConstructor(byteArrayOf(nibble1, nibble2, nibble3))

        return instruction
    }
}