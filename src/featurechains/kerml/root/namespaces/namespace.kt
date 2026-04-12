@file:Suppress("unused")

package sandbox.featurechains.kerml.root.namespaces

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.namespaces.Import
import sandbox.kerml.root.namespaces.Membership
import sandbox.kerml.root.namespaces.Namespace

val Iterable<Namespace>.importedMembership: List<Membership>
    get() = flatMap(Namespace::importedMembership)

val Iterable<Namespace>.member: List<Element>
    get() = flatMap(Namespace::member)

val Iterable<Namespace>.membership: List<Membership>
    get() = flatMap(Namespace::membership)

val Iterable<Namespace>.ownedImport: List<Import>
    get() = flatMap(Namespace::ownedImport)

val Iterable<Namespace>.ownedMember: List<Element>
    get() = flatMap(Namespace::ownedMember)

val Iterable<Namespace>.ownedMembership: List<Membership>
    get() = flatMap(Namespace::ownedMembership)
