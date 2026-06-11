@file:Suppress("unused")

package sandbox.featurechains.kerml.root

import sandbox.kerml.root.Element
import sandbox.kerml.root.Import
import sandbox.kerml.root.Membership
import sandbox.kerml.root.Namespace

/** @see Namespace.importedMembership */
val Iterable<Namespace>.importedMembership: List<Membership>
    get() = flatMap(Namespace::importedMembership)

/** @see Namespace.member */
val Iterable<Namespace>.member: List<Element>
    get() = flatMap(Namespace::member)

/** @see Namespace.membership */
val Iterable<Namespace>.membership: List<Membership>
    get() = flatMap(Namespace::membership)

/** @see Namespace.ownedImport */
val Iterable<Namespace>.ownedImport: List<Import>
    get() = flatMap(Namespace::ownedImport)

/** @see Namespace.ownedMember */
val Iterable<Namespace>.ownedMember: List<Element>
    get() = flatMap(Namespace::ownedMember)

/** @see Namespace.ownedMembership */
val Iterable<Namespace>.ownedMembership: List<Membership>
    get() = flatMap(Namespace::ownedMembership)
