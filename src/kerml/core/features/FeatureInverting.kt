@file:Suppress("unused")

package sandbox.kerml.core.features

import sandbox.kerml.root.elements.Relationship

interface FeatureInverting : Relationship {
    var featureInverted: Feature
    var invertingFeature: Feature
}