@file:Suppress("unused")

package sandbox.featurechains.kerml.core.types

import sandbox.kerml.core.types.Specialization
import sandbox.kerml.core.types.Type
import sandbox.kerml.root.elements.Element

/** @see Specialization.general */
val Iterable<Specialization>.general: List<Type>
    get() = map(Specialization::general)

/** @see Specialization.target */
val Iterable<Specialization>.target: List<Element>
    get() = flatMap(Specialization::target)

/** @see Specialization.owningType */
val Iterable<Specialization>.owningType: List<Type?>
    get() = map(Specialization::owningType)

/** @see Specialization.specific */
val Iterable<Specialization>.specific: List<Type>
    get() = map(Specialization::specific)

/** @see Specialization.source */
val Iterable<Specialization>.source: List<Element>
    get() = flatMap(Specialization::source)
