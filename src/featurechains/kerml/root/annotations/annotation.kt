@file:Suppress("unused")

package sandbox.featurechains.kerml.root.annotations

import sandbox.kerml.root.annotations.AnnotatingElement
import sandbox.kerml.root.annotations.Annotation
import sandbox.kerml.root.elements.Element

/** @see Annotation.annotatedElement */
val Iterable<Annotation>.annotatedElement: List<Element>
    get() = map(Annotation::annotatedElement)

/** @see Annotation.target */
val Iterable<Annotation>.target: List<Element>
    get() = flatMap(Annotation::target)

/** @see Annotation.annotatingElement */
val Iterable<Annotation>.annotatingElement: List<AnnotatingElement>
    get() = map(Annotation::annotatingElement)

/** @see Annotation.ownedAnnotatingElement */
val Iterable<Annotation>.ownedAnnotatingElement: List<AnnotatingElement?>
    get() = map(Annotation::ownedAnnotatingElement)

/** @see Annotation.owningAnnotatedElement */
val Iterable<Annotation>.owningAnnotatedElement: List<Element?>
    get() = map(Annotation::owningAnnotatedElement)

/** @see Annotation.owningAnnotatingElement */
val Iterable<Annotation>.owningAnnotatingElement: List<AnnotatingElement?>
    get() = map(Annotation::owningAnnotatingElement)
