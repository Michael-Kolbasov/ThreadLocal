package com.ecwid.threadlocal.implementation1

class MyThread(name: String, task: Runnable) : Thread(task, name) {
    val threadLocals = MyThreadLocal.ThreadLocalMap()

    override fun toString(): String {
        return "MyThread(name=$name)"
    }
}
