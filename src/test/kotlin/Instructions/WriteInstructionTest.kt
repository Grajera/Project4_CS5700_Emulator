import Instructions.WriteInstruction
import Memory.Registry_Handlers.ARegisterInstance.a
import Memory.Registry_Handlers.MRegisterInstance.m
import Memory.Registry_Handlers.RRegisterInstance.r
import Memory.RamInstance.RAM
import Memory.Registry_Handlers.R
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WriteInstructionTest {

    private lateinit var instruction: WriteInstruction
    private lateinit var registerX: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x0, 0x0) // Register index
        instruction = WriteInstruction(nibbles)
        registerX = r[nibbles[0].toInt()]
        registerX.operateOnRegister(byteArrayOf(0xAA.toByte())) // Set value to write
        a.operateOnRegister(byteArrayOf(0x00, 0x00)) // Address to write to
        m.operateOnRegister(byteArrayOf(0)) // Start in RAM mode
        RAM.writeToMemoryLocation(0x00, 0x00) // Initialize RAM
    }

    @Test
    fun testPerformInstruction_WriteToRAM() {
        instruction.runTask()
        assertEquals(0xAA.toByte(), RAM.readMemoryAddress(0x00), "The value should be written to RAM.")
    }

    @Test
    fun testPerformInstruction_ROMNotInitialized() {
        // Switch to ROM mode without initialization
        m.operateOnRegister(byteArrayOf(1)) // Switch to ROM mode
        assertFailsWith<IllegalStateException>("ROM is not initialized.") {
            instruction.runTask()
        }
    }

    @Test
    fun testPerformInstruction_InvalidAddress() {
        a.operateOnRegister(byteArrayOf(0xFF.toByte(), 0x0)) // Invalid address
        assertFailsWith<IllegalArgumentException>("Invalid address: 255. Must be in the range [0, 4096).") {
            instruction.runTask()
        }
    }
}
