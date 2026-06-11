@file:Suppress("unused")

package sandbox.featurechains.kerml.core

import sandbox.kerml.core.FeatureChaining

val Iterable<FeatureChaining>.chainingFeature
    get() = map(FeatureChaining::chainingFeature)