package Instructions

import Memory.Registry_Handlers.R
import Memory.Registry_Handlers.RRegisterManager.r

class SubtractInstruction (
    nibbles: ByteArray
) : BasicInstruction(nibbles) {
    lateinit var rx: R
    lateinit var ry: R
    lateinit var rz: R

    public override fun processNibbles() {
        val rxIndex = nibbles[0].toInt()
        val ryIndex = nibbles[1].toInt()
        val rzIndex = nibbles[2].toInt()

        rx = r[rxIndex]
        ry = r[ryIndex]
        rz = r[rzIndex]
    }

    public override fun performOperation() {
        val rxValue = rx.read()[0].toInt()
        val ryValue = ry.read()[0].toInt()

        val result = (rxValue - ryValue).toByte()

        rz.operate(byteArrayOf(result))
    }
}