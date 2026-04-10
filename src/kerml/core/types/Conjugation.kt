package sandbox.kerml.core.types

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * Conjugation is a Relationship between two types in which the conjugatedType inherits all the Features of
 * the originalType, but with all input and output Features reversed. That is, any Features with a
 * direction in relative to the originalType are considered to have an effective direction of out relative to the
 * conjugatedType and, similarly, Features with direction out in the originalType are considered to have an
 * effective direction of in in the conjugatedType. Features with direction inout, or with no direction, in
 * the originalType, are inherited without change.
 * A Type may participate as a conjugatedType in at most one Conjugation relationship, and such a Type may
 * not also be the specific Type in any Specialization relationship
 */
interface Conjugation : Relationship {
    /**
     * The Type that is the result of applying Conjugation to the originalType.
     * 
     * conjugatedType : Type {redefines source}
     */
    var conjugatedType: Type
    
    override val source: List<Element>
        get() = listOf(conjugatedType)
    
    /**
     * The Type to be conjugated.
     * 
     * originalType : Type {redefines target}
     */
    var originalType: Type
    
    override val target: List<Element>
        get() = listOf(originalType)
    
    /**
     * The conjugatedType of this Conjugation that is also its owningRelatedElement.
     * 
     * /owningType : Type [0..1] {subsets conjugatedType, owningRelatedElement}
     */
    val owningType: Type?
        get() = conjugatedType.takeIf { it === owningRelatedElement }
}

