@file:Suppress("unused")

package sandbox.featurechains.kerml.core.types

import sandbox.kerml.core.types.Differencing
import sandbox.kerml.core.types.Type
import sandbox.kerml.root.elements.Element

val Iterable<Differencing>.differencingType: List<Type>
    get() = map(Differencing::differencingType)

val Iterable<Differencing>.target: List<Element>
    get() = flatMap(Differencing::target)

val Iterable<Differencing>.typeDifferenced: List<Type>
    get() = map(Differencing::typeDifferenced)

val Iterable<Differencing>.source: List<Element>
    get() = flatMap(Differencing::source)
