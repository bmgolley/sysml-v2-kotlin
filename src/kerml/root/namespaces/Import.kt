package sandbox.kerml.root.namespaces

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * An Import is an Relationship between its importOwningNamespace and either a Membership (for a
 * MembershipImport) or another Namespace (for a NamespaceImport), which determines a set of Memberships
 * that become importedMemberships of the importOwningNamespace. If isImportAll = false (the default),
 * then only public Memberships are considered "visible". If isImportAll = true, then all Memberships are
 * considered "visible", regardless of their declared visibility. If isRecursive = true, then visible
 * Memberships are also recursively imported from owned sub-Namespaces.
 * 
 * Constraints
 * - validateImportTopLevelVisibility
 *     A top-level Import (that is, one that is owned by a root Namespace) must have a visibility of private.
 *     ```ocl
 *     importOwningNamespace.owner = null implies
 *         visibility = VisibilityKind::private
 *     ```
 */
interface Import : Relationship {
    /**
     * The effectively imported Element for this Import. For a MembershipImport, this is the memberElement of the
     * importedMembership. For a NamespaceImport, it is the importedNamespace.
     * 
     * /importedElement : Element
     */
    val importedElement: Element

    /**
     * The Namespace into which Memberships are imported by this Import, which must be the owningRelatedElement
     * of the Import.
     * 
     * /importOwningNamespace : Namespace {subsets owningRelatedElement, redefines source}
     */
    val importOwningNamespace: Namespace
        get() = checkNotNull(owningRelatedElement) as Namespace

    override val source: List<Element>
        get() = listOf(importOwningNamespace)

    /**
     * Whether to import memberships without regard to declared visibility.
     * 
     * isImportAll : Boolean
     */
    var isImportAll: Boolean

    /**
     * Whether to recursively import Memberships from visible, owned sub-Namespaces.
     * 
     * isRecursive : Boolean
     */
    var isRecursive: Boolean

    /**
     * The visibility level of the imported members from this Import relative to the importOwningNamespace. The
     * default is private.
     * 
     * visibility : VisibilityKind
     */
    var visibility: VisibilityKind

    /**
     * Returns Memberships that are to become importedMemberships of the importOwningNamespace. (The
     * excluded parameter is used to handle the possibility of circular Import Relationships.)
     * 
     * importedMemberships(excluded : Namespace [0..*]) : Membership [0..*]
     */
    fun importedMemberships(excluded: Collection<Namespace> = emptySet()): List<Membership>
}
