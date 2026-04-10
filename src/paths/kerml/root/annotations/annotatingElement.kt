@file:Suppress("unused")

package sandbox.paths.kerml.root.annotations

import sandbox.kerml.root.annotations.AnnotatingElement
import sandbox.kerml.root.annotations.Annotation
import sandbox.kerml.root.elements.Element

val Iterable<AnnotatingElement>.annotatedElement: List<Element>
    get() = flatMap(AnnotatingElement::annotatedElement)

val Iterable<AnnotatingElement>.annotation: List<Annotation>
    get() = flatMap(AnnotatingElement::annotation)

val Iterable<AnnotatingElement>.ownedAnnotatingRelationship: List<Annotation>
    get() = flatMap(AnnotatingElement::ownedAnnotatingRelationship)

val Iterable<AnnotatingElement>.owningAnnotatingRelationship: List<Annotation>
    get() = mapNotNull(AnnotatingElement::owningAnnotatingRelationship)
