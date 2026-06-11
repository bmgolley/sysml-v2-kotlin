@file:Suppress("unused")

package sandbox.featurechains.kerml.root

import sandbox.kerml.root.Dependency
import sandbox.kerml.root.Element

/** @see Dependency.client */
val Iterable<Dependency>.client: List<Element>
    get() = flatMap(Dependency::client)

/** @see Dependency.client */
val Iterable<Dependency>.source: List<Element>
    get() = flatMap(Dependency::source)

/** @see Dependency.supplier */
val Iterable<Dependency>.supplier: List<Element>
    get() = flatMap(Dependency::supplier)

/** @see Dependency.target */
val Iterable<Dependency>.target: List<Element>
    get() = flatMap(Dependency::target)
