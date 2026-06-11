@file:Suppress("unused")

package sandbox.kerml.core

import sandbox.kerml.root.Relationship

interface FeatureInverting : Relationship {
    var featureInverted: Feature
    var invertingFeature: Feature
}