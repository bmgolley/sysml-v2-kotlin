@file:Suppress("unused")

package sandbox.featurechains.kerml.root

import sandbox.kerml.root.Element
import sandbox.kerml.root.Import
import sandbox.kerml.root.Membership
import sandbox.kerml.root.Namespace
import sandbox.kerml.root.VisibilityKind

/** @see Import.importedElement */
val Iterable<Import>.importedElement: List<Element>
    get() = map(Import::importedElement)

/** @see Import.importOwningNamespace */
val Iterable<Import>.importOwningNamespace: List<Namespace>
    get() = map(Import::importOwningNamespace)

/** @see Import.source */
val Iterable<Import>.source: List<Element>
    get() = flatMap(Import::source)

/** @see Import.isImportAll */
val Iterable<Import>.isImportAll: List<Boolean>
    get() = map(Import::isImportAll)

/** @see Import.isRecursive */
val Iterable<Import>.isRecursive: List<Boolean>
    get() = map(Import::isRecursive)

/** @see Import.visibility */
val Iterable<Import>.visibility: List<VisibilityKind>
    get() = map(Import::visibility)

/** @see Import.importedMemberships */
fun Iterable<Import>.importedMemberships(excluded: Collection<Namespace> = emptySet()): List<Membership> =
    flatMap { it.importedMemberships(excluded) }
