@file:Suppress("unused")

package sandbox.kerml.core.types

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

interface Unioning : Relationship {
    /**
     * [Type] with interpretations partly determined by [unioningType], as described in [Type.unioningType].
     *
     * ```ocl
     * /typeUnioned : Type {subsets owningRelatedElement, redefines source}
     * ```
     */
    val typeUnioned: Type

    override val source: List<Element>
        get() = listOf(typeUnioned)

    /**
     * `Type` that partly determines interpretations of [typeUnioned], as described in [Type.unioningType].
     *
     * ```ocl
     * unioningType : Type {redefines target}
     * ```
     */
    var unioningType: Type

    override val target: List<Element>
        get() = listOf(unioningType)
}
