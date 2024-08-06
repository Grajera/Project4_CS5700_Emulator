import Instructions.SwitchMemoryInstruction
import Memory.Registry_Handlers.MRegisterManager.m
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SwitchMemoryInstructionTest {

    private lateinit var instruction: SwitchMemoryInstruction

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x00,0x00,0x00)
        instruction = SwitchMemoryInstruction(nibbles)
    }

    @Test
    fun testPerformInstruction_SwitchToROM() {
        m.operateOnRegister(byteArrayOf(0)) // Start in RAM mode
        instruction.runTask()

        assertEquals(1.toByte(), m.readRegister()[0], "The memory mode should switch to ROM mode.")
    }

    @Test
    fun testPerformInstruction_SwitchToRAM() {
        m.operateOnRegister(byteArrayOf(1)) // Start in ROM mode
        instruction.runTask()

        assertEquals(0.toByte(), m.readRegister()[0], "The memory mode should switch back to RAM mode.")
    }
}
