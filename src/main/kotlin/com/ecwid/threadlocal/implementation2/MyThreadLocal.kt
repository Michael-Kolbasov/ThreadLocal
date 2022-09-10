package com.ecwid.threadlocal.implementation2

import java.util.Collections
import java.util.Objects
import java.util.WeakHashMap

class MyThreadLocal<T>(private var value: T?) {
    init {
        val current = Thread.currentThread()
        if (map.containsKey(current)) {
            map[current]?.put(this, value)
        } else {
            map[current] = mutableMapOf(this to value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun get(): T? = getMap()?.get(this) as? T

    fun set(value: T?) {
        getMap()?.set(this, value)
    }

    fun remove() {
        this.value = null
        set(null)
    }

    private fun getMap() = map[Thread.currentThread()]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MyThreadLocal<*>) return false
        if (value != other.value) return false
        return true
    }

    override fun hashCode() = Objects.hash(Thread.currentThread(), value)

    override fun toString() = "MyThreadLocal(value=$value)"

    private companion object {
        private val map: MutableMap<Thread, MutableMap<MyThreadLocal<*>, Any?>> = Collections.synchronizedMap(WeakHashMap())
    }
}
