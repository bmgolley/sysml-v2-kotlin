@file:Suppress("unused")

package sandbox.featurechains.kerml.root.elements

import sandbox.kerml.root.annotations.Annotation
import sandbox.kerml.root.annotations.Documentation
import sandbox.kerml.root.annotations.TextualRepresentation
import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.OwningMembership

val Iterable<Element>.aliasIds: List<String>
    get() = flatMap(Element::aliasIds)

val Iterable<Element>.declaredName: List<String?>
    get() = map(Element::declaredName)

val Iterable<Element>.documentation: List<Documentation>
    get() = flatMap(Element::documentation)

val Iterable<Element>.elementId: List<String>
    get() = map(Element::elementId)

val Iterable<Element>.isImpliedIncluded: List<Boolean>
    get() = map(Element::isImpliedIncluded)

val Iterable<Element>.isLibraryElement: List<Boolean>
    get() = map(Element::isLibraryElement)

val Iterable<Element>.name: List<String?>
    get() = map(Element::name)

val Iterable<Element>.ownedAnnotation: List<Annotation>
    get() = flatMap(Element::ownedAnnotation)

val Iterable<Element>.ownedElement: List<Element>
    get() = flatMap(Element::ownedElement)

val Iterable<Element>.ownedRelationship: List<Relationship>
    get() = flatMap(Element::ownedRelationship)

val Iterable<Element>.owner: List<Element?>
    get() = map(Element::owner)

val Iterable<Element>.owningMembership: List<OwningMembership?>
    get() = map(Element::owningMembership)

val Iterable<Element>.owningNamespace: List<Namespace?>
    get() = map(Element::owningNamespace)

val Iterable<Element>.owningRelationship: List<Relationship?>
    get() = map(Element::owningRelationship)

val Iterable<Element>.qualifiedName: List<String?>
    get() = map(Element::qualifiedName)

val Iterable<Element>.shortName: List<String?>
    get() = map(Element::shortName)

val Iterable<Element>.textualRepresentation: List<TextualRepresentation>
    get() = flatMap(Element::textualRepresentation)

fun Iterable<Element>.effectiveName(): List<String?> = map(Element::effectiveName)

fun Iterable<Element>.effectiveShortName(): List<String?> = map(Element::effectiveShortName)

fun Iterable<Element>.escapedName(): List<String?> = map(Element::escapedName)

fun Iterable<Element>.libraryNamespace(): List<Namespace?> = map(Element::libraryNamespace)

fun Iterable<Element>.path(): List<String> = map(Element::path)
