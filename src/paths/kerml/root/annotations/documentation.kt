@file:Suppress("unused")

package sandbox.paths.kerml.root.annotations

import sandbox.kerml.root.annotations.Documentation
import sandbox.kerml.root.elements.Element

val Iterable<Documentation>.documentedElement: List<Element>
    get() = map(Documentation::documentedElement)

val Iterable<Documentation>.annotatedElement: List<Element>
    get() = flatMap(Documentation::annotatedElement)

