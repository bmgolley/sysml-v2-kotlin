package sandbox.featurechains.kerml.core.features

import sandbox.kerml.core.features.FeatureChaining

val Iterable<FeatureChaining>.chainingFeature
    get() = map(FeatureChaining::chainingFeature)