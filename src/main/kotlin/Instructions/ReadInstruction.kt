package Instructions

import com.emulator.byteArrayToInt
import Memory.RamManager.RAM
import Memory.RomManager
import Memory.Registry_Handlers.ARegisterManager.a
import Memory.Registry_Handlers.MRegisterManager.m
import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class ReadInstruction(
    nibbles: ByteArray
) : BasicInstruction(nibbles) {

    lateinit var registerX: R

    public override fun runTask() {
        registerX = r[nibbles[0].toInt()]

        val address = byteArrayToInt(a.readRegister())

        val value = if (m.readRegister()[0].toInt() != 0) {
            RomManager.getRom()?.read(address) ?: throw IllegalStateException("ROM is not initialized.")
        } else {
            RAM.read(address)
        }

        registerX.operateOnRegister(byteArrayOf(value))
    }
}
