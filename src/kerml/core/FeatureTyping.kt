@file:Suppress("unused")

package sandbox.kerml.core

import sandbox.kerml.root.Relationship

interface FeatureTyping : Relationship {
    var type: Type
    var typedFeature: Feature
}