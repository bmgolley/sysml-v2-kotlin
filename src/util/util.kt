package sandbox.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.ExperimentalExtendedContracts
import kotlin.contracts.contract

infix fun Boolean.implies(other: Boolean): Boolean = !this || other

@OptIn(ExperimentalContracts::class, ExperimentalExtendedContracts::class)
infix fun Boolean.implies(other: () -> Boolean): Boolean {
    contract {
        callsInPlace(other, AT_MOST_ONCE)
        this@implies holdsIn other
    }
    return !this || other()
}
