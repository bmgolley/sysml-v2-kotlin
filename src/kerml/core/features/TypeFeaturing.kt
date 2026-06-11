@file:Suppress("unused")

package sandbox.kerml.core.features

import sandbox.kerml.core.types.Type
import sandbox.kerml.root.elements.Relationship

interface TypeFeaturing : Relationship {
    var featureOfType: Feature
    var featuringType: Type
}