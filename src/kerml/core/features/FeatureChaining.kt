package sandbox.kerml.core.features

import sandbox.kerml.root.elements.Relationship

interface FeatureChaining : Relationship {
    var chainingFeature: Feature
}