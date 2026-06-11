@file:Suppress("unused")

package sandbox.kerml.core

import sandbox.kerml.root.Relationship

interface TypeFeaturing : Relationship {
    var featureOfType: Feature
    var featuringType: Type
}