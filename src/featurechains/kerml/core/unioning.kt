@file:Suppress("unused")

package sandbox.featurechains.kerml.core

import sandbox.kerml.core.Type
import sandbox.kerml.core.Unioning

/** @see Unioning.unioningType */
val Iterable<Unioning>.unioningType: List<Type>
    get() = map(Unioning::unioningType)
