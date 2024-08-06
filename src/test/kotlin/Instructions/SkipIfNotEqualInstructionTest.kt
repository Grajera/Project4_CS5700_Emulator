import Instructions.SkipIfNotEqualInstruction
import Memory.Registry_Handlers.PRegisterManager
import Memory.Registry_Handlers.RRegisterManager
import com.emulator.byteArrayToInt
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
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(5)) // Register X value
        RRegisterManager.r[1].operateOnRegister(byteArrayOf(10)) // Register Y value
    }

    @Test
    fun testSkipNextInstructionWhenRegistersNotEqual() {
        instruction.runTask() // Perform the instruction
        val currentPC = PRegisterManager.p.readRegister()

        instruction.incrementProgramCounter() // Increment program counter based on comparison

        val expectedPC = byteArrayToInt(currentPC) + 4 // Expecting to skip next instruction
        assertEquals(expectedPC, byteArrayToInt(PRegisterManager.p.readRegister()))
    }

    @Test
    fun testDoNotSkipNextInstructionWhenRegistersEqual() {
        // Change register Y to be equal to register X
        RRegisterManager.r[1].operateOnRegister(byteArrayOf(5)) // Set register Y value to 5

        instruction.runTask() // Perform the instruction
        val currentPC = PRegisterManager.p.readRegister()

        instruction.incrementProgramCounter() // Increment program counter based on comparison

        val expectedPC = byteArrayToInt(currentPC) + 2 // Expecting not to skip next instruction
        assertEquals(expectedPC, byteArrayToInt(PRegisterManager.p.readRegister()))
    }

    @Test
    fun testSkipNextInstructionHandlesEmptyRegisters() {
        // Clear the registers to simulate an empty state
        RRegisterManager.r[0].operateOnRegister(byteArrayOf(0)) // Set register X value to 0
        RRegisterManager.r[1].operateOnRegister(byteArrayOf(0)) // Set register Y value to 0

        instruction.runTask() // Perform the instruction
        val currentPC = PRegisterManager.p.readRegister()

        instruction.incrementProgramCounter() // Increment program counter based on comparison

        val expectedPC = byteArrayToInt(currentPC) + 2 // Expecting not to skip next instruction
        assertEquals(expectedPC, byteArrayToInt(PRegisterManager.p.readRegister()))
    }
}
