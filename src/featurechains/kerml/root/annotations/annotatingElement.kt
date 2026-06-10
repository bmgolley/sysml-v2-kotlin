@file:Suppress("unused")

package sandbox.featurechains.kerml.root.annotations

import sandbox.kerml.root.annotations.AnnotatingElement
import sandbox.kerml.root.annotations.Annotation
import sandbox.kerml.root.elements.Element

/** @see AnnotatingElement.annotatedElement */
val Iterable<AnnotatingElement>.annotatedElement: List<Element>
    get() = flatMap(AnnotatingElement::annotatedElement)

/** @see AnnotatingElement.annotation */
val Iterable<AnnotatingElement>.annotation: List<Annotation>
    get() = flatMap(AnnotatingElement::annotation)

/** @see AnnotatingElement.ownedAnnotatingRelationship */
val Iterable<AnnotatingElement>.ownedAnnotatingRelationship: List<Annotation>
    get() = flatMap(AnnotatingElement::ownedAnnotatingRelationship)

/** @see AnnotatingElement.owningAnnotatingRelationship */
val Iterable<AnnotatingElement>.owningAnnotatingRelationship: List<Annotation?>
    get() = map(AnnotatingElement::owningAnnotatingRelationship)
