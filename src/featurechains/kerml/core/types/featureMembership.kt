@file:Suppress("unused")

package sandbox.featurechains.kerml.core.types

import sandbox.kerml.core.features.Feature
import sandbox.kerml.core.types.FeatureMembership

/** @see FeatureMembership.ownedMemberFeature */
val Iterable<FeatureMembership>.ownedMemberFeature: List<Feature>
    get() = map(FeatureMembership::ownedMemberFeature)