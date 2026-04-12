@file:Suppress("unused")

package sandbox.featurechains.kerml.core.features

import sandbox.kerml.core.features.Feature
import sandbox.kerml.core.features.Redefinition

val Iterable<Redefinition>.redefinedFeature: Collection<Feature>
    get() = map(Redefinition::redefinedFeature)
