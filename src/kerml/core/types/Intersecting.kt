package sandbox.kerml.core.types

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * Intersecting is a Relationship that makes its intersectingType one of the intersectingTypes of its
 * typeIntersected.
 */
interface Intersecting : Relationship {
    /**
     * Type that partly determines interpretations of typeIntersected, as described in Type::intersectingType.
     * 
     * intersectingType : Type {redefines target}
     */
    var intersectingType: Type

    override val target: List<Element>
        get() = listOf(intersectingType)

    /**
     * Type with interpretations partly determined by intersectingType, as described in Type::intersectingType.
     * 
     * /typeIntersected : Type {subsets owningRelatedElement, redefines source}
     */
    val typeIntersected: Type

    override val source: List<Element>
        get() = listOf(typeIntersected)
}
