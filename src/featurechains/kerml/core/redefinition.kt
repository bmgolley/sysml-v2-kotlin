@file:Suppress("unused")

package sandbox.featurechains.kerml.core

import sandbox.kerml.core.Feature
import sandbox.kerml.core.Redefinition

val Iterable<Redefinition>.redefinedFeature: Collection<Feature>
    get() = map(Redefinition::redefinedFeature)
