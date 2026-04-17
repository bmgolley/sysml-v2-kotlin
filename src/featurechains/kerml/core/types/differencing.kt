@file:Suppress("unused")

package sandbox.featurechains.kerml.core.types

import sandbox.kerml.core.types.Differencing
import sandbox.kerml.core.types.Type
import sandbox.kerml.root.elements.Element

/** @see Differencing.differencingType */
val Iterable<Differencing>.differencingType: List<Type>
    get() = map(Differencing::differencingType)

/** @see Differencing.target */
val Iterable<Differencing>.target: List<Element>
    get() = flatMap(Differencing::target)

/** @see Differencing.typeDifferenced */
val Iterable<Differencing>.typeDifferenced: List<Type>
    get() = map(Differencing::typeDifferenced)

/** @see Differencing.source */
val Iterable<Differencing>.source: List<Element>
    get() = flatMap(Differencing::source)
