@file:Suppress("unused")

package sandbox.paths.kerml.root.dependencies

import sandbox.kerml.root.dependencies.Dependency
import sandbox.kerml.root.elements.Element

val Iterable<Dependency>.client: List<Element>
    get() = flatMap(Dependency::client)

val Iterable<Dependency>.source: List<Element>
    get() = flatMap(Dependency::source)

val Iterable<Dependency>.supplier: List<Element>
    get() = flatMap(Dependency::supplier)

val Iterable<Dependency>.target: List<Element>
    get() = flatMap(Dependency::target)
