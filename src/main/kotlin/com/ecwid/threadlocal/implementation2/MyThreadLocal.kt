package com.ecwid.threadlocal.implementation2

import java.util.WeakHashMap

class MyThreadLocal<T>(private val value: T?) {
    init {
        val current = Thread.currentThread()
        if (map.containsKey(current)) {
            map[current]?.put(this, value)
        } else {
            map[current] = mutableMapOf(this to value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun get(): T? = map[Thread.currentThread()]?.get(this) as? T

    private val hashCode: Int = Thread.currentThread().hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MyThreadLocal<*>) return false
        if (hashCode != other.hashCode) return false
        return true
    }

    override fun hashCode(): Int {
        return hashCode
    }

    override fun toString(): String {
        return "MyThreadLocal(value=$value)"
    }

    private companion object {
        private val map: MutableMap<Thread, MutableMap<MyThreadLocal<*>, Any?>> = WeakHashMap()
    }
}
