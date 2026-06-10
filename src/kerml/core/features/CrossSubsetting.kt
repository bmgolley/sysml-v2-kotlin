@file:Suppress("unused")

package sandbox.kerml.core.features

/**
 * CrossSubsetting is a kind of Subsetting for end Features, as identified by crossingFeature, to subset a
 * chained Feature, identified by crossedFeature. It navigates to instances of the end Feature’s type from
 * instances of other end Feature types on the same owningType (at least two end Features are required for any of
 * them to have a CrossSubsetting).
 * The crossedFeature of a CrossSubsetting must have a feature chain of exactly two Features. The second
 * Feature in the chain is the crossFeature of the crossingFeature (end Feature), which has the same type as
 * the crossingFeature. When the owningType of the crossingFeature has exactly two end Features, the
 * first Feature in the chain of the crossedFeature is the other end Feature. The crossFeature’s
 * featuringType in this case is the other end Feature. When the owningType has more than two end Features,
 * the first Feature in the chain is a Feature that CrossMultiplies all the other end Features, which is also the
 * featuringType of the crossFeature.
 * A crossFeature must be owned by its featureCrossing (end Feature) when the featureCrossing
 * owningType has more than two end Features. Otherwise, for exactly two end Features, the crossFeatures of
 * each the ends can instead optionally be inherited by the other end from one of its types or a subsetted Feature.
 */
interface CrossSubsetting : Subsetting {
    /**
     * The chained Feature that is cross subset by the crossingFeature of this CrossSubsetting.
     * 
     * crossedFeature : Feature {redefines subsettedFeature}
     */
    var crossedFeature: Feature
    
    // override var subsettedFeature: Feature by ::crossedFeature
    
    /**
     * The end Feature that owns this CrossSubsetting relationship and is also its subsettingFeature.
     * 
     * /crossingFeature : Feature {redefines owningFeature, subsettingFeature}
     */
    val crossingFeature: Feature
    
    object Validation {
        /**
         * The crossedFeature of a CrossSubsetting must have exactly two chainingFeatures. If the
crossingFeature of the CrossSubsetting is one of two end Features, then the first chainingFeature
must be the other end Feature.
crossingFeature.isEnd and crossingFeature.owningType <> null implies
let endFeatures: Sequence(Feature) = crossingFeature.owningType.endFeature in
let chainingFeatures: Sequence(Feature) = crossedFeature.chainingFeature in
chainingFeatures->size() = 2 and
endFeatures->size() = 2 implies
chainingFeatures->at(1) = endFeatures->excluding(crossingFeature)->at(1)
         */
        fun validateCrossSubsettingCrossedFeature()
        
        /**
         * The crossingFeature of a CrossSubsetting must be an end Feature that is owned by a Type with at least
two end Features.
crossingFeature.isEnd and
crossingFeature.owningType<>null and
crossingFeature.owningType.endFeature ->size() > 1
         */
        fun validateCrossSubsettingCrossingFeature()
    }
}
