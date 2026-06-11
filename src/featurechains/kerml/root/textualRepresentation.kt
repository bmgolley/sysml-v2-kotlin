@file:Suppress("unused")

package sandbox.featurechains.kerml.root

import sandbox.kerml.root.TextualRepresentation
import sandbox.kerml.root.Element

/** @see TextualRepresentation.annotatedElement */
val Iterable<TextualRepresentation>.annotatedElement: List<Element>
    get() = flatMap(TextualRepresentation::annotatedElement)

/** @see TextualRepresentation.body */
val Iterable<TextualRepresentation>.body: List<String>
    get() = map(TextualRepresentation::body)

/** @see TextualRepresentation.language */
val Iterable<TextualRepresentation>.language: List<String>
    get() = map(TextualRepresentation::language)

/** @see TextualRepresentation.representedElement */
val Iterable<TextualRepresentation>.representedElement: List<Element>
    get() = map(TextualRepresentation::representedElement)
