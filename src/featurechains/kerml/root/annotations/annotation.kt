@file:Suppress("unused")

package sandbox.featurechains.kerml.root.annotations

import sandbox.kerml.root.annotations.AnnotatingElement
import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.annotations.Annotation

val Iterable<Annotation>.annotatedElement: List<Element>
    get() = map(Annotation::annotatedElement)

val Iterable<Annotation>.target: List<Element>
    get() = flatMap(Annotation::target)

val Iterable<Annotation>.annotatingElement: List<AnnotatingElement>
    get() = map(Annotation::annotatingElement)

val Iterable<Annotation>.ownedAnnotatingElement: List<AnnotatingElement?>
    get() = map(Annotation::ownedAnnotatingElement)

val Iterable<Annotation>.owningAnnotatedElement: List<Element?>
    get() = map(Annotation::owningAnnotatedElement)

val Iterable<Annotation>.owningAnnotatingElement: List<AnnotatingElement?>
    get() = map(Annotation::owningAnnotatingElement)
