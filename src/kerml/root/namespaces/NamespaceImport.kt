package sandbox.kerml.root.namespaces

import sandbox.kerml.root.elements.Element

/**
 * A NamespaceImport is an Import that imports Memberships from its importedNamespace into the
 * importOwningNamespace. If isRecursive = false, then only the visible Memberships of the
 * importedNamespace are imported. If isRecursive = true, then, in addition, Memberships are recursively
 * imported from any ownedMembers of the importedNamespace that are Namespaces.
 */
interface NamespaceImport : Import {
    /**
     * The Namespace whose visible Memberships are imported by this NamespaceImport.
     * 
     * `importedNamespace : Namespace {redefines target}`
     */
    var importedNamespace: Namespace
    
    override val target: List<Element>
        get() = listOf(importedNamespace)
    
    /**
     * - deriveNamespaceImportImportedElement
     *    The importedElement of a NamespaceImport is its importedNamespace.
     *    importedElement = importedNamespace
     */
    override val importedElement: Namespace
        get() = importedNamespace
    
    /**
     * Returns at least the visible Memberships of the importedNamespace. If isRecursive = true, then
     * Memberships are also recursively imported from any ownedMembers of the importedNamespace that are
     * themselves Namespaces.
     * 
     * `importedMemberships(excluded : Namespace [0..*]) : Membership [0..*] {redefines importedMemberships}`
     * ```ocl
     * body: if excluded->includes(importedNamespace) then Sequence{}
     * else importedNamespace.visibleMemberships(excluded, isRecursive, isImportAll)
     * ```
     */
    override fun importedMemberships(excluded: Collection<Namespace>): List<Membership> =
        if (importedNamespace in excluded) {
            emptyList()
        } else {
            importedNamespace.visibleMemberships(excluded, isRecursive, isImportAll)
        }
}
