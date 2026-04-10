package sandbox.kerml.core.types

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * A Disjoining is a Relationship between Types asserted to have interpretations that are not shared (disjoint)
 * between them, identified as typeDisjoined and disjoiningType. For example, a Classifier for mammals is
 * disjoint from a Classifier for minerals, and a Feature for people's parents is disjoint from a Feature for their
 * children.
 */
interface Disjoining : Relationship {
    /**
     * Type asserted to be disjoint with the typeDisjoined.
     * 
     * disjoiningType : Type {redefines target}
     */
    var disjoiningType: Type
    
    override val target: List<Element>
        get() = listOf(disjoiningType)
    
    /**
     * A typeDisjoined that is also an owningRelatedElement.
     * 
     * /owningType : Type [0..1] {subsets typeDisjoined, owningRelatedElement}
     */
    val owningType: Type?
        get() = typeDisjoined.takeIf { it === owningRelatedElement }
    
    /**
     * Type asserted to be disjoint with the disjoiningType.
     * 
     * typeDisjoined : Type {redefines source}
     */
    var typeDisjoined: Type
    
    override val source: List<Element>
        get() = listOf(typeDisjoined)
}
