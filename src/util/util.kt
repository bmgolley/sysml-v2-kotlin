package sandbox.util

infix fun Boolean.implies(other: Boolean): Boolean = !this || other
