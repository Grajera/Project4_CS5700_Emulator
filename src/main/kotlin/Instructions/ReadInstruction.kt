package Instructions

import com.emulator.Utils.byteArrayToInteger
import Memory.RamInstance.RAM
import Memory.RomInstance
import Memory.Registry_Handlers.ARegisterInstance.a
import Memory.Registry_Handlers.MRegisterInstance.m
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterInstance.r

class ReadInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]

        val address = byteArrayToInteger(a.readRegister())

        val value = if (m.readRegister()[0].toInt() != 0) {
            RomInstance.getRom()?.readMemoryAddress(address) ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            RAM.readMemoryAddress(address)
        }

        registerX.operateOnRegister(byteArrayOf(value))
    }
}
