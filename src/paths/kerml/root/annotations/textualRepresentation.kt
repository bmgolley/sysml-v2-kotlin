@file:Suppress("unused")

package sandbox.paths.kerml.root.annotations

import sandbox.kerml.root.annotations.TextualRepresentation
import sandbox.kerml.root.elements.Element

val Iterable<TextualRepresentation>.body: List<String>
    get() = map(TextualRepresentation::body)

val Iterable<TextualRepresentation>.language: List<String>
    get() = map(TextualRepresentation::language)

val Iterable<TextualRepresentation>.representedElement: List<Element>
    get() = map(TextualRepresentation::representedElement)

val Iterable<TextualRepresentation>.annotatedElement: List<Element>
    get() = flatMap(TextualRepresentation::annotatedElement)
