@file:Suppress("unused")

package sandbox.featurechains.kerml.root.namespaces

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.namespaces.Membership
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.Import
import sandbox.kerml.root.namespaces.VisibilityKind

val Iterable<Import>.importedElement: List<Element>
    get() = map(Import::importedElement)

val Iterable<Import>.importOwningNamespace: List<Namespace>
    get() = map(Import::importOwningNamespace)

val Iterable<Import>.source: List<Element>
    get() = flatMap(Import::source)

val Iterable<Import>.isImportAll: List<Boolean>
    get() = map(Import::isImportAll)

val Iterable<Import>.isRecursive: List<Boolean>
    get() = map(Import::isRecursive)

val Iterable<Import>.visibility: List<VisibilityKind>
    get() = map(Import::visibility)

fun Iterable<Import>.importedMemberships(excluded: Collection<Namespace> = emptySet()): List<Membership> =
    flatMap { it.importedMemberships(excluded) }
