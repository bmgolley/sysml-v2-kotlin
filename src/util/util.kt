@file:Suppress("unused")

package sandbox.util

import kotlin.contracts.contract

infix fun Boolean.implies(other: Boolean): Boolean = !this || other

infix fun Boolean.implies(other: () -> Boolean): Boolean {
    contract {
        callsInPlace(other, AT_MOST_ONCE)
        this@implies holdsIn other
    }
    return !this || other()
}

inline fun <reified R> Iterable<*>.firstIsInstance(): R = first { it is R } as R
inline fun <reified R> Iterable<*>.firstIsInstanceOrNull(): R? = firstOrNull { it is R } as R?
