package sandbox.kerml.root.namespaces

import sandbox.kerml.root.elements.Element

/**
 * 
 * A `Namespace` is an [Element] that contains other Elements, known as its members, via [Membership]
 * Relationships with those Elements. The members of a Namespace may be owned by the Namespace, aliased
 * in the Namespace, or imported into the Namespace via [Import] Relationships.
 * A Namespace can provide names for its members via the memberNames and memberShortNames specified by the
 * Memberships in the Namespace. If a Membership specifies a memberName and/or memberShortName, then
 * those are names of the corresponding memberElement relative to the Namespace. For an OwningMembership, the
 * ownedMemberName and ownedMemberShortName are given by the Element name and shortName. Note that the
 * same Element may be the memberElement of multiple Memberships in a Namespace (though it may be owned
 * at most once), each of which may define a separate alias for the Element relative to the Namespace.
 */
@Suppress("unused")
interface Namespace : Element {
    /**
     * The Memberships in this Namespace that result from the ownedImports of this Namespace.
     * 
     * /importedMembership : Membership [0..*] {subsets membership, ordered}
     * Constraints:
     * deriveNamespaceImportedMembership
     * The importedMemberships of a Namespace are derived using the importedMemberships() operation, with no
     * initially excluded Namespaces.
     * importedMembership = importedMemberships(Set{})
     */
    val importedMembership: List<Membership>
        get() = importedMemberships()

    /**
     * The set of all member Elements of this Namespace, which are the memberElements of all memberships of the
     * Namespace.
     * 
     * /member : Element [0..*] {ordered}
     * deriveNamespaceMembers
     * The members of a Namespace are the memberElements of all its memberships.
     * member = membership.memberElement
     */
    val member: List<Element>
        get() = membership.map(Membership::memberElement)

    /**
     * All Memberships in this Namespace, including (at least) the union of ownedMemberships and
     * importedMemberships.
     * 
     * /membership : Membership [0..*] {ordered, union}
     */
    val membership: List<Membership>
        get() = ownedMembership + importedMembership

    /**
     * The ownedRelationships of this Namespace that are Imports, for which the Namespace is the
     * importOwningNamespace.
     * 
     * /ownedImport : Import [0..*] {subsets sourceRelationship, ownedRelationship, ordered}
     * deriveNamespaceOwnedImport
     * The ownedImports of a Namespace are all its ownedRelationships that are Imports.
     * ownedImport = ownedRelationship->selectByKind(Import)
     */
    val ownedImport: List<Import>
        get() = ownedRelationship.filterIsInstance<Import>()

    /**
     * The owned members of this Namespace, which are the ownedMemberElements of the ownedMemberships of the
     * Namespace.
     * 
     * /ownedMember : Element [0..*] {subsets member, ordered}
     * deriveNamespaceOwnedMember
     * The ownedMembers of a Namespace are the ownedMemberElements of all its ownedMemberships that are
     * OwningMemberships
     * ownedMember = ownedMembership->selectByKind(OwningMembership).ownedMemberElement
     */
    val ownedMember: List<Element>
        get() = ownedMembership.filterIsInstance<OwningMembership>().map(OwningMembership::ownedMemberElement)

    /**
     * The ownedRelationships of this Namespace that are Memberships, for which the Namespace is the
     * membershipOwningNamespace.
     * 
     * /ownedMembership : Membership [0..*] {subsets membership, sourceRelationship, ownedRelationship, ordered}
     * deriveNamespaceOwnedMembership
     * The ownedMemberships of a Namespace are all its ownedRelationships that are Memberships.
     * ownedMembership = ownedRelationship->selectByKind(Membership)
     */
    val ownedMembership: List<Membership>
        get() = ownedRelationship.filterIsInstance<Membership>()

    /**
     * Derive the imported Memberships of this Namespace as the importedMembership of all ownedImports,
     * excluding those Imports whose importOwningNamespace is in the excluded set, and excluding Memberships
     * that have distinguisibility collisions with each other or with any ownedMembership.
     * 
     * importedMemberships(excluded : Namespace [0..*]) : Membership [0..*]
     * body: ownedImport.importedMemberships(excluded->including(self))
     */
    fun importedMemberships(excluded: Collection<Namespace> = emptySet()): List<Membership> {
        val excludedSelf = buildSet { add(this@Namespace); addAll(excluded) }
        return ownedImport.flatMap { it.importedMemberships(excludedSelf) }
    }

    /**
     * If [visibility] is not `null`, return the [Memberships][Membership] of this `Namespace` with the given [Membership.visibility], including
     * [ownedMemberships][ownedMembership] with the given [Membership.visibility] and [Memberships][Membership] imported with the given [Membership.visibility]. If
     * [visibility] is `null`, return all [ownedMemberships][ownedMembership] and imported [Memberships][Membership] regardless of [Membership.visibility]. When
     * computing imported [Memberships][Membership], ignore this `Namespace` and any `Namespaces` in the given excluded set.
     *
     * membershipsOfVisibility(visibility : VisibilityKind [0..1], excluded : Namespace [0..*]) : Membership [0..*]
     * body: ownedMembership->
     *     select(mem | visibility = null or mem.visibility = visibility)->
     *     union(ownedImport->
     *         select(imp | visibility = null or imp.visibility = visibility).
     *         importedMemberships(excluded->including(self)))
     */
    fun membershipsOfVisibility(
        visibility: VisibilityKind?,
        excluded: Collection<Namespace> = emptySet()
    ): Collection<Membership> {
        val excludedSelf = buildSet { addAll(excluded); add(this@Namespace) }
        return (ownedMembership.filter { visibility == null || it.visibility == visibility }
            + ownedImport.filter { visibility == null || it.visibility == visibility }
            .flatMap { it.importedMemberships(excludedSelf) })
    }

    /**
     * Return the names of the given element as it is known in this Namespace.
     * 
     * namesOf(element : Element) : String [0..*]
     * body: let elementMemberships : Sequence(Membership) =
     *     memberships->select(memberElement = element) in
     * memberships.memberShortName->
     *     union(memberships.memberName)->
     *     asSet()
     */
    fun namesOf(element: Element): Set<String> {
        val elementMemberships = membership.filter { it.memberElement == element }
        return elementMemberships.map(Membership::memberShortName)
            .plus(elementMemberships.map(Membership::memberName))
            .filterNotNull()
            .toSet()
    }

    /**
     * Return a string with valid KerML syntax representing the qualification part of a given qualifiedName, that is, a
     * qualified name with all the segment names of the given name except the last. If the given qualifiedName has only
     * one segment, then return null.
     * 
     * qualificationOf(qualifiedName : String) : String [0..1]
     * body: No OCL
     */
    fun qualificationOf(qualifiedName: String): String?

    /**
     * Resolve the given qualified name to the named Membership (if any), starting with this Namespace as the local
     * scope. The qualified name string must conform to the concrete syntax of the KerML textual notation. According to
     * the KerML name resolution rules every qualified name will resolve to either a single Membership, or to none.
     * 
     * resolve(qualifiedName : String) : Membership [0..1]
     * body: let qualification : String = qualificationOf(qualifiedName) in
     * let name : String = unqualifiedNameOf(qualifiedName) in
     * if qualification = null then resolveLocal(name)
     * else if qualification = '$' then resolveGlobal(name)
     * else
     *     let namespaceMembership : Membership = resolve(qualification) in
     *     if namespaceMembership = null or
     *         not namespaceMembership.memberElement.oclIsKindOf(Namespace)
     *     then null
     *     else
     *         namespaceMembership.memberElement.oclAsType(Namespace).
     *         resolveVisible(name)
     *     endif
     * endif endif
     */
    fun resolve(qualifiedName: String): Membership? {
        val name = unqualifiedNameOf(qualifiedName)
        return when (val qualification = qualificationOf(qualifiedName)) {
            null -> resolveLocal(name)
            "$" -> resolveGlobal(name)
            else -> (resolve(qualification)?.memberElement as? Namespace)?.resolveVisible(name)
        }
    }

    /**
     * Resolve the given qualified name to the named Membership (if any) in the effective global Namespace that is the
     * outermost naming scope. The qualified name string must conform to the concrete syntax of the KerML textual
     * notation.
     * 
     * resolveGlobal(qualifiedName : String) : Membership [0..1]
     * body: No OCL
     */
    fun resolveGlobal(qualifiedName: String): Membership?

    /**
     * Resolve a simple name starting with this Namespace as the local scope, and continuing with containing outer scopes
     * as necessary. However, if this Namespace is a root Namespace, then the resolution is done directly in global scope.
     * 
     * resolveLocal(name : String) : Membership [0..1]
     * body: if owningNamespace = null then resolveGlobal(name)
     * else
     *     let memberships : Membership = membership->
     *         select(memberShortName = name or memberName = name) in
     *     if memberships->notEmpty() then memberships->first()
     *     else owningNamspace.resolveLocal(name)
     *     endif
     * endif
     */
    fun resolveLocal(name: String): Membership? = if (owningNamespace == null) {
        resolveGlobal(name)
    } else {
        membership.firstOrNull { it.memberShortName == name || it.memberName == name }
            ?: (owningNamespace as Namespace).resolveLocal(name)
    }

    /**
     * Resolve a simple name from the visible Memberships of this Namespace.
     * 
     * resolveVisible(name : String) : Membership [0..1]
     * body: let memberships : Sequence(Membership) =
     *     visibleMemberships(Set{}, false, false)->
     *     select(memberShortName = name or memberName = name) in
     * if memberships->isEmpty() then null
     * else memberships->first()
     * endif
     */
    fun resolveVisible(name: String): Membership? = visibleMemberships()
        .firstOrNull { it.memberShortName == name || it.memberName == name }

    /**
     * Return the simple name that is the last segment name of the given qualifiedName. If this segment name has the
     * form of a KerML unrestricted name, then "unescape" it by removing the surrounding single quotes and replacing all
     * escape sequences with the specified character.
     * 
     * `unqualifiedNameOf(qualifiedName : String) : String`
     * body: No OCL
     */
    fun unqualifiedNameOf(qualifiedName: String): String

    /**
     * Returns this visibility of mem relative to this Namespace. If mem is an importedMembership, this is the
     * visibility of its Import. Otherwise it is the visibility of the Membership itself
     * 
     * `visibilityOf(mem : Membership) : VisibilityKind`
     * ```ocl
     * body: if importedMembership->includes(mem) then
     *     ownedImport->
     *         select(importedMemberships(Set{})->includes(mem)).
     *         first().visibility
     * else if memberships->includes(mem) then
     *     mem.visibility
     * else
     *     VisibilityKind::private
     * endif
     * ```
     */
    fun visibilityOf(mem: Membership): VisibilityKind = when (mem) {
        in importedMembership -> ownedImport.first { mem in it.importedMemberships() }.visibility
        in membership -> mem.visibility
        else -> PRIVATE
    }

    /**
     * If includeAll = true, then return all the Memberships of this Namespace. Otherwise, return only the publicly
     * visible Memberships of this Namespace, including ownedMemberships that have a visibility of public and
     * Memberships imported with a visibility of public. If isRecursive = true, also recursively include all
     * visible Memberships of any public owned Namespaces, or, if IncludeAll = true, all Memberships of all
     * owned Namespaces. When computing imported Memberships, ignore this Namespace and any Namespaces in
     * the given excluded set.
     * 
     * visibleMemberships(excluded : Namespace [0..*], isRecursive : Boolean, includeAll : Boolean) : Membership [0..*]
     * body: let visibleMemberships : OrderedSet(Membership) =
     *     if includeAll then membershipsOfVisibility(null, excluded)
     *     else membershipsOfVisibility(VisibilityKind::public, excluded)
     *     endif in
     * if not isRecursive then visibleMemberships
     * else visibleMemberships->union(ownedMember->
     *     selectAsKind(Namespace).
     *     select(includeAll or owningMembership.visibility = VisibilityKind::public)->
     *     visibleMemberships(excluded->including(self), true, includeAll))
     * endif
     */
    fun visibleMemberships(
        excluded: Collection<Namespace> = emptySet(),
        isRecursive: Boolean = false,
        includeAll: Boolean = false,
    ): Collection<Membership> {
        val visibleMemberships = membershipsOfVisibility(if (!includeAll) PUBLIC else null, excluded)
        return if (!isRecursive) {
            visibleMemberships
        } else {
            val excludedSelf: Set<Namespace> = buildSet { addAll(excluded); add(this@Namespace) }
            (visibleMemberships
                + ownedMember.filterIsInstance<Namespace>()
                .filter { includeAll || it.owningMembership?.visibility == VisibilityKind.PUBLIC }
                .flatMap { it.visibleMemberships(excludedSelf, true, includeAll) })
        }
    }
    
    object Validation : Validator<Namespace> {
        override fun Namespace.validate() = validateNamespaceDistinguishibility() && Element.Validation.validate(this)

        /**
         * All memberships of a Namespace must be distinguishable from each other.
         * membership->forAll(m1 |
         * membership->forAll(m2 |
         * m1 <> m2 implies m1.isDistinguishableFrom(m2)))
         */
        fun Namespace.validateNamespaceDistinguishibility() = membership.all { m1 ->
            membership.all { m2 ->
                (m1 != m2) implies m1.isDistinguishableFrom(m2)
            }
        }
    }
}
