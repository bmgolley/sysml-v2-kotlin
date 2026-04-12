@file:Suppress("unused")

package sandbox.featurechains.kerml.root.namespaces

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.namespaces.Membership
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.VisibilityKind

val Iterable<Membership>.memberElement: List<Element>
    get() = map(Membership::memberElement)

val Iterable<Membership>.target: List<Element>
    get() = flatMap(Membership::target)

val Iterable<Membership>.memberElementId: List<String>
    get() = map(Membership::memberElementId)

val Iterable<Membership>.memberName: List<String?>
    get() = map(Membership::memberName)

val Iterable<Membership>.membershipOwningNamespace: List<Namespace>
    get() = map(Membership::membershipOwningNamespace)

val Iterable<Membership>.source: List<Element>
    get() = flatMap(Membership::source)

val Iterable<Membership>.memberShortName: List<String?>
    get() = map(Membership::memberShortName)

val Iterable<Membership>.visibility: List<VisibilityKind>
    get() = map(Membership::visibility)

fun Iterable<Membership>.isDistinguishableFrom(other: Membership): List<Boolean> =
    map { it.isDistinguishableFrom(other) }
