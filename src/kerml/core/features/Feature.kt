package sandbox.kerml.core.features

import sandbox.kerml.core.types.Type

interface Feature : Type {
    fun allRedefinedFeatures(): Set<Feature>
}
