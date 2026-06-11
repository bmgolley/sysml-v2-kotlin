@file:Suppress("unused")

package sandbox.kerml.core

import sandbox.featurechains.kerml.core.chainingFeature
import sandbox.featurechains.kerml.core.featuringType
import sandbox.featurechains.kerml.core.redefinedFeature

/**
 * A Feature is a Type that classifies relations between multiple things (in the universe). The domain of the relation
 * is the intersection of the featuringTypes of the Feature. (The domain of a Feature with no featuringTyps
 * is implicitly the most general Type Base::Anything from the Kernel Semantic Library.) The co-domain of the
 * relation is the intersection of the types of the Feature.
 * In the simplest cases, the featuringTypes and types are Classifiers and the Feature relates two things, one
 * from the domain and one from the range. Examples include cars paired with wheels, people paired with other
 * people, and cars paired with numbers representing the car length.
 * Since Features are Types, their featuringTypes and types can be Features. In this case, the Feature
 * effectively classifies relations between relations, which can be interpreted as the sequence of things related by the
 * domain Feature concatenated with the sequence of things related by the co-domain Feature.
 * The values of a Feature for a given instance of its domain are all the instances of its co-domain that are related to
 * that domain instance by the Feature. The values of a Feature with chainingFeatures are the same as values
 * of the last Feature in the chain, which can be found by starting with values of the first Feature, then using those
 * values as domain instances to obtain valus of the second Feature, and so on, to values of the last Feature.
 */
interface Feature : Type {
    /**
     * The Feature that are chained together to determine the values of this Feature, derived from the
     * chainingFeatures of the ownedFeatureChainings of this Feature, in the same order. The values of a
     * Feature with chainingFeatures are the same as values of the last Feature in the chain, which can be found by
     * starting with the values of the first Feature (for each instance of the domain of the original Feature), then using
     * each of those as domain instances to find the values of the second Feature in chainingFeatures, and so on, to
     * values of the last Feature.
     *
     * ```ocl
     * /chainingFeature : Feature [0..*] {ordered, nonunique}
     * ```
     */
    val chainingFeature: List<Feature>
        get() = ownedFeatureChaining.chainingFeature

    /**
     * The second chainingFeature of the crossedFeature of the ownedCrossSubsetting of this Feature, if it
     * has one. Semantically, the values of the crossFeature of an end Feature must include all values of the end
     * Feature obtained when navigating from values of the other end Features of the same owningType.
     * 
     * ```ocl
     * /crossFeature : Feature [0..1]
     * ```
     */
    val crossFeature: Feature?
        get() = ownedCrossSubsetting?.crossedFeature?.chainingFeature?.getOrNull(2)

    /**
     * Indicates how values of this Feature are determined or used (as specified for the FeatureDirectionKind).
     * 
     * ```ocl
     * direction : FeatureDirectionKind [0..1]
     * ```
     */
    var direction: FeatureDirectionKind?

    /**
     * The Type that is related to this Feature by an EndFeatureMembership in which the Feature is an
     * ownedMemberFeature.
     * 
     * ```ocl
     * /endOwningType : Type [0..1] {subsets typeWithEndFeature, owningType}
     * ```
     */
    val endOwningType: Type?

    // override val owningType get() = endOwningType

    /**
     * The last of the chainingFeatures of this Feature, if it has any. Otherwise, this Feature itself.
     * 
     * ```ocl
     * /featureTarget : Feature
     * ```
     */
    val featureTarget: Feature
        get() = chainingFeature.lastOrNull() ?: this

    /**
     * Types that feature this Feature, such that any instance in the domain of the Feature must be classified by all of
     * these Types, including at least all the featuringTypes of its typeFeaturings. If the Feature is chained, then
     * the featuringTypes of the first Feature in the chain are also featuringTypes of the chained Feature.
     * 
     * ```ocl
     * /featuringType : Type [0..*] {ordered}
     * ```
     */
    val featuringType: List<Type>
        get() = typeFeaturing.featuringType.let { featuringTypes ->
            if (chainingFeature.isNotEmpty()) {
                featuringTypes
            } else {
                buildSet {
                    addAll(featuringTypes)
                    addAll(chainingFeature.first().featuringType)
                }.toList()
            }
        }


    /**
     * Whether the Feature is a composite feature of its featuringType. If so, the values of the Feature cannot
     * exist after its featuring instance no longer does and cannot be values of another composite feature that is not on the
     * same featuring instance.Tap on a clip to paste it in the text box.Tap on a clip to paste it in the text box.
     * 
     * ```ocl
     * isComposite : Boolean
     * ```
     */
    val isComposite: Boolean

    /**
     * If isVariable is true, then whether the value of this Feature nevertheless does not change over all snapshots of
     * its owningType.
     * 
     * ```ocl
     * isConstant : Boolean
     * ```
     */
    var isConstant: Boolean

    /**
     * Whether the values of this Feature can always be computed from the values of other Features.
     * 
     * ```ocl
     * isDerived : Boolean
     * ```
     */
    var isDerived: Boolean

    /**
     * Whether or not this Feature is an end Feature. An end Feature always has multiplicity 1, mapping each of its
     * domain instances to a single co-domain instance. However, it may have a crossFeature, in which case values of
     * the crossFeature must be the same as those found by navigation across instances of the owningType from values
     * of other end Features to values of this Feature. If the owningType has n end Features, then the multiplicity,
     * ordering, and uniqueness declared for the crossFeature of any one of these end Features constrains the
     * cardinality, ordering, and uniqueness of the collection of values of that Feature reached by navigation when the
     * values of the other n-1 end Features are held fixed.
     * 
     * ```ocl
     * isEnd : Boolean
     * ```
     */
    var isEnd: Boolean

    /**
     * Whether an order exists for the values of this Feature or not.
     * 
     * ```ocl
     * isOrdered : Boolean
     * ```
     */
    var isOrdered: Boolean

    /**
     * Whether the values of this Feature are contained in the space and time of instances of the domain of the Feature
     * and represent the same thing as those instances.
     * 
     * ```ocl
     * isPortion : Boolean
     * ```
     */
    var isPortion: Boolean

    /**
     * Whether or not values for this Feature must have no duplicates or not.
     * 
     * ```ocl
     * isUnique : Boolean
     * ```
     */
    var isUnique: Boolean

    /**
     * Whether the value of this Feature might vary over time. That is, whether the Feature may have a different value
     * for each snapshot of an owningType that is an Occurrence.
     * 
     * ```ocl
     * isVariable : Boolean
     * ```
     */
    var isVariable: Boolean

    /**
     * The one ownedSubsetting of this Feature, if any, that is a CrossSubsetting, for which the Feature
     * is the crossingFeature.
     * 
     * ```ocl
     * /ownedCrossSubsetting : CrossSubsetting [0..1] {subsets ownedSubsetting}
     * ```
     */
    val ownedCrossSubsetting: CrossSubsetting?
        get() = ownedSubsetting.firstOrNull { it is CrossSubsetting } as CrossSubsetting

    /**
     * The ownedRelationships of this Feature that are FeatureChainings, for which the Feature will be the
     * featureChained.
     * 
     * ```ocl
     * /ownedFeatureChaining : FeatureChaining [0..*] {subsets sourceRelationship, ownedRelationship, ordered}
     * ```
     */
    val ownedFeatureChaining: List<FeatureChaining>
        get() = ownedRelationship.filterIsInstance<FeatureChaining>()

    /**
     * The ownedRelationships of this Feature that are FeatureInvertings and for which the Feature is the
     * featureInverted.
     * 
     * ```ocl
     * /ownedFeatureInverting : FeatureInverting [0..*] {subsets ownedRelationship, invertingFeatureInverting}
     * ```
     */
    val ownedFeatureInverting: List<FeatureInverting>
        get() = ownedRelationship.filterIsInstance<FeatureInverting>().filter { it.featureInverted === this }

    /**
     * The ownedSubsettings of this Feature that are Redefinitions, for which the Feature is the
     * redefiningFeature.
     * 
     * ```ocl
     * /ownedRedefinition : Redefinition [0..*] {subsets ownedSubsetting}
     * ```
     */
    val ownedRedefinition: List<Redefinition>
        get() = ownedSubsetting.filterIsInstance<Redefinition>()

    /**
     * The one ownedSubsetting of this Feature, if any, that is a ReferenceSubsetting, for which the Feature is
     * the referencingFeature.
     * 
     * ```ocl
     * /ownedReferenceSubsetting : ReferenceSubsetting [0..1] {subsets ownedSubsetting}
     * ```
     */
    val ownedReferenceSubsetting: ReferenceSubsetting?
        get() = ownedSubsetting.firstOrNull { it is ReferenceSubsetting } as ReferenceSubsetting

    /**
     * The ownedSpecializations of this Feature that are Subsettings, for which the Feature is the
     * subsettingFeature.
     * 
     * ```ocl
     * /ownedSubsetting : Subsetting [0..*] {subsets ownedSpecialization, subsetting}
     * ```
     */
    val ownedSubsetting: List<Subsetting>
        get() = ownedSpecialization.filterIsInstance<Subsetting>()

    /**
     * The ownedRelationships of this Feature that are TypeFeaturings and for which the Feature is the
     * featureOfType.
     * 
     * ```ocl
     * /ownedTypeFeaturing : TypeFeaturing [0..*] {subsets ownedRelationship, typeFeaturing, ordered}
     * ```
     */
    val ownedTypeFeaturing: List<TypeFeaturing>
        get() = ownedRelationship.filterIsInstance<TypeFeaturing>().filter { it.featureOfType === this }

    /**
     * The ownedSpecializations of this Feature that are FeatureTypings, for which the Feature is the
     * typedFeature.
     * 
     * ```ocl
     * /ownedTyping : FeatureTyping [0..*] {subsets ownedSpecialization, typing, ordered}
     * ```
     */
    val ownedTyping: List<FeatureTyping>
        get() = ownedRelationship.filterIsInstance<FeatureTyping>()

    /**
     * The FeatureMembership that owns this Feature as an ownedMemberFeature, determining its owningType.
     * 
     * ```ocl
     * /owningFeatureMembership : FeatureMembership [0..1] {subsets owningMembership}
     * ```
     */
    val owningFeatureMembership: FeatureMembership?

    /**
     * The Type that is the owningType of the owningFeatureMembership of this Feature.
     * 
     * ```ocl
     * /owningType : Type [0..1] {subsets typeWithFeature, owningNamespace, featuringType}
     * ```
     */
    val owningType: Type?

    /**
     * Types that restrict the values of this Feature, such that the values must be instances of all the types. The types of
     * a Feature are derived from its typings and the types of its subsettings. If the Feature is chained, then the
     * types of the last Feature in the chain are also types of the chained Feature.
     * 
     * ```ocl
     * /type : Type [0..*] {ordered}
     * ```
     */
    val type: List<Type>

    /**
     * Return this Feature and all the Features that are directly or indirectly Redefined by this Feature.
     *
     * ```ocl
     * allRedefinedFeatures() : Feature [0..*]
     * body: ownedRedefinition.redefinedFeature->
     *     closure(ownedRedefinition.redefinedFeature)->
     *     asOrderedSet()->prepend(self)
     */
    fun allRedefinedFeatures(): Set<Feature> = buildSet {
        addAll(ownedRedefinition.redefinedFeature.flatMap { it.ownedRedefinition.redefinedFeature })
        add(this@Feature)
    }

    fun asCartesianProduct(): List<Type>
    fun canAccess(feature: Feature): Boolean
    fun directionFor(type: Type): FeatureDirectionKind?
    override fun effectiveName(): String?
    override fun effectiveShortName(): String?
    fun isCartesianProduct(): Boolean
    override fun isCompatibleWith(otherType: Type): Boolean
    fun isFeaturedWithin(type: Type?): Boolean
    fun isFeaturingType(type: Type): Boolean
    fun isOwnedCrossFeature(): Boolean
    fun namingFeature(): Feature?
    fun ownedCrossFeature(): Feature?
    fun redefines(redefinedFeature: Feature): Boolean
    fun redefinesFromLibrary(libraryFeatureName: String): Boolean
    fun subsetsChain(first: Feature, second: Feature): Boolean
    fun typingFeatures(): List<Feature>

    val typeFeaturing: Collection<TypeFeaturing>
    val redefinition: Collection<Redefinition>
}

/*
# Operations
asCartesianProduct() : Type [0..*]
If isCartesianProduct is true, then return the list of Types whose Cartesian product can be represented by this
Feature. (If isCartesianProduct is not true, the operation will still return a valid value, it will just not
represent anything useful.)
body: featuringType->select(t | t.owner <> self)->
union(featuringType->select(t | t.owner = self)->
selectByKind(Feature).asCartesianProduct())->
union(type)

canAccess(feature : Feature) : Boolean
A Feature can access another feature if the other feature is featured within one of the direct or indirect
featuringTypes of this Feature.
body: let anythingType: Element =
subsettingFeature.resolveGlobal('Base::Anything').memberElement in
let allFeaturingTypes : Sequence(Type) =
featuringTypes->closure(t |
if not t.oclIsKindOf(Feature) then Sequence{}
else
let featuringTypes : OrderedSet(Type) = t.oclAsType(Feature).featuringType in
if featuringTypes->isEmpty() then Sequence{anythingType}
else featuringTypes
endif
endif) in
allFeaturingTypes->exists(t | feature.isFeaturedWithin(t))

directionFor(type : Type) : FeatureDirectionKind [0..1]
Return the directionOf this Feature relative to the given type.
body: type.directionOf(self)

effectiveName() : String [0..1] {redefines effectiveName}
If a Feature has no declaredName or declaredShortName , then its effective name is given by the effective
name of the Feature returned by the namingFeature() operation, if any.
body: if declaredShortName <> null or declaredName <> null then
declaredName
else
let namingFeature : Feature = namingFeature() in
if namingFeature = null then
null
else
namingFeature.effectiveName()
endif
endif

effectiveShortName() : String [0..1] {redefines effectiveShortName}
If a Feature has no declaredShortName or declaredName, then its effective shortName is given by the
effective shortName of the Feature returned by the namingFeature() operation, if any.
body: if declaredShortName <> null or declaredName <> null then
declaredShortName
else
let namingFeature : Feature = namingFeature() in
if namingFeature = null then
null
else
namingFeature.effectiveShortName()
endif
endif

isCartesianProduct() : Boolean
check whether this Feature can be used to represent a Cartesian product of Types.
body: type->size() = 1 and
featuringType.size() = 1 and
(featuringType.first().owner = self implies
featuringType.first().oclIsKindOf(Feature) and
featuringType.first().oclAsType(Feature).isCartesianProduct())

isCompatibleWith(otherType : Type) {redefines isCompatibleWith}
A Feature is compatible with an otherType if it either directly or indirectly specializes the otherType or if the
otherType is also a Feature and all of the following are true.
1. Neither this Feature or the otherType have any ownedFeatures.
2. This Feature directly or indirectly redefines a Feature that is also directly or indirectly redefined by the
otherType.
3. This Feature can access the otherType.
body: specializes(otherType) or
supertype.oclIsKindOf(Feature) and
ownedFeature->isEmpty() and
otherType.ownedFeature->isEmpty() and
ownedRedefinitions.allRedefinedFeatures()->exists(f |
otherType.oclAsType(Feature).allRedefinedFeatures()->includes(f)) and
canAccess(otherType.oclAsType(Feature))

isFeaturedWithin(type : Type [0..1]) : Boolean
Return if the featuringTypes of this Feature are compatible with the given type. If type is null, then check if
this Feature is explicitly or implicitly featured by Base::Anything. If this Feature has isVariable = true,
then also consider it to be featured within its owningType. If this Feature is a feature chain whose first
chainingFeature has isVariable = true, then also consider it to be featured within the owningType of its
first chainingFeature.
body: if type = null then
featuringType->forAll(f | f = resolveGlobal('Base::Anything').memberElement)
else
featuringType->forAll(f | type.isCompatibleWith(f)) or
isVariable and type.specializes(owningType) or
chainingFeature->notEmpty() and chainingFeature->first().isVariable and
type.specializes(chainingFeature->first().owningType)
endif

isFeaturingType(type : Type) : Boolean
Return whether the given type must be a featuringType of this Feature. If this Feature has isVariable =
false, then return true if the type is the owningType of the Feature. If isVariable = true, then return true
if the type is a Feature representing the snapshots of the owningType of this Feature.
body: owningType <> null and
if not isVariable then type = owningType
else if owningType = resolveGlobal('Occurrences::Occurrence').memberElement then
type = resolveGlobal('Occurrences::Occurrence::snapshots').memberElement
else
type.oclIsKindOf(Feature) and
let feature : Feature = type.oclAsType(Feature) in
feature.featuringType->includes(owningType) and
feature.redefinesFromLibrary('Occurrences::Occurrence::snapshots')
endif

isOwnedCrossFeature() : Boolean
Return whether this Feature is an owned cross Feature of an end Feature.
body: owningNamespace <> null and
owningNamespace.oclIsKindOf(Feature) and
owningNamespace.oclAsType(Feature).ownedCrossFeature() = self

namingFeature() : Feature [0..1]
By default, the naming Feature of a Feature is given by its first redefinedFeature of its first
ownedRedefinition, if any.
body: if ownedRedefinition->isEmpty() then
null
else
ownedRedefinition->at(1).redefinedFeature
endif

ownedCrossFeature() : Feature [0..1]
If this Feature is an end Feature of its owningType, then return the first ownedMember of the Feature that is a
Feature, but not a Multiplicity or a MetadataFeature, and whose owningMembership is not a
FeatureMembership. If this exists, it is the crossFeature of the end Feature.
body: if not isEnd or owningType = null then null
else
let ownedMemberFeatures: Sequence(Feature) =
ownedMember->selectByKind(Feature)->
reject(oclIsKindOf(Multiplicity) or
oclIsKindOf(MetadataFeature) or
oclIsKindOf(FeatureValue))->
reject(owningMembership.oclIsKindOf(FeatureMembership)) in
if ownedMemberFeatures.isEmpty() then null
else ownedMemberFeatures->first()
endif

redefines(redefinedFeature : Feature) : Boolean
Check whether this Feature directly redefines the given redefinedFeature.
body: ownedRedefinition.redefinedFeature->includes(redefinedFeature)

redefinesFromLibrary(libraryFeatureName : String) : Boolean
Check whether this Feature directly redefines the named library Feature. libraryFeatureName must conform
to the syntax of a KerML qualified name and must resolve to a Feature in global scope.
body: let mem: Membership = resolveGlobal(libraryFeatureName) in
mem <> null and mem.memberElement.oclIsKindOf(Feature) and
redefines(mem.memberElement.oclAsType(Feature))

subsetsChain(first : Feature, second : Feature) : Boolean
Check whether this Feature directly or indirectly specializes a Feature whose last two chainingFeatures are
the given Features first and second.
body: allSuperTypes()->selectAsKind(Feature)->
exists(f | let n: Integer = f.chainingFeature->size() in
n >= 2 and
f.chainingFeature->at(n-1) = first and
f.chainingFeature->at(n) = second)
supertypes(excludeImplied : Boolean) : Type [0..*] {redefines supertypes}
body: let supertypes : OrderedSet(Type) =
self.oclAsType(Type).supertypes(excludeImplied) in
if featureTarget = self then supertypes
else supertypes->append(featureTarget)
endif

typingFeatures() : Feature [0..*]
Return the Features used to determine the types of this Feature (other than this Feature itself). If this
Feature is not conjugated, then the typingFeatures consist of all subsetted Features, except from
CrossSubsetting, and the last chainingFeature (if any). If this Feature is conjugated, then the
typingFeatures are only its originalType (if the originalType is a Feature).
Note. CrossSubsetting is excluded from the determination of the type of a Feature in order to avoid
circularity in the construction of implied CrossSubsetting relationships. The

validateFeatureCrossFeatureType requires that the crossFeature of a Feature have the same type as
the Feature.
body: if not isConjugated then
let subsettedFeatures : OrderedSet(Feature) =
subsetting->reject(s | s.oclIsKindOf(CrossSubsetting)).subsettedFeatures in
if chainingFeature->isEmpty() or
subsettedFeature->includes(chainingFeature->last())
then subsettedFeatures
else subsettedFeatures->append(chainingFeature->last())
endif
else if conjugator.originalType.oclIsKindOf(Feature) then
OrderedSet{conjugator.originalType.oclAsType(Feature)}
else OrderedSet{}
endif endif

# Constraints

checkFeatureCrossingSpecialization
If this Feature has isEnd = true and ownedCrossFeature returns a non-null value, then the crossFeature
of the Feature must be the Feature returned from ownedCrossFeature (which implies that this Feature has
an appropriate ownedCrossSubsetting to realize this).
ownedCrossFeature() <> null implies
crossFeature = ownedCrossFeature()

checkFeatureDataValueSpecialization
If a Feature has an ownedTyping relationship to a DataType, then it must directly or indirectly specialize
Base::dataValues from the Kernel Semantic Library.
ownedTyping.type->exists(selectByKind(DataType)) implies
specializesFromLibrary('Base::dataValues')

checkFeatureEndRedefinition
If a Feature has isEnd = true and an owningType that is not empty, then, for each direct supertype of its
owningType, it must redefine the endFeature at the same position, if any.
isEnd and owningType <> null implies
let i : Integer =
owningType.ownedEndFeature->indexOf(self) in
owningType.ownedSpecialization.general->
forAll(supertype |
supertype.endFeature->size() >= i implies
redefines(supertype.endFeature->at(i))

checkFeatureEndSpecialization
If a Feature has isEnd = true and an owningType that is an Association or a Connector, then it must
directly or indirectly specialize Links::Link::participant from the Kernel Semantic Library.
isEnd and owningType <> null and
(owningType.oclIsKindOf(Association) or
owningType.oclIsKindOf(Connector)) implies
specializesFromLibrary('Links::Link::participant')

checkFeatureFeatureMembershipTypeFeaturing
If a Feature is owned via a FeatureMembership, then it must have a featuringType for which the operation
isFeaturingType returns true.
owningFeatureMembership <> null implies
featuringTypes->exists(t | isFeaturingType(t))

checkFeatureFlowFeatureRedefinition
If a Feature is the first ownedFeature of a first or second FlowEnd, then it must directly or indirectly specialize
either Transfers::Transfer::source::sourceOutput or
Transfers::Transfer::target::targetInput, respectively, from the Kernel Semantic Library.
owningType <> null and
owningType.oclIsKindOf(FlowEnd) and
owningType.ownedFeature->at(1) = self implies
let flowType : Type = owningType.owningType in
flowType <> null implies
let i : Integer =
flowType.ownedFeature.indexOf(owningType) in
(i = 1 implies
redefinesFromLibrary('Transfers::Transfer::source::sourceOutput')) and
(i = 2 implies
redefinesFromLibrary('Transfers::Transfer::source::targetInput'))

checkFeatureObjectSpecialization
If a Feature has an ownedTyping relationship to a Structure, then it must directly or indirectly specialize
Objects::objects from the Kernel Semantics Library.
ownedTyping.type->exists(selectByKind(Structure)) implies
specializesFromLibary('Objects::objects')

checkFeatureOccurrenceSpecialization
If a Feature has an ownedTyping relationship to a Class, then it must directly or indirectly specialize
Occurrences::occurrences from the Kernel Semantic Library.
ownedTyping.type->exists(selectByKind(Class)) implies
specializesFromLibrary('Occurrences::occurrences')

checkFeatureOwnedCrossFeatureRedefinitionSpecialization
If this Feature is the ownedCrossFeature of an end Feature, then, for any end Feature that is redefined by
the owning end Feature of this Feature, this Feature must subset the crossFeature of the redefined end
Feature, if this exists.
isOwnedCrossFeature() implies
ownedSubsetting.subsettedFeature->includesAll(
owner.oclAsType(Feature).ownedRedefinition.redefinedFeature->
select(crossFeature <> null).crossFeature)

checkFeatureOwnedCrossFeatureSpecialization
If this Feature is the ownedCrossFeature of an end Feature, then it must directly or indirectly specialize the
types of its owning end Feature.
isOwnedCrossFeature() implies
owner.oclAsType(Feature).type->forAll(t | self.specializes(t))

checkFeatureOwnedCrossFeatureTypeFeaturing
If this Feature is the ownedCrossFeature of an end Feature, then it must have featuringTypes consistent
with the crossing from other end Features of the owningType of its end Feature.
isOwnedCrossFeature() implies
let otherEnds : OrderedSet(Feature) =
owner.oclAsType(Feature).owningType.endFeature->excluding(self) in
if (otherEnds->size() = 1) then
featuringType = otherEnds->first().type
else
featuringType->size() = 1 and
featuringType->first().isCartesianProduct() and
featuringType->first().asCartesianProduct() = otherEnds.type and
featuringType->first().allSupertypes()->includesAll(
owner.oclAsType(Feature).ownedRedefinition.redefinedFeature->
select(crossFeature() <> null).crossFeature().featuringType)
endif

checkFeatureParameterRedefinition
If a Feature is a parameter of an owningType that is a Behavior or Step, but not
• A result parameter
• A parameter of an InvocationExpression, with at least one non-implied ownedRedefinition
then, for each direct supertype of its owningType that is also a Behavior or Step, it must redefine the parameter
at the same position, if any.
owningType <> null and
not owningFeatureMembership.
oclIsKindOf(ReturnParameterMembership) and
(owningType.oclIsKindOf(Behavior) or
owningType.oclIsKindOf(Step) and
(owningType.oclIsKindOf(InvocationExpression) implies
not ownedRedefinition->exists(not isImplied))
implies
let i : Integer =
owningType.ownedFeature->select(direction <> null)->
reject(owningFeatureMembership.
oclIsKindOf(ReturnParameterMembership))->
indexOf(self) in
owningType.ownedSpecialization.general->
forAll(supertype |
let ownedParameters : Sequence(Feature) =
supertype.ownedFeature->select(direction <> null)->
reject(owningFeatureMembership.
oclIsKindOf(ReturnParameterMembership)) in
ownedParameters->size() >= i implies
redefines(ownedParameters->at(i))

checkFeaturePortionSpecialization
If a Feature has isPortion = true, an ownedTyping relationship to a Class, and an owningType that is a
Class or another Feature typed by a Class, then it must directly or indirectly specialize
Occurrences::Occurrence::portions from the Kernel Semantic Library.
isPortion and
ownedTyping.type->includes(oclIsKindOf(Class)) and
owningType <> null and
(owningType.oclIsKindOf(Class) or
owningType.oclIsKindOf(Feature) and
owningType.oclAsType(Feature).type->
exists(oclIsKindOf(Class))) implies
specializesFromLibrary('Occurrence::Occurrence::portions')

checkFeatureResultRedefinition
If a Feature is a result parameter of an owningType that is a Function or Expression, then, for each direct
supertype of its owningType that is also a Function or Expression, it must redefine the result parameter.
owningType <> null and
(owningType.oclIsKindOf(Function) and
self = owningType.oclAsType(Function).result or
owningType.oclIsKindOf(Expression) and
self = owningType.oclAsType(Expression).result) implies
owningType.ownedSpecialization.general->
select(oclIsKindOf(Function) or oclIsKindOf(Expression))->
forAll(supertype |
redefines(
if superType.oclIsKindOf(Function) then
superType.oclAsType(Function).result
else
superType.oclAsType(Expression).result
endif)

checkFeatureSpecialization
A Feature must directly or indirectly specialize Base::things from the Kernel Semantic Library.
specializesFromLibrary('Base::things')

checkFeatureSubobjectSpecialization
A composite Feature typed by a Structure, and whose ownedType is a Structure or another Feature typed
by a Structure must directly or indirectly specialize Objects::Object::subobjects
isComposite and
ownedTyping.type->includes(oclIsKindOf(Structure)) and
owningType <> null and
(owningType.oclIsKindOf(Structure) or
owningType.type->includes(oclIsKindOf(Structure))) implies
specializesFromLibrary('Occurrence::Occurrence::suboccurrences')

checkFeatureSuboccurrenceSpecialization
A composite Feature that has an ownedTyping relationship to a Class, and whose ownedType is a Class or
another Feature typed by a Class, must directly or indirectly specialize
Occurrences::Occurrence::suboccurrences
isComposite and
ownedTyping.type->includes(oclIsKindOf(Class)) and
owningType <> null and
(owningType.oclIsKindOf(Class) or
owningType.oclIsKindOf(Feature) and
owningType.oclAsType(Feature).type->
exists(oclIsKindOf(Class))) implies
specializesFromLibrary('Occurrence::Occurrence::suboccurrences')

checkFeatureValuationSpecialization
If a Feature has a FeatureValue, no ownedSpecializations that are not implied, and is not directed, then it
must specialize the result of the value Expression of the FeatureValue.
direction = null and
ownedSpecializations->forAll(isImplied) implies
ownedMembership->
selectByKind(FeatureValue)->
forAll(fv | specializes(fv.value.result))

## Derive

deriveFeatureChainingFeature
The chainingFeatures of a Feature are the chainingFeatures of its ownedFeatureChainings.
chainingFeature = ownedFeatureChaining.chainingFeature

deriveFeatureCrossFeature
The crossFeature of a Feature is the second chainingFeature of the crossedFeature of the
ownedCrossSubsetting of the Feature, if any.
crossFeature =
if ownedCrossSubsetting = null then null
else
let chainingFeatures: Sequence(Feature) =
ownedCrossSubsetting.crossedFeature.chainingFeature in
if chainingFeatures->size() < 2 then null
else chainingFeatures->at(2)
endif

deriveFeatureFeatureTarget
If a Feature has no chainingFeatures, then its featureTarget is the Feature itself, otherwise the
featureTarget is the last of the chainingFeatures.
featureTarget = if chainingFeature->isEmpty() then self else chainingFeature->last() endif

deriveFeatureFeaturingType
The featuringTypes of a Feature include the featuringTypes of all the typeFeaturings of the Feature.
If the Feature has chainingFeatures, then its featuringTypes also include the featuringTypes of the first
chainingFeature.
featuringType =
let featuringTypes : OrderedSet(Type) =
featuring.type->asOrderedSet() in
if chainingFeature->isEmpty() then featuringTypes
else
featuringTypes->
union(chainingFeature->first().featuringType)->
asOrderedSet()
endif

deriveFeatureOwnedCrossSubsetting
The ownedCrossSubsetting of a Feature is the ownedSubsetting that is a CrossSubsetting, if any.
ownedCrossSubsetting =
let crossSubsettings: Sequence(CrossSubsetting) =
ownedSubsetting->selectByKind(CrossSubsetting) in
if crossSubsettings->isEmpty() then null
else crossSubsettings->first()
endif

deriveFeatureOwnedFeatureChaining
The ownedFeatureChainings of a Feature are the ownedRelationships that are FeatureChainings.
ownedFeatureChaining = ownedRelationship->selectByKind(FeatureChaining)

deriveFeatureOwnedFeatureInverting
The ownedFeatureInvertings of a Feature are its ownedRelationships that are FeatureInvertings.
ownedFeatureInverting = ownedRelationship->selectByKind(FeatureInverting)->
select(fi | fi.featureInverted = self)

deriveFeatureOwnedRedefinition
The ownedRedefinitions of a Feature are its ownedSubsettings that are Redefinitions.
ownedRedefinition = ownedSubsetting->selectByKind(Redefinition)

deriveFeatureOwnedReferenceSubsetting
The ownedReferenceSubsetting of a Feature is the first ownedSubsetting that is a
ReferenceSubsetting (if any).
ownedReferenceSubsetting =
let referenceSubsettings : OrderedSet(ReferenceSubsetting) =
ownedSubsetting->selectByKind(ReferenceSubsetting) in
if referenceSubsettings->isEmpty() then null
else referenceSubsettings->first() endif

deriveFeatureOwnedSubsetting
The ownedSubsettings of a Feature are its ownedSpecializations that are Subsettings.
ownedSubsetting = ownedSpecialization->selectByKind(Subsetting)

deriveFeatureOwnedTypeFeaturing
The ownedTypeFeaturings of a Feature are its ownedRelationships that are TypeFeaturings and which
have the Feature as their featureOfType.
ownedTypeFeaturing = ownedRelationship->selectByKind(TypeFeaturing)->
select(tf | tf.featureOfType = self)

deriveFeatureOwnedTyping
The ownedTypings of a Feature are its ownedSpecializations that are FeatureTypings.
ownedTyping = ownedGeneralization->selectByKind(FeatureTyping)

deriveFeatureType
The types of a Feature are the union of the types of its typings and the types of the Features it subsets,
with all redundant supertypes removed. If the Feature has chainingFeatures, then the union also includes the
types of the last chainingFeature.
type =
let types : OrderedSet(Types) = OrderedSet{self}->
-- Note: The closure operation automatically handles circular relationships.
closure(typingFeatures()).typing.type->asOrderedSet() in
types->reject(t1 | types->exist(t2 | t2 <> t1 and t2.specializes(t1)))

validateFeatureChainingFeatureConformance
Each chainingFeature (other than the first) must be featured within the previous chainingFeature.
Sequence{2..chainingFeature->size()}->forAll(i |
chainingFeature->at(i).isFeaturedWithin(chainingFeature->at(i-1)))

validateFeatureChainingFeatureNotOne
A Feature must have either no chainingFeatures or more than one.
chainingFeature->size() <> 1

validateFeatureChainingFeaturesNotSelf
A Feature cannot be one of its own chainingFeatures.
chainingFeature->excludes(self)

validateFeatureConstantIsVariable
A Feature with isConstant = true must have isVariable = true
isConstant implies isVariable

validateFeatureCrossFeatureSpecialization
If this Feature has a crossFeature, then, for any Feature that is redefined by this Feature, the
crossFeature must specialize the crossFeature of the redefined end Feature, if this exists.
crossFeature <> null implies
ownedRedefinition.redefinedFeature.crossFeature->
forAll(f | f <> null implies crossFeature.specializes(f))

validateFeatureCrossFeatureType
The crossFeature of a Feature must have the same types as the Feature.
crossFeature <> null implies
crossFeature.type->asSet() = type->asSet()

validateFeatureEndIsConstant
A Feature with isEnd = true and isVariable = true must have isConstant = true.
isEnd and isVariable implies isConstant

validateFeatureEndMultiplicity
If a Feature has isEnd = true, then it must have multiplicity 1..1.
isEnd implies
multiplicities().allSuperTypes()->flatten()->
selectByKind(MultiplicityRange)->exists(hasBounds(1,1))

validateFeatureEndNoDirection
A Feature with isEnd = true must have no direction.
isEnd implied direction = null

validateFeatureEndNotDerivedAbstractCompositeOrPortion
A Feature with isEnd = true must have all of isDerived = false, isAbstract = false, isComposite
= false, and isPortion = false.
isEnd implies not (isDerived or isAbstract or isComposite or isPortion)

validateFeatureIsVariable
A Feature with isVariable = true must have an owningType that directly or indirectly specializes the Class
Occurrences::Occurrence from the Kernel Semantic Library.
isVariable implies
owningType <> null and
owningType.specializes('Occurrences::Occurrence')

validateFeatureMultiplicityDomain
If a Feature has a multiplicity, then the featuringTypes of the multiplicity must be the same as those
of the Feature itself.
multiplicity <> null implies multiplicity.featuringType = featuringType

validateFeatureOwnedCrossSubsetting
A Feature must have at most one ownedSubsetting that is a CrossSubsetting.
ownedSubsetting->selectByKind(CrossSubsetting)->size() <= 1

validateFeatureOwnedReferenceSubsetting
A Feature must have at most one ownedSubsetting that is an ReferenceSubsetting.
ownedSubsetting->selectByKind(ReferenceSubsetting)->size() <= 1

validateFeaturePortionNotVariable
isPortion implies not isVariable
*/
