@file:Suppress("unused")

package sandbox.featurechains.kerml.core

import sandbox.kerml.core.Feature
import sandbox.kerml.core.FeatureMembership

/** @see FeatureMembership.ownedMemberFeature */
val Iterable<FeatureMembership>.ownedMemberFeature: List<Feature>
    get() = map(FeatureMembership::ownedMemberFeature)