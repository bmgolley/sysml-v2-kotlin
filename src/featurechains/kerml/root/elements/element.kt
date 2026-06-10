@file:Suppress("unused")

package sandbox.featurechains.kerml.root.elements

import sandbox.kerml.root.annotations.Annotation
import sandbox.kerml.root.annotations.Documentation
import sandbox.kerml.root.annotations.TextualRepresentation
import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.OwningMembership

/** @see Element.aliasIds */
val Iterable<Element>.aliasIds: List<String>
    get() = flatMap(Element::aliasIds)

/** @see Element.declaredName */
val Iterable<Element>.declaredName: List<String?>
    get() = map(Element::declaredName)

/** @see Element.documentation */
val Iterable<Element>.documentation: List<Documentation>
    get() = flatMap(Element::documentation)

/** @see Element.elementId */
val Iterable<Element>.elementId: List<String>
    get() = map(Element::elementId)

/** @see Element.isImpliedIncluded */
val Iterable<Element>.isImpliedIncluded: List<Boolean>
    get() = map(Element::isImpliedIncluded)

/** @see Element.isLibraryElement */
val Iterable<Element>.isLibraryElement: List<Boolean>
    get() = map(Element::isLibraryElement)

/** @see Element.name */
val Iterable<Element>.name: List<String?>
    get() = map(Element::name)

/** @see Element.ownedAnnotation */
val Iterable<Element>.ownedAnnotation: List<Annotation>
    get() = flatMap(Element::ownedAnnotation)

/** @see Element.ownedElement */
val Iterable<Element>.ownedElement: List<Element>
    get() = flatMap(Element::ownedElement)

/** @see Element.ownedRelationship */
val Iterable<Element>.ownedRelationship: List<Relationship>
    get() = flatMap(Element::ownedRelationship)

/** @see Element.owner */
val Iterable<Element>.owner: List<Element?>
    get() = map(Element::owner)

/** @see Element.owningMembership */
val Iterable<Element>.owningMembership: List<OwningMembership?>
    get() = map(Element::owningMembership)

/** @see Element.owningNamespace */
val Iterable<Element>.owningNamespace: List<Namespace?>
    get() = map(Element::owningNamespace)

/** @see Element.owningRelationship */
val Iterable<Element>.owningRelationship: List<Relationship?>
    get() = map(Element::owningRelationship)

/** @see Element.qualifiedName */
val Iterable<Element>.qualifiedName: List<String?>
    get() = map(Element::qualifiedName)

/** @see Element.shortName */
val Iterable<Element>.shortName: List<String?>
    get() = map(Element::shortName)

/** @see Element.textualRepresentation */
val Iterable<Element>.textualRepresentation: List<TextualRepresentation>
    get() = flatMap(Element::textualRepresentation)

/** @see Element.effectiveName */
fun Iterable<Element>.effectiveName(): List<String?> = map(Element::effectiveName)

/** @see Element.effectiveShortName */
fun Iterable<Element>.effectiveShortName(): List<String?> = map(Element::effectiveShortName)

/** @see Element.escapedName */
fun Iterable<Element>.escapedName(): List<String?> = map(Element::escapedName)

/** @see Element.libraryNamespace */
fun Iterable<Element>.libraryNamespace(): List<Namespace?> = map(Element::libraryNamespace)

/** @see Element.path */
fun Iterable<Element>.path(): List<String> = map(Element::path)
