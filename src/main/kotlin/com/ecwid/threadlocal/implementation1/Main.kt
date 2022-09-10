package com.ecwid.threadlocal.implementation1

fun main() {
    // create 3 threads and print their thread local values from inside the thread context
    val threads = sequenceOf(1, 2, 3)
        .map { createThreadAndThreadLocal(value = it) }
        .toList()
        .onEach {
            it.start()
            it.join()
        }

    // access those 3 thread's thread locals from the main thread context -> value null
    threads.forEachIndexed { i, t ->
        val current = Thread.currentThread()
        val threadLocalFromAnotherThread = t.threadLocals.map[MyThreadLocal(i)]
        println("from the main thread, accessibility is lost: ($current) -> $threadLocalFromAnotherThread")
    }

    println()

    // create another thread with thread local, remove its value and access from the thread's context -> value null
    createThreadAndThreadLocal(value = 4, removeValue = true)
        .also {
            it.start()
            it.join()
        }
}

private fun createThreadAndThreadLocal(value: Int, removeValue: Boolean = false): MyThread =
    MyThread(name = value.toString()) {
        val current = Thread.currentThread()
        val threadLocal = MyThreadLocal(value)
        println("creating thread local: ($value, $current) -> ${threadLocal.get()}")

        if (removeValue) {
            println("removing thread local value: ($value, $current)")
            threadLocal.remove()
            println("thread local value removed: ($value, $current) -> ${threadLocal.get()}")
        }
    }
