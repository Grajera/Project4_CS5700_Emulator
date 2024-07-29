package org.example

class Timer : Runnable {
    private var timerValue: Int = 0
    private var running: Boolean = false
    private val lock = Object()

    fun setTimer(value: Int) {
        synchronized(lock) {
            timerValue = value
        }
    }

    fun readTimer(): Int {
        synchronized(lock) {
            return timerValue
        }
    }

    fun start() {
        running = true
        Thread(this).start()
    }

    fun stop() {
        running = false
    }

    override fun run() {
        while (running) {
            Thread.sleep(16) // 16 milliseconds for 60Hz
            synchronized(lock) {
                if (timerValue > 0) {
                    timerValue--
                }
            }
        }
    }
}