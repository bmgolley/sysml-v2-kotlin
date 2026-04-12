@file:Suppress("unused")

package sandbox.featurechains.kerml.core.features

import sandbox.kerml.core.features.Feature
import sandbox.kerml.core.features.Redefinition

val Iterable<Feature>.isEnd: Collection<Boolean>
    get() = map(Feature::isEnd)

val Iterable<Feature>.redefinition: Collection<Redefinition>
    get() = flatMap(Feature::redefinition)
