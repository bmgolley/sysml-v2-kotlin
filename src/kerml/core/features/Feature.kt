package sandbox.kerml.core.features

import sandbox.kerml.core.types.Type

interface Feature : Type {
    val featuringType: List<Type>
    val isEnd: Boolean

    fun allRedefinedFeatures(): Set<Feature>

    val redefinition: Collection<Redefinition>
}
