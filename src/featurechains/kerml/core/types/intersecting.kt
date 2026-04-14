@file:Suppress("unused")

package sandbox.featurechains.kerml.core.types

import sandbox.kerml.core.types.Intersecting
import sandbox.kerml.core.types.Type
import sandbox.kerml.root.elements.Element

/** @see Intersecting.intersectingType */
val Iterable<Intersecting>.intersectingType: List<Type>
    get() = map(Intersecting::intersectingType)

/** @see Intersecting.target */
val Iterable<Intersecting>.target: List<Element>
    get() = flatMap(Intersecting::target)

/** @see Intersecting.typeIntersected */
val Iterable<Intersecting>.typeIntersected: List<Type>
    get() = map(Intersecting::typeIntersected)

/** @see Intersecting.source */
val Iterable<Intersecting>.source: List<Element>
    get() = flatMap(Intersecting::source)
