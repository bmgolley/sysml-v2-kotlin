@file:Suppress("unused")

package sandbox.kerml.core.features

import sandbox.kerml.core.types.Type
import sandbox.kerml.root.elements.Relationship

interface FeatureTyping : Relationship {
    var type: Type
    var typedFeature: Feature
}