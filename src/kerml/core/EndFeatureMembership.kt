@file:Suppress("unused")

package sandbox.kerml.core

/**
 * EndFeatureMembership is a FeatureMembership that requires its memberFeature be owned and have isEnd
 * = true.
 */
interface EndFeatureMembership : FeatureMembership {
    override val ownedMemberFeature: Feature
}

/*
Constraints
validateEndFeatureMembershipIsEnd
The ownedMemberFeature of an EndFeatureMembership must be an end Feature.
ownedMemberFeature.isEnd
*/
