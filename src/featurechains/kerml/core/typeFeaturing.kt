@file:Suppress("unused")

package sandbox.featurechains.kerml.core

import sandbox.kerml.core.TypeFeaturing

val Iterable<TypeFeaturing>.featuringType
    get() = map(TypeFeaturing::featuringType)