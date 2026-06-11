@file:Suppress("unused")

package sandbox.kerml.core

import sandbox.kerml.root.Element
import sandbox.kerml.root.Relationship

/**
 * Differencing is a Relationship that makes its differencingType one of the differencingTypes of its
 * typeDifferenced.
 */
interface Differencing : Relationship {
    /**
     * [Type] that partly determines interpretations of [typeDifferenced], as described in
     * [Type::differencingType][Type.differencingType].
     * 
     * ```ocl
     * differencingType : Type {redefines target}
     * ```
     */
    var differencingType: Type

    override val target: List<Element>
        get() = listOf(differencingType)

    /**
     * [Type] with interpretations partly determined by [differencingType], as described in
     * [Type::differencingType][Type.differencingType].
     * 
     * ```ocl
     * /typeDifferenced : Type {subsets owningRelatedElement, redefines source}
     * ```
     */
    val typeDifferenced: Type

    override val source: List<Element>
        get() = listOf(typeDifferenced)
}
