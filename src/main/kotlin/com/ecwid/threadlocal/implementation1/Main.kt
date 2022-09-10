package com.ecwid.threadlocal.implementation1

fun main() {
    val threads = mutableListOf<MyThread>()
    repeat(3) { i ->
        val thread = MyThread(i.toString()) {
            val current = Thread.currentThread()
            val threadLocal = MyThreadLocal(i)
            println("creating thread locals: ($i, $current) -> $threadLocal")
        }
        threads.add(thread)
    }

    threads.forEach {
        it.start()
        it.join()
    }

    threads.forEachIndexed { i, t ->
        val current = Thread.currentThread()
        val threadLocalFromAnotherThread = t.threadLocals.map[MyThreadLocal(i)]
        println("From the main thread, accessibility is lost: ($i, $current) -> $threadLocalFromAnotherThread")
    }
}
