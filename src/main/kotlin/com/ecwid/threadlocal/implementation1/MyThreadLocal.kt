package com.ecwid.threadlocal.implementation1

import java.util.WeakHashMap

class MyThreadLocal<T> private constructor() {
    constructor(value: T) : this() {
        this.value = value
    }

    @Suppress("UNCHECKED_CAST")
    var value: T?
        get() = (Thread.currentThread() as? MyThread)?.threadLocals?.map?.get(this) as T?
        set(value) = (Thread.currentThread() as? MyThread)?.threadLocals?.map?.set(this, value) ?: Unit

    private val hashcode: Int = Thread.currentThread().hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MyThreadLocal<*>) return false
        if (hashcode != other.hashcode) return false
        return true
    }

    override fun hashCode(): Int {
        return hashcode
    }

    override fun toString(): String {
        return "MyThreadLocal(value=$value)"
    }

    class ThreadLocalMap {
        val map: MutableMap<MyThreadLocal<*>, Any?> = WeakHashMap()
    }
}
