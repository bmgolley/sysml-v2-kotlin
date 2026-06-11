@file:Suppress("unused")

package sandbox.featurechains.kerml.core

import sandbox.kerml.core.Feature
import sandbox.kerml.core.Redefinition

val Iterable<Feature>.isEnd: Collection<Boolean>
    get() = map(Feature::isEnd)

val Iterable<Feature>.redefinition: Collection<Redefinition>
    get() = flatMap(Feature::redefinition)

val Iterable<Feature>.ownedRedefinition: Collection<Redefinition>
    get() = flatMap(Feature::ownedRedefinition)
