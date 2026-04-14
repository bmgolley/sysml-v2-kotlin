package sandbox.kerml.core.types

import sandbox.kerml.core.features.Feature
import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.OwningMembership

/**
 * A FeatureMembership is an OwningMembership between an ownedMemberFeature and an owningType. If
the ownedMemberFeature has isVariable = false, then the FeatureMembership implies that the
owningType is also a featuringType of the ownedMemberFeature. If the ownedMemberFeature has
isVariable = true, then the FeatureMembership implies that the ownedMemberFeature is featured by the
snapshots of the owningType, which must specialize the Kernel Semantic Library base class Occurrence.
 */
interface FeatureMembership : OwningMembership {
    /**
     * The [Feature] that this `FeatureMembership` relates to its [owningType], making it an
     * [ownedFeature][Type.ownedFeature] of the [owningType].
     * 
     * ```ocl
     * /ownedMemberFeature : Feature {redefines ownedMemberElement}
     * ```
     */
    val ownedMemberFeature: Feature

    override val ownedMemberElement: Element
        get() = ownedMemberFeature

    /**
     * ```/owningType : Type {subsets type, redefines membershipOwningNamespace}```
     */
    val owningType: Type

    override val membershipOwningNamespace: Namespace
        get() = owningType
}

