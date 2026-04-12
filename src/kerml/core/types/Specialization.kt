package sandbox.kerml.core.types

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * `Specialization` is a [Relationship] between two [Types][Type] that requires all instances of the specific type to
 * also be instances of the general [Type] (i.e., the set of instances of the specific [Type] is a subset of those of
 * the general [Type], which might be the same set).
 * 
 * Constraints
 * validateSpecificationSpecificNotConjugated
 * The specific Type of a Specialization cannot be a conjugated Type.
 * not specific.isConjugated
 */
interface Specialization : Relationship {
    /**
     * A [Type] with a superset of all instances of the specific [Type], which might be the same set.
     * 
     * `general : Type {redefines target}`
     */
    var general: Type

    override val target: List<Element>
        get() = listOf(general)

    /**
     * The [Type] that is the specific [Type] of this `Specialization` and owns it as its [owningRelatedElement].
     * 
     * `/owningType : Type [0..1] {subsets specific, owningRelatedElement}`
     */
    val owningType: Type?
        get() = specific.takeIf { it === owningRelatedElement }

    /**
     * A Type with a subset of all instances of the general `Type`, which might be the same set.
     * 
     * `specific : Type {redefines source}`
     */
    var specific: Type

    override val source: List<Element>
        get() = listOf(specific)
}
