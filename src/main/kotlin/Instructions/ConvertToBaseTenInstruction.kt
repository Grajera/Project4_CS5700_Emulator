package Instructions

import com.emulator.Utils.byteArrayToInteger
import Memory.RamInstance.RAM
import Memory.RomInstance
import Memory.Registry_Handlers.ARegisterInstance.a
import Memory.Registry_Handlers.MRegisterInstance.m
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r

class ConvertToBaseTenInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]

        val address = byteArrayToInteger(a.readRegister())
        val value = registerX.readRegister()[0].toInt()
        require(value in 0..0xFF) { "Value in registerX must be between 0 and 255." }

        val digits = intArrayOf(value / 100, (value % 100) / 10, value % 10)

        if (m.readRegister()[0].toInt() != 0) {
            RomInstance.getRom()?.let { rom ->
                for (i in digits.indices) {
                    rom.writeToMemory(address + i, digits[i].toByte())
                }
            } ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            for (i in digits.indices) {
                RAM.writeToMemory(address + i, digits[i].toByte())
            }
        }
    }
}
