@file:Suppress("unused")

package sandbox.featurechains.kerml.root.annotations

import sandbox.kerml.root.annotations.Documentation
import sandbox.kerml.root.elements.Element

/** @see Documentation.documentedElement */
val Iterable<Documentation>.documentedElement: List<Element>
    get() = map(Documentation::documentedElement)

/** @see Documentation.annotatedElement */
val Iterable<Documentation>.annotatedElement: List<Element>
    get() = flatMap(Documentation::annotatedElement)

