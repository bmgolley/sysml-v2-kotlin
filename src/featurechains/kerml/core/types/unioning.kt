@file:Suppress("unused")

package sandbox.featurechains.kerml.core.types

import sandbox.kerml.core.types.Type
import sandbox.kerml.core.types.Unioning

/** @see Unioning.unioningType */
val Iterable<Unioning>.unioningType: List<Type>
    get() = map(Unioning::unioningType)
