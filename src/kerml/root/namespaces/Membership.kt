package sandbox.kerml.root.namespaces

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * A `Membership` is a [Relationship] between a [Namespace] and an [Element] that indicates the Element is a
 * member of (i.e., is contained in) the [Namespace]. Any memberNames specify how the [memberElement] is identified
 * in the Namespace and the visibility specifies whether or not the [memberElement] is publicly visible from
 * outside the Namespace.
 * If a `Membership` is an [OwningMembership], then it owns its [memberElement], which becomes an ownedMember
 * of the [membershipOwningNamespace]. Otherwise, the memberNames of a `Membership` are effectively aliases
 * within the [membershipOwningNamespace] for an [Element] with a separate [OwningMembership] in the same or a
 * different [Namespace].
 */
interface Membership : Relationship {
    /**
     * The [Element] that becomes a member of the [membershipOwningNamespace] due to this Membership.
     * 
     * memberElement : Element {redefines target}
     */
    var memberElement: Element

    override val target: List<Element>
        get() = listOf(memberElement)

    /**
     * The elementId of the memberElement.
     *
     * ```ocl
     * /memberElementId : String
     * 
     * memberElementId = memberElement.elementId
     * ```
     */
    val memberElementId: String
        get() = memberElement.elementId

    /**
     * The name of the memberElement relative to the membershipOwningNamespace.
     * 
     * memberName : String [0..1]
     */
    var memberName: String?

    /**
     * The Namespace of which the memberElement becomes a member due to this Membership.
     * 
     * /membershipOwningNamespace : Namespace {subsets membershipNamespace, owningRelatedElement, redefines source}
     */
    val membershipOwningNamespace: Namespace

    override val source: List<Element>
        get() = listOf(membershipOwningNamespace)

    /**
     * The short name of the memberElement relative to the membershipOwningNamespace.
     * 
     * memberShortName : String [0..1]
     */
    var memberShortName: String?

    /**
     * Whether or not the Membership of the memberElement in the membershipOwningNamespace is publicly visible
     * outside that Namespace.
     * 
     * visibility : VisibilityKind
     */
    var visibility: VisibilityKind

    /**
     * Whether this Membership is distinguishable from a given other Membership. By default, this is true if this
     * Membership has no memberShortName or memberName; or each of the memberShortName and memberName are
     * different than both of those of the other Membership; or neither of the metaclasses of the memberElement of this
     * Membership and the memberElement of the other Membership conform to the other. But this may be
     * overridden in specializations of Membership.
     * 
     * isDistinguishableFrom(other : Membership) : Boolean
     * body: not (memberElement.oclKindOf(other.memberElement.oclType()) or
     *     other.memberElement.oclKindOf(memberElement.oclType())) or
     * (shortMemberName = null or
     *     (shortMemberName <> other.shortMemberName and
     *     shortMemberName <> other.memberName)) and
     * (memberName = null or
     *     (memberName <> other.shortMemberName and
     *     memberName <> other.memberName)))
     */
    fun isDistinguishableFrom(other: Membership): Boolean =
        !(other.memberElement::class.isInstance(memberElement) || memberElement::class.isInstance(other.memberElement))
            || (memberShortName == null || (memberShortName != other.memberShortName && memberShortName != other.memberName))
            && (memberName == null || (memberName != other.memberShortName && memberName != other.memberName))
}
