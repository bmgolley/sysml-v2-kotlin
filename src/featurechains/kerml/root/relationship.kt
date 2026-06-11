@file:Suppress("unused")

package sandbox.featurechains.kerml.root

import sandbox.kerml.root.Element
import sandbox.kerml.root.Relationship
import sandbox.kerml.root.Namespace

/** @see Relationship.isImplied */
val Iterable<Relationship>.isImplied: List<Boolean>
    get() = map(Relationship::isImplied)

/** @see Relationship.ownedRelatedElement */
val Iterable<Relationship>.ownedRelatedElement: List<Element>
    get() = flatMap(Relationship::ownedRelatedElement)

/** @see Relationship.owningRelatedElement */
val Iterable<Relationship>.owningRelatedElement: List<Element?>
    get() = map(Relationship::owningRelatedElement)

/** @see Relationship.relatedElement */
val Iterable<Relationship>.relatedElement: List<Element>
    get() = flatMap(Relationship::relatedElement)

/** @see Relationship.source */
val Iterable<Relationship>.source: List<Element>
    get() = flatMap(Relationship::source)

/** @see Relationship.target */
val Iterable<Relationship>.target: List<Element>
    get() = flatMap(Relationship::target)

/** @see Relationship.libraryNamespace */
fun Iterable<Relationship>.libraryNamespace(): List<Namespace?> = map(Relationship::libraryNamespace)

/** @see Relationship.path */
fun Iterable<Relationship>.path(): List<String> = map(Relationship::path)
