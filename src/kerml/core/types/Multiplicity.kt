package sandbox.kerml.core.types

import sandbox.kerml.core.features.Feature

/**
 * A Multiplicity is a Feature whose co-domain is a set of natural numbers giving the allowed cardinalities of
 * each typeWithMultiplicity. The cardinality of a Type is defined as follows, depending on whether the Type is
 * a Classifier or Feature.
 * 
 *  - Classifier – The number of basic instances of the Classifier, that is, those instances representing
 *     things, which are not instances of any subtypes of the Classifier that are Features.
 * - Features – The number of instances with the same featuring instances. In the case of a Feature with a
 *     Classifier as its featuringType, this is the number of values of Feature for each basic instance of
 *     the Classifier. Note that, for non-unique Features, all duplicate values are included in this count.
 * 
 * Multiplicity co-domains (in models) can be specified by Expression that might vary in their results. If the
 * typeWithMultiplicity is a Classifier, the domain of the Multiplicity shall be Base::Anything. If the
 * typeWithMultiplicity is a Feature, the Multiplicity shall have the same domain as the
 * typeWithMultiplicity.
 * 
 * Constraints:
 * 
 * - checkMultiplicitySpecialization
 *     A Multiplicity must directly or indirectly specialize the Feature Base::naturals from the Kernel Semantic
 *     Library.
 *     specializesFromLibrary('Base::naturals')
 * - checkMultiplicityTypeFeaturing
 *     If the owningType of a Multiplicity is a Feature, then the Multiplicity must have the same
 *     featuringTypes as that Feature. Otherwise, it must have no featuringTypes (meaning that it is implicitly
 *     featured by the base Classifier Anything).
 *     if owningType <> null and owningType.oclIsKindOf(Feature) then
 *     featuringType =
 *     owningType.oclAsType(Feature).featuringType
 *     else
 *     featuringType->isEmpty()
 *     endif
 */
interface Multiplicity : Feature
