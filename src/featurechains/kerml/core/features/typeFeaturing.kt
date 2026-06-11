package sandbox.featurechains.kerml.core.features

import sandbox.kerml.core.features.TypeFeaturing

val Iterable<TypeFeaturing>.featuringType
    get() = map(TypeFeaturing::featuringType)