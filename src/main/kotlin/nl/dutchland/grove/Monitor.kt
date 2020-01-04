package nl.dutchland.grove

import java.util.concurrent.Semaphore

class Monitor {
    private val semaphore = Semaphore(0)
    var isRunning = true
        private set

    fun waitForStop() {
        semaphore.acquire()
        semaphore.release()
    }

    fun stop() {
        isRunning = false
        semaphore.release()
    }
}