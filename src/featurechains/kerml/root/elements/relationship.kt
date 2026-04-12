@file:Suppress("unused")

package sandbox.featurechains.kerml.root.elements

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship
import sandbox.kerml.root.namespaces.Namespace

val Iterable<Relationship>.isImplied: List<Boolean>
    get() = map(Relationship::isImplied)

val Iterable<Relationship>.ownedRelatedElement: List<Element>
    get() = flatMap(Relationship::ownedRelatedElement)

val Iterable<Relationship>.owningRelatedElement: List<Element?>
    get() = map(Relationship::owningRelatedElement)

val Iterable<Relationship>.relatedElement: List<Element>
    get() = flatMap(Relationship::relatedElement)

val Iterable<Relationship>.source: List<Element>
    get() = flatMap(Relationship::source)

val Iterable<Relationship>.target: List<Element>
    get() = flatMap(Relationship::target)

fun Iterable<Relationship>.libraryNamespace(): List<Namespace?> = map(Relationship::libraryNamespace)

fun Iterable<Relationship>.path(): List<String> = map(Relationship::path)
