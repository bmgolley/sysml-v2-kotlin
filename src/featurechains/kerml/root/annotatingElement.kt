@file:Suppress("unused")

package sandbox.featurechains.kerml.root

import sandbox.kerml.root.AnnotatingElement
import sandbox.kerml.root.Annotation
import sandbox.kerml.root.Element

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
