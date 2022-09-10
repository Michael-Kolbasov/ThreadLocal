package com.ecwid.threadlocal.implementation2

import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

fun main() {
    // create 3 threads and print their thread local values from inside the thread context
    val threadToItsThreadLocal = ConcurrentHashMap<Thread, MyThreadLocal<*>>()
    repeat(3) { i ->
        thread(name = i.toString()) {
            val current = Thread.currentThread()
            val threadLocal = MyThreadLocal(i)
            threadToItsThreadLocal[current] = threadLocal
            println("creating thread locals: ($i, $current) -> ${threadLocal.get()}")
        }.join()
    }


    // access those 3 thread's thread locals from the main thread context -> value null
    threadToItsThreadLocal.entries.forEach { (_, threadLocal) ->
        println("From the main thread, accessibility is lost: (${Thread.currentThread()}) -> ${threadLocal.get()}")
    }

    println()

    // create another thread with thread local, remove its value and access from the thread's context -> value null
    val nextValue = 4
    thread(name = nextValue.toString()) {
        val current = Thread.currentThread()
        val threadLocal = MyThreadLocal(nextValue)
        threadToItsThreadLocal[current] = threadLocal
        println("creating thread locals: ($nextValue, $current) -> ${threadLocal.get()}")

        println("removing thread local value: ($nextValue, $current)")
        threadLocal.remove()
        println("thread local value removed: ($nextValue, $current) -> ${threadLocal.get()}")
    }.join()
}
