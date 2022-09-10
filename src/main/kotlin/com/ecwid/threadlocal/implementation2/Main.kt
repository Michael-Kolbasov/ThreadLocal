package com.ecwid.threadlocal.implementation2

import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

fun main() {
    val threadToItsThreadLocal = ConcurrentHashMap<Thread, MyThreadLocal<*>>()
    repeat(3) { i ->
        thread(name = i.toString()) {
            val current = Thread.currentThread()
            val threadLocal = MyThreadLocal(i)
            threadToItsThreadLocal[current] = threadLocal
            println("creating thread locals: ($i, $current) -> $threadLocal")
        }.join()
    }

    threadToItsThreadLocal.entries.forEach { (_, threadLocal) ->
        println("From the main thread, accessibility is lost: (${Thread.currentThread()}) -> ${threadLocal.get()}")
    }
}
