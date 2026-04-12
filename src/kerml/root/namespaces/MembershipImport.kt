package sandbox.kerml.root.namespaces

import sandbox.kerml.root.elements.Element

/**
 * 
A MembershipImport is an Import that imports its importedMembership into the importOwningNamespace.
 * If isRecursive = true and the memberElement of the importedMembership is a Namespace, then the
 * equivalent of a recursive NamespaceImport is also performed on that Namespace.
 */
@Suppress("unused")
interface MembershipImport : Import {
    /**
     * The Membership to be imported.
     * 
     * importedMembership : Membership {redefines target}
     * 
     * Constraints
     * - deriveMembershipImportImportedElement
     *     The importedElement of a MembershipImport is the memberElement of its importedMembership.
     *     importedElement = importedMembership.memberElement
     */
    var importedMembership: Membership

    override val target: List<Element>
        get() = listOf(importedMembership)

    override val importedElement: Element
        get() = importedMembership.memberElement

    /**
     * Returns at least the importedMembership. If isRecursive = true and the memberElement of the
     * importedMembership is a Namespace, then Memberships are also recursively imported from that Namespace.
     * 
     * importedMemberships(excluded : Namespace [0..*]) : Membership [0..*] {redefines importedMemberships}
     * body: if not isRecursive or
     *     not importedElement.oclIsKindOf(Namespace) or
     *     excluded->includes(importedElement)
     * then Sequence{importedMembership}
     * else importedElement.oclAsType(Namespace).
     *     visibleMemberships(excluded, true, importAll)->
     *     prepend(importedMembership)
     * endif
     */
    override fun importedMemberships(excluded: Collection<Namespace>): List<Membership> =
        if (!isRecursive || importedElement !is Namespace || importedElement in excluded) {
            listOf(importedMembership)
        } else {
            buildList {
                add(importedMembership)
                addAll((importedElement as Namespace).visibleMemberships(excluded, true, isImportAll))
            }
        }
}




