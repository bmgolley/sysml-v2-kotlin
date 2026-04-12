@file:Suppress("unused")

package sandbox.kerml.root.namespaces

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * An OwningMembership is a Membership that owns its memberElement as a ownedRelatedElement. The
 * ownedMemberElement becomes an ownedMember of the membershipOwningNamespace.
 */
interface OwningMembership : Membership {
    /**
     * The Element that becomes an ownedMember of the membershipOwningNamespace due to this
     * OwningMembership.
     * 
     * /ownedMemberElement : Element {subsets ownedRelatedElement, redefines memberElement}
     */
    val ownedMemberElement: Element
        get() = memberElement

    /**
     * The elementId of the ownedMemberElement.
     * 
     * /ownedMemberElementId : String {redefines memberElementId}
     */
    val ownedMemberElementId: String

    override val memberElementId: String
        get() = ownedMemberElementId

    /**
     * The name of the ownedMemberElement.
     * 
     * /ownedMemberName : String [0..1] {redefines memberName}
     * 
     * deriveOwningMembershipOwnedMemberName
     * The ownedMemberName of an OwningMembership is the name of its ownedMemberElement.
     * ownedMemberName = ownedMemberElement.name
     */
    val ownedMemberName: String?
        get() = ownedMemberElement.name

    /**
     * The shortName of the ownedMemberElement
     * 
     * /ownedMemberShortName : String [0..1] {redefines memberShortName}
     * 
     * deriveOwningMembershipOwnedMemberShortName
     * The ownedMemberShortName of an OwningMembership is the shortName of its ownedMemberElement.
     * ownedMemberShortName = ownedMemberElement.shortName
     */
    val ownedMemberShortName: String?
        get() = ownedMemberElement.shortName

    /**
     * If the ownedMemberElement of this OwningMembership has a non-null qualifiedName, then return the string
     * constructed by appending to that qualifiedName the string "/owningMembership". Otherwise, return the path
     * of the OwningMembership as specified for a Relationship
     * 
     * path() : String {redefines path}
     * body: if ownedElement.qualifiedName <> null then
     * ownedElement.qualifiedName + '/owningMembership'
     * else self.oclAsType(Relationship).path()
     * endif
     */
    override fun path(): String =
        ownedMemberElement.qualifiedName?.plus("/owningMembership") ?: (this as Relationship).path()
}
