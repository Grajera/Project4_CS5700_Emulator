import Instructions.ReadInstruction
import Memory.Registry_Handlers.ARegisterManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.RRegisterManager.r
import Memory.RamManager.RAM
import Memory.Registry_Handlers.R
import Memory.RomManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ReadInstructionTest {

    private lateinit var instruction: ReadInstruction
    private lateinit var registerX: R

    @BeforeEach
    fun setUp() {
        val nibbles = byteArrayOf(0x0, 0x00, 0x00) // Example nibbles for register index
        instruction = ReadInstruction(nibbles)
        registerX = r[nibbles[0].toInt()]
        // Prepare initial state
        m.operateOnRegister(byteArrayOf(0)) // Use RAM mode
        a.operateOnRegister(byteArrayOf(0x00, 0x00)) // Set address in A register
        RAM.writeToMemory(0x00, 0xAB.toByte()) // Write value to RAM
    }

    @Test
    fun testPerformInstruction_ReadFromRAM() {
        instruction.runTask()
        assertEquals(0xAB.toByte(), registerX.readRegister()[0], "The value read from RAM should be stored in the target register.")
    }

    @Test
    fun testPerformInstruction_ReadFromROM() {
        // Switch to ROM mode and set a value in ROM
        m.operateOnRegister(byteArrayOf(1)) // Switch to ROM mode
        RomManager.initializeRom(byteArrayOf(0xFF.toByte())) // Ensure ROM is initialized
        assertFailsWith<IllegalStateException> {
            // Cannot Write value to ROM}
            RomManager.getRom()?.writeToMemory(0x00, 0xCD.toByte())
        }
    }

    @Test
    fun testPerformInstruction_ROMNotInitialized() {
        m.operateOnRegister(byteArrayOf(1)) // Switch to ROM mode
        assertFailsWith<IllegalStateException>("ROM is not initialized.") {
            instruction.runTask()
        }
    }
}
