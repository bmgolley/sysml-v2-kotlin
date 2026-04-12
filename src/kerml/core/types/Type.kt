package sandbox.kerml.core.types

import sandbox.featurechains.kerml.core.features.redefinedFeature
import sandbox.featurechains.kerml.core.features.redefinition
import sandbox.featurechains.kerml.core.types.differencingType
import sandbox.featurechains.kerml.core.types.directionOfExcluding
import sandbox.featurechains.kerml.core.types.general
import sandbox.featurechains.kerml.core.types.multiplicity
import sandbox.featurechains.kerml.core.types.nonPrivateMemberships
import sandbox.featurechains.kerml.core.types.ownedMemberFeature
import sandbox.kerml.core.classifiers.Classifier
import sandbox.kerml.core.features.Feature
import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.namespaces.Membership
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.Import

/**
 * A `Type` is a [Namespace] that is the most general kind of [Element] supporting the semantics of classification. A
 * `Type` may be a [Classifier] or a [Feature], defining conditions on what is classified by the `Type` (see also the
 * description of [isSufficient]).
 */
@Suppress("unused")
interface Type : Namespace {
    /**
     * The interpretations of a `Type` with `differencingTypes` are asserted to be those of the first of those `Types`,
     * but not including those of the remaining `Types`. For example, a [Classifier] might be the difference of a
     * [Classifier] for people and another for people of a particular nationality, leaving people who are not of that
     * nationality. Similarly, a feature of people might be the difference between a feature for their children and a
     * [Classifier] for people of a particular sex, identifying their children not of that sex (because the
     * interpretations of the children [Feature] that identify those of that sex are also interpretations of the
     * [Classifier] for that sex).
     * 
     * ```ocl
     * /differencingType : Type [0..*] {ordered}
     * ```
     *
     * - deriveTypeDifferencingType
     *
     * The [differencingTypes][differencingType] of a `Type` are the [differencingTypes][differencingType] of its
     * [ownedDifferencings][ownedDifferencing], in the same order.
     * ```ocl
     * differencingType = ownedDifferencing.differencingType
     * ```
     */
    val differencingType: List<Type>
        get() = ownedDifferencing.differencingType

    /**
     * The features of this `Type` that have a non-null direction.
     * 
     * ```ocl
     * /directedFeature : Feature [0..*] {subsets feature, ordered}
     * ```
     *
     * - deriveTypeDirectedFeature
     *
     * The `directedFeatures` of a `Type` are those features for which the direction is non-null.
     *
     * ```ocl
     * directedFeature = feature->select(f | directionOf(f) <> null)
     * ```
     */
    val directedFeature: List<Feature>
        get() = feature.filter { directionOf(it) != null }

    /**
     * All features of this `Type` with `isEnd = true`.
     *
     * ```ocl
     * /endFeature : Feature [0..*] {subsets feature, ordered}
     * ```
     *
     * - deriveTypeEndFeature
     * The [endFeatures][endFeature] of a Type are all its features for which [isEnd][Feature.isEnd]` = true`.
     *
     * ```ocl
     * endFeature = feature->select(isEnd)
     * ```
     */
    val endFeature: List<Feature>
        get() = feature.filter(Feature::isEnd)

    /**
     * The [ownedMemberFeatures][FeatureMembership.ownedMemberFeature] of the [featureMemberships][featureMembership] of
     * this `Type`.
     * 
     * ```ocl
     * /feature : Feature [0..*] {subsets member, ordered}
     * ```
     *
     * - deriveTypeFeature
     *
     * The features of a `Type` are the ownedMemberFeatures of its [featureMemberships][featureMembership]
     *
     * ```ocl
     * feature = featureMembership.ownedMemberFeature
     * ```
     */
    val feature: List<Feature>
        get() = featureMembership.ownedMemberFeature

    /**
     * The FeatureMemberships for features of this Type, which include all ownedFeatureMemberships and
     * those inheritedMemberships that are FeatureMemberships (but does not include any
     * importedMemberships).
     * 
     * ```ocl
     * /featureMembership : FeatureMembership [0..*] {ordered}
     * ```
     */
    val featureMembership: List<FeatureMembership>

    /**
     * All the memberFeatures of the inheritedMemberships of this Type that are FeatureMemberships.
     * 
     * `/inheritedFeature : Feature [0..*] {subsets feature, ordered}`
     */
    val inheritedFeature: List<Feature>

    /**
     * All Memberships inherited by this Type via Specialization or Conjugation. These are included in the
     * derived union for the memberships of the Type.
     * 
     * `/inheritedMembership : Membership [0..*] {subsets membership, ordered}`
     */
    val inheritedMembership: List<Membership>

    /**
     * All features related to this Type by FeatureMemberships that have direction in or inout.
     * 
     * `/input : Feature [0..*] {subsets directedFeature, ordered}`
     */
    val input: List<Feature>

    /**
     * The interpretations of a Type with intersectingTypes are asserted to be those in common among the
     * intersectingTypes, which are the Types derived from the intersectingType of the ownedIntersectings
     * of this Type. For example, a Classifier might be an intersection of Classifiers for people of a particular sex
     * and of a particular nationality. Similarly, a feature for people's children of a particular sex might be the intersection
     * of a Feature for their children and a Classifier for people of that sex (because the interpretations of the children
     * Feature that identify those of that sex are also interpretations of the Classifier for that sex).
     * 
     * `/intersectingType : Type [0..*] {ordered}`
     */
    val intersectingType: List<Type>

    /**
     * Indicates whether instances of this Type must also be instances of at least one of its specialized Types.
     * 
     * `isAbstract : Boolean`
     */
    var isAbstract: Boolean

    /**
     * Indicates whether this `Type` has an [ownedConjugator].
     * 
     * ```ocl
     * /isConjugated : Boolean
     * ```
     */
    val isConjugated: Boolean

    /**
     * Whether all things that meet the classification conditions of this Type must be classified by the Type.
     * 
     * (A Type gives conditions that must be met by whatever it classifies, but when isSufficient is false, things may
     * meet those conditions but still not be classified by the Type. For example, a Type Car that is not sufficient could
     * require everything it classifies to have four wheels, but not all four wheeled things would classify as cars. However,
     * if the Type Car were sufficient, it would classify all four-wheeled things.)
     * 
     * `isSufficient : Boolean`
     */
    var isSufficient: Boolean

    /**
     * An [ownedMember] of this Type that is a [Multiplicity], which constraints the cardinality of the Type. If there is no
     * such [ownedMember], then the cardinality of this Type is constrained by all the [Multiplicity] constraints
     * applicable to any direct supertypes.
     * 
     * `/multiplicity : Multiplicity [0..1] {subsets ownedMember}`
     */
    val multiplicity: Multiplicity?

    /**
     * All features related to this Type by [FeatureMemberships][FeatureMembership] that have direction
     * [out][FeatureDirectionKind.OUT] or [inout][FeatureDirectionKind.INOUT].
     * 
     * `/output : Feature [0..*] {subsets directedFeature, ordered}`
     */
    val output: List<Feature>

    /**
     * A [Conjugation] owned by this Type for which the Type is the [originalType][Conjugation.originalType].
     * 
     * `/ownedConjugator : Conjugation [0..1] {subsets ownedRelationship, conjugator}`
     */
    val ownedConjugator: Conjugation?

    /**
     * The [ownedRelationships][ownedRelationship] of this Type that are [Differencings][Differencing], having this
     * Type as their [typeDifferenced][Differencing.typeDifferenced].
     * 
     * `/ownedDifferencing : Differencing [0..*] {subsets sourceRelationship, ownedRelationship, ordered}`
     */
    val ownedDifferencing: List<Differencing>

    /**
     * The ownedRelationships of this Type that are Disjoinings, for which the Type is the typeDisjoined Type.
     * 
     * `/ownedDisjoining : Disjoining [0..*] {subsets ownedRelationship, disjoiningTypeDisjoining}`
     */
    val ownedDisjoining: List<Disjoining>

    /**
     * All endFeatures of this Type that are ownedFeatures.
     * 
     * `/ownedEndFeature : Feature [0..*] {subsets endFeature, ownedFeature, ordered}`
     */
    val ownedEndFeature: List<Feature>

    /**
     * The ownedMemberFeatures of the ownedFeatureMemberships of this Type.
     * 
     * `/ownedFeature : Feature [0..*] {subsets ownedMember, ordered}`
     */
    val ownedFeature: List<Feature>

    /**
     * The ownedMemberships of this Type that are FeatureMemberships, for which the Type is the owningType.
     * Each such FeatureMembership identifies an ownedFeature of the Type.
     * 
     * `/ownedFeatureMembership : FeatureMembership [0..*] {subsets ownedMembership, featureMembership, ordered}`
     */
    val ownedFeatureMembership: List<FeatureMembership>

    /**
     * The ownedRelationships of this Type that are Intersectings, have the Type as their typeIntersected.
     * 
     * `/ownedIntersecting : Intersecting [0..*] {subsets ownedRelationship, sourceRelationship, ordered}`
     */
    val ownedIntersecting: List<Intersecting>

    /**
     * The ownedRelationships of this Type that are Specializations, for which the Type is the specific Type.
     * 
     * `/ownedSpecialization : Specialization [0..*] {subsets specialization, ownedRelationship, ordered}`
     */
    val ownedSpecialization: List<Specialization>

    /**
     * The ownedRelationships of this Type that are Unionings, having the Type as their typeUnioned.
     * 
     * `/ownedUnioning : Unioning [0..*] {subsets ownedRelationship, sourceRelationship, ordered}`
     */
    val ownedUnioning: List<Unioning>

    /**
     * The interpretations of a Type with unioningTypes are asserted to be the same as those of all the unioningTypes
     * together, which are the Types derived from the unioningType of the ownedUnionings of this Type. For
     * example, a Classifier for people might be the union of Classifiers for all the sexes. Similarly, a feature for
     * people's children might be the union of features dividing them in the same ways as people in general.
     * 
     * `/unioningType : Type [0..*] {ordered}`
     */
    val unioningType: List<Type>

    /**
     * If the memberElement of the given membership is a Feature, then return all Features directly or indirectly
     * redefined by the memberElement.
     *
     * ```ocl
     * allRedefinedFeaturesOf(membership : Membership) : Feature [0..*]
     * body: if not membership.memberElement.oclIsType(Feature) then Set{}
     * else membership.memberElement.oclAsType(Feature).allRedefinedFeatures()
     * endif
     * ```
     */
    fun allRedefinedFeaturesOf(membership: Membership): Set<Feature> =
        (membership.memberElement as? Feature)?.allRedefinedFeatures().orEmpty()

    /**
     * Return this Type and all Types that are directly or transitively supertypes of this Type (as determined by the
     * supertypes operation with excludeImplied = false).
     * 
     * `allSupertypes() : Type [0..*]`
     * ```ocl
     * body: OrderedSet{self}->closure(supertypes(false))
     * ```
     */
    fun allSupertypes(): Set<Type> = supertypes(false).toSet()

    /**
     * If the given feature is a feature of this `Type`, then return its direction relative to this `Type`, taking
     * conjugation into account.
     *
     * ```ocl
     * directionOf(feature : Feature) : FeatureDirectionKind [0..1]
     * body: directionOfExcluding(f, Set{})
     * ```
     */
    fun directionOf(feature: Feature): FeatureDirectionKind? = directionOfExcluding(feature, emptySet())

    /**
     * Return the direction of the given feature relative to this `Type`, excluding a given set of `Types` from the
     * search of supertypes of this `Type`.
     *
     * ```ocl
     * directionOfExcluding(feature : Feature, excluded : Type [0..*]) : FeatureDirectionKind [0..1]
     * body: let excludedSelf : Set(Type) = excluded->including(self) in
     * if feature.owningType = self then feature.direction
     * else
     *     let directions : Sequence(FeatureDirectionKind) =
     *         supertypes(false)->excluding(excludedSelf).
     *         directionOfExcluding(feature, excludedSelf)->
     *         select(d | d <> null) in
     *     if directions->isEmpty() then null
     * else
     *     let direction : FeatureDirectionKind = directions->first() in
     *     if not isConjugated then direction
     *     else if direction = FeatureDirectionKind::_'in' then FeatureDirectionKind::out
     *     else if direction = FeatureDirectionKind::out then FeatureDirectionKind::_'in'
     *     else direction
     *     endif endif endif endif
     * endif
     * ```
     */
    fun directionOfExcluding(feature: Feature, excluded: Collection<Type>): FeatureDirectionKind? {
        val excludedSelf = buildSet { addAll(excluded); add(this@Type) }
        val directions = supertypes(false).minus(excludedSelf).directionOfExcluding(feature, excludedSelf)
        return when (val direction = directions.firstOrNull()) {
            else if isConjugated -> direction
            IN -> OUT
            OUT -> IN
            else -> direction
        }

    }

    /**
     * If this `Type` is conjugated, then return just the [originalType][Conjugation.originalType] of the [Conjugation].
     * Otherwise, return the general `Types` from all [ownedSpecializations][ownedSpecialization] of this type, if
     * [excludeImplied]` = false`, or all nonimplied [ownedSpecializations][ownedSpecialization], if [excludeImplied]` =
     * true`.
     *
     * ```ocl
     * supertypes(excludeImplied : Boolean) : Type [0..*]
     * body: if isConjugated then Sequence{conjugator.originalType}
     * else if not excludeImplied then ownedSpecialization.general
     * else ownedSpecialization->reject(isImplied).general
     * endif
     * endif
     * ```
     */
    fun supertypes(excludeImplied: Boolean): Collection<Type> = when {
        isConjugated -> setOf() // conjugator.originalType
        !excludeImplied -> ownedSpecialization.general
        else -> ownedSpecialization.filterNot(Specialization::isImplied).general
    }

    /**
     *
     * Return all the non-private [Memberships][Membership] of all the supertypes of this `Type`, excluding any supertypes that are this
     * `Type` or are in the given set of [excludedTypes]. If [excludeImplied]` = true`, then also transitively exclude any
     * supertypes from implied [Specializations][Specialization].
     *
     * ```ocl
     * inheritableMemberships(excludedNamespaces : Namespace [0..*], excludedTypes : Type [0..*], excludeImplied :
     *      Boolean) : Membership [0..*]
     * body: let excludingSelf : Set(Type) = excludedType->including(self) in
     * supertypes(excludeImplied)->reject(t | excludingSelf->includes(t)).
     *     nonPrivateMemberships(excludedNamespaces, excludingSelf, excludeImplied)
     * ```
     */
    fun inheritableMemberships(
        excludedNamespaces: Collection<Namespace> = emptySet(),
        excludedTypes: Collection<Type> = emptySet(),
        excludeImplied: Boolean = false
    ): Collection<Membership> {
        val excludedSelf = buildSet { addAll(excludedTypes); add(this@Type) }
        return supertypes(excludeImplied).filter(excludedSelf::contains)
            .nonPrivateMemberships(excludedNamespaces, excludedTypes, excludeImplied)
    }

    /**
     * Return the [Memberships][Membership] inheritable from supertypes of this `Type` with redefined
     * [Features][Feature] removed. When computing inheritable [Memberships][Membership], exclude [Imports][Import] of
     * [excludedNamespaces], [Specializations][Specialization] of [excludedTypes], and, if [excludeImplied]` = true`,
     * all implied [Specializations][Specialization].
     *
     * ```ocl
     * inheritedMemberships(excludedNamespaces : Namespace [0..*], excludedTypes : Type [0..*], excludeImplied :
     *      Boolean) : Membership [0..*]
     * body: removeRedefinedFeatures(
     *     inheritableMemberships(excludedNamespaces, excludedTypes, excludeImplied))
     * ```
     */
    fun inheritedMemberships(
        excludedNamespaces: Collection<Namespace> = emptySet(),
        excludedTypes: Collection<Type> = emptySet(),
        excludeImplied: Boolean = false
    ): Collection<Membership> =
        removeRedefinedFeatures(inheritableMemberships(excludedNamespaces, excludedTypes, excludeImplied))

    /**
     * By default, this `Type` is compatible with an [otherType] if it directly or indirectly specializes the
     * [otherType].
     *
     * ```ocl
     * isCompatibleWith(otherType : Type)
     * body: specializes(otherType)
     * ```
     */
    infix fun isCompatibleWith(otherType: Type): Boolean = specializes(otherType)

    /**
     * Return the owned or inherited [Multiplicities][Multiplicity] for this `Type`.
     *
     * ```ocl
     * multiplicities() : Multiplicity [0..*]
     * body: if multiplicity <> null then OrderedSet{multiplicity}
     * else
     *     ownedSpecialization.general->closure(t |
     *         if t.multiplicity <> null then OrderedSet{}
     *         else ownedSpecialization.general
     *     )->select(multiplicity <> null).multiplicity->asOrderedSet()
     * endif
     * ```
     */
    fun multiplicities(): Collection<Multiplicity> = if (multiplicity != null) {
        setOf(multiplicity!!)
    } else {
        ownedSpecialization.general.flatMap { t ->
            if (t.multiplicity != null) emptySet() else t.ownedSpecialization.general.multiplicity.filterNotNull()
        }
    }

    /**
     * Return the public, protected and inherited [Memberships][Membership] of this `Type`. When computing imported
     * [Memberships][Membership], exclude the given set of [excludedNamespaces]. When computing inherited
     * [Memberships][Membership], exclude `Types` in the given set of [excludedTypes]. If [excludeImplied]` = true`,
     * then also exclude any supertypes from implied [Specializations][Specialization].
     *
     * ```ocl
     * nonPrivateMemberships(excludedNamespaces : Namespace [0..*], excludedTypes : Type [0..*], excludeImplied :
     * Boolean) : Membership [0..*]
     * body: let publicMemberships : OrderedSet(Membership) =
     *     membershipsOfVisibility(VisibilityKind::public, excludedNamespaces) in
     * let protectedMemberships : OrderedSet(Membership) =
     *     membershipsOfVisibility(VisibilityKind::protected, excludedNamespaces) in
     * let inheritedMemberships : OrderedSet(Membership) =
     *     inheritedMemberships(excludedNamespaces, excludedTypes, excludeImplied) in
     * publicMemberships->
     *     union(protectedMemberships)->
     *     union(inheritedMemberships)
     * ```
     */
    fun nonPrivateMemberships(
        excludedNamespaces: Collection<Namespace> = emptySet(),
        excludedTypes: Collection<Type> = emptySet(),
        excludeImplied: Boolean = false
    ): Collection<Membership> = buildSet {
        addAll(membershipsOfVisibility(PUBLIC, excludedNamespaces))
        addAll(membershipsOfVisibility(PROTECTED, excludedNamespaces))
        addAll(inheritedMemberships(excludedNamespaces, excludedTypes, excludeImplied))
    }

    /**
     * Return a subset of [memberships], removing those [Memberships][Membership] whose
     * [memberElements][Membership.memberElement] are [Features][Feature] and for which either of the following two
     * conditions holds:
     *
     * 1. The memberElement of the Membership is included in redefined Features of another Membership in [memberships].
     * 2. One of the redefined Features of the Membership is a directly redefinedFeature of an ownedFeature of this
     * `Type`.
     *
     * For this purpose, the redefined [Features][Feature] of a [Membership] whose
     * [memberElements][Membership.memberElement] is a [Feature] includes the [memberElement][Membership.memberElement]
     * and all [Features][Feature] directly or indirectly redefined by the [memberElement][Membership.memberElement].
     *
     * ```ocl
     * removeRedefinedFeatures(memberships : Membership [0..*]) : Membership [0..*]
     * body: let reducedMemberships : Sequence(Membership) =
     *     memberships->reject(mem1 |
     *         memberships->excluding(mem1)->
     *         exists(mem2 | allRedefinedFeaturesOf(mem2)->
     *             includes(mem1.memberElement))) in
     * let redefinedFeatures : Set(Feature) =
     *     ownedFeature.redefinition.redefinedFeature->asSet() in
     * reducedMemberships->reject(mem | allRedefinedFeaturesOf(mem)->
     *     exists(feature | redefinedFeatures->includes(feature)))
     * ```
     */
    fun removeRedefinedFeatures(memberships: Collection<Membership>): Collection<Membership> {
        val reducedMemberships = memberships.toSet().let { memberships ->
            memberships.filter { mem1 ->
                memberships.minus(mem1).any { mem2 -> mem1.memberElement in allRedefinedFeaturesOf(mem2) }
            }
        }
        val redefinedFeatures = ownedFeature.redefinition.redefinedFeature
        return reducedMemberships.filter { allRedefinedFeaturesOf(it).any(redefinedFeatures::contains) }
    }

    /**
     * Check whether this Type is a direct or indirect specialization of the given supertype.
     *
     * ```ocl
     * specializes(supertype : Type) : Boolean
     *
     * body: if isConjugated then
     *     ownedConjugator.originalType.specializes(supertype)
     * else
     *     allSupertypes()->includes(supertype)
     * endif
     * ```
     */
    fun specializes(supertype: Type): Boolean = if (isConjugated) {
        ownedConjugator!!.originalType.specializes(supertype)
    } else {
        supertype in allSupertypes()
    }

    /**
     * Check whether this `Type` is a direct or indirect specialization of the named library `Type`. [libraryTypeName]
     * must conform to the syntax of a KerML qualified name and must resolve to a `Type` in global scope.
     *
     * ```ocl
     * specializesFromLibrary(libraryTypeName : String) : Boolean
     *
     * body: let mem : Membership = resolveGlobal(libraryTypeName) in
     * mem <> null and mem.memberElement.oclIsKindOf(Type) and
     * specializes(mem.memberElement.oclAsType(Type))
     */
    fun specializesFromLibrary(libraryTypeName: String): Boolean {
        val memberElement = resolveGlobal(libraryTypeName)?.memberElement
        return memberElement is Type && specializes(memberElement)
    }

    /**
     * The visible [Memberships][Membership] of a `Type` include [inheritedMemberships].
     *
     * ```ocl
     * visibleMemberships(excluded : Namespace [0..*], isRecursive : Boolean, includeAll : Boolean) : Membership [0..*]
     * {redefines visibleMemberships}
     *
     * body: let visibleMemberships : OrderedSet(Membership) =
     *     self.oclAsType(Namespace).
     *         visibleMemberships(excluded, isRecursive, includeAll) in
     * let visibleInheritedMemberships : OrderedSet(Membership) =
     *     inheritedMemberships(excluded->including(self), Set{}, isRecursive)->
     *         select(includeAll or visibility = VisibilityKind::public) in
     * visibleMemberships->union(visibleInheritedMemberships)
     * ```
     */
    override fun visibleMemberships(
        excluded: Collection<Namespace>,
        isRecursive: Boolean,
        includeAll: Boolean
    ): Collection<Membership> = buildSet {
        addAll(super.visibleMemberships(excluded, isRecursive, includeAll))
        addAll(
            inheritedMemberships(excluded.plus(this@Type), excludeImplied = isRecursive)
                .filter { includeAll || it.visibility == PUBLIC }
        )
    }
}

/*

Constraints

checkTypeSpecialization
A Type must directly or indirectly specialize Base::Anything from the Kernel Semantic Library.
specializesFromLibrary('Base::Anything')



deriveTypeFeatureMembership
The featureMemberships of a Type is the union of the ownedFeatureMemberships and those
inheritedMemberships that are FeatureMemberships.
featureMembership = ownedFeatureMembership->union(
inheritedMembership->selectByKind(FeatureMembership))

deriveTypeInheritedFeature
The inheritedFeatures of this Type are the memberFeatures of the inheritedMemberships that are
FeatureMemberships.
inheritedFeature = inheritedMemberships->
selectByKind(FeatureMembership).memberFeature

deriveTypeInheritedMembership
The inheritedMemberships of a Type are determined by the inheritedMemberships()
operation.
inheritedMembership = inheritedMemberships(Set{}, Set{}, false)

deriveTypeInput
The inputs of a Type are those of its features that have a direction of in or inout relative to the Type, taking
conjugation into account.
input = feature->select(f |
let direction: FeatureDirectionKind = directionOf(f) in
direction = FeatureDirectionKind::_'in' or
direction = FeatureDirectionKind::inout)

deriveTypeIntersectingType
The intersectingTypes of a Type are the intersectingTypes of its ownedIntersectings.
intersectingType = ownedIntersecting.intersectingType

deriveTypeMultiplicity
If a Type has an owned Multiplicity, then that is its multiplicity. Otherwise, if the Type has an
ownedSpecialization, then its multiplicity is the multiplicity of the general Type of that
Specialization.
multiplicity =
let ownedMultiplicities: Sequence(Multiplicity) =
ownedMember->selectByKind(Multiplicity) in
if ownedMultiplicities->isEmpty() then null
else ownedMultiplicities->first()
endif

deriveTypeOutput
The outputs of a Type are those of its features that have a direction of out or inout relative to the Type, taking
conjugation into account.
output = feature->select(f |
let direction: FeatureDirectionKind = directionOf(f) in
direction = FeatureDirectionKind::out or
direction = FeatureDirectionKind::inout)

deriveTypeOwnedConjugator
The ownedConjugator of a Type is the its single ownedRelationship that is a Conjugation.
ownedConjugator =
let ownedConjugators: Sequence(Conjugator) =
ownedRelationship->selectByKind(Conjugation) in
if ownedConjugators->isEmpty() then null
else ownedConjugators->at(1) endif

deriveTypeOwnedDifferencing
The ownedDifferencings of a Type are its ownedRelationships that are Differencings.
ownedDifferencing =
ownedRelationship->selectByKind(Differencing)

deriveTypeOwnedDisjoining
The ownedDisjoinings of a Type are the ownedRelationships that are Disjoinings.
ownedDisjoining =
ownedRelationship->selectByKind(Disjoining)

deriveTypeOwnedEndFeature
The ownedEndFeatures of a Type are all its ownedFeatures for which isEnd = true.
ownedEndFeature = ownedFeature->select(isEnd)

deriveTypeOwnedFeature
The ownedFeatures of a Type are the ownedMemberFeatures of its ownedFeatureMemberships
ownedFeature = ownedFeatureMembership.ownedMemberFeature

deriveTypeOwnedFeatureMembership
The ownedFeatureMemberships of a Type are its ownedMemberships that are FeatureMemberships.
ownedFeatureMembership = ownedRelationship->selectByKind(FeatureMembership)

deriveTypeOwnedIntersecting
The ownedIntersectings of a Type are the ownedRelationships that are Intersectings.
ownedRelationship->selectByKind(Intersecting)

deriveTypeOwnedSpecialization
The ownedSpecializations of a Type are the ownedRelationships that are Specializations whose
special Type is the owning Type.
ownedSpecialization = ownedRelationship->selectByKind(Specialization)->
select(s | s.special = self)

deriveTypeOwnedUnioning
The ownedUnionings of a Type are the ownedRelationships that are Unionings.
ownedUnioning =
ownedRelationship->selectByKind(Unioning)

deriveTypeUnioningType
The unioningTypes of a Type are the unioningTypes of its ownedUnionings.
unioningType = ownedUnioning.unioningType

validateTypeAtMostOneConjugator
A Type must have at most one owned Conjugation Relationship.
ownedRelationship->selectByKind(Conjugation)->size() <= 1
validateTypeDifferencingTypesNotSelf
A Type cannot be one of its own differencingTypes.
differencingType->excludes(self)
validateTypeIntersectingTypesNotSelf
A Type cannot be one of its own intersectingTypes.
intersectingType->excludes(self)
validateTypeOwnedDifferencingNotOne
A Type must not have exactly one ownedDifferencing.
ownedDifferencing->size() <> 1
validateTypeOwnedIntersectingNotOne
A Type must not have exactly one ownedIntersecting.
ownedIntersecting->size() <> 1
validateTypeOwnedMultiplicity
A Type may have at most one ownedMember that is a Multiplicity.
ownedMember->selectByKind(Multiplicity)->size() <= 1
validateTypeOwnedUnioningNotOne
A Type must not have exactly one ownedUnioning.
ownedUnioning->size() <> 1
validateTypeUnioningTypesNotSelf
A Type cannot be one of its own unioningTypes.
unioningType->excludes(self)
*/
