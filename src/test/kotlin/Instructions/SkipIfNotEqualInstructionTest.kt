import Instructions.SkipIfNotEqualInstruction
import Memory.Registry_Handlers.PRegisterInstance
import Memory.Registry_Handlers.RRegisterInstance
import com.emulator.Utils.byteArrayToInteger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class SkipIfNotEqualInstructionTest {

    private lateinit var instruction: SkipIfNotEqualInstruction

    @BeforeEach
    fun setUp() {
        // Initialize the instruction with nibbles for registers
        val nibbles = byteArrayOf(0, 1, 0) // Assume registers 0 and 1 are used for comparison
        instruction = SkipIfNotEqualInstruction(nibbles)

        // Set initial values in the registers for testing
        RRegisterInstance.r[0].operateOnRegister(byteArrayOf(5)) // Register X value
        RRegisterInstance.r[1].operateOnRegister(byteArrayOf(10)) // Register Y value
    }

    @Test
    fun testSkipNextInstructionWhenRegistersNotEqual() {
        instruction.runTask() // Perform the instruction
        val currentPC = PRegisterInstance.p.readRegister()

        instruction.incrementProgramCounter() // Increment program counter based on comparison

        val expectedPC = byteArrayToInteger(currentPC) + 4 // Expecting to skip next instruction
        assertEquals(expectedPC, byteArrayToInteger(PRegisterInstance.p.readRegister()))
    }

    @Test
    fun testDoNotSkipNextInstructionWhenRegistersEqual() {
        // Change register Y to be equal to register X
        RRegisterInstance.r[1].operateOnRegister(byteArrayOf(5)) // Set register Y value to 5

        instruction.runTask() // Perform the instruction
        val currentPC = PRegisterInstance.p.readRegister()

        instruction.incrementProgramCounter() // Increment program counter based on comparison

        val expectedPC = byteArrayToInteger(currentPC) + 2 // Expecting not to skip next instruction
        assertEquals(expectedPC, byteArrayToInteger(PRegisterInstance.p.readRegister()))
    }

    @Test
    fun testSkipNextInstructionHandlesEmptyRegisters() {
        // Clear the registers to simulate an empty state
        RRegisterInstance.r[0].operateOnRegister(byteArrayOf(0)) // Set register X value to 0
        RRegisterInstance.r[1].operateOnRegister(byteArrayOf(0)) // Set register Y value to 0

        instruction.runTask() // Perform the instruction
        val currentPC = PRegisterInstance.p.readRegister()

        instruction.incrementProgramCounter() // Increment program counter based on comparison

        val expectedPC = byteArrayToInteger(currentPC) + 2 // Expecting not to skip next instruction
        assertEquals(expectedPC, byteArrayToInteger(PRegisterInstance.p.readRegister()))
    }
}
