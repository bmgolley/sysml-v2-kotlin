@file:Suppress("unused")

package sandbox.featurechains.kerml.root.namespaces

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.namespaces.Membership
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.VisibilityKind

/** @see Membership.memberElement */
val Iterable<Membership>.memberElement: List<Element>
    get() = map(Membership::memberElement)

/** @see Membership.target */
val Iterable<Membership>.target: List<Element>
    get() = flatMap(Membership::target)

/** @see Membership.memberElementId */
val Iterable<Membership>.memberElementId: List<String>
    get() = map(Membership::memberElementId)

/** @see Membership.memberName */
val Iterable<Membership>.memberName: List<String?>
    get() = map(Membership::memberName)

/** @see Membership.membershipOwningNamespace */
val Iterable<Membership>.membershipOwningNamespace: List<Namespace>
    get() = map(Membership::membershipOwningNamespace)

/** @see Membership.source */
val Iterable<Membership>.source: List<Element>
    get() = flatMap(Membership::source)

/** @see Membership.memberShortName */
val Iterable<Membership>.memberShortName: List<String?>
    get() = map(Membership::memberShortName)

/** @see Membership.visibility */
val Iterable<Membership>.visibility: List<VisibilityKind>
    get() = map(Membership::visibility)

/** @see Membership.isDistinguishableFrom */
fun Iterable<Membership>.isDistinguishableFrom(other: Membership): List<Boolean> =
    map { it.isDistinguishableFrom(other) }
