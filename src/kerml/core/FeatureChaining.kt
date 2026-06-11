package sandbox.kerml.core

import sandbox.kerml.root.Relationship

interface FeatureChaining : Relationship {
    var chainingFeature: Feature
}