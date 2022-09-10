package com.ecwid.threadlocal.implementation1

import java.util.Objects
import java.util.WeakHashMap

class MyThreadLocal<T>(private var value: T?) {
    init {
        set(value)
    }

    @Suppress("UNCHECKED_CAST")
    fun get() = getMap()?.get(this) as T?

    fun set(value: T?) = getMap()?.set(this, value) ?: Unit

    fun remove() {
        value = null
        set(null)
    }

    private fun getMap() = (Thread.currentThread() as? MyThread)?.threadLocals?.map

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MyThreadLocal<*>) return false
        if (value != other.value) return false
        return true
    }

    override fun hashCode() = Objects.hash(Thread.currentThread(), value)

    override fun toString() = "MyThreadLocal(value=$value)"

    class ThreadLocalMap {
        val map: MutableMap<MyThreadLocal<*>, Any?> = WeakHashMap()
    }
}
