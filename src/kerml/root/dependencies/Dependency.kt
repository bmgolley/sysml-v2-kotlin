package sandbox.kerml.root.dependencies

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * A Dependency is a Relationship that indicates that one or more client Elements require one more
 * supplier Elements for their complete specification. In general, this means that a change to one of the supplier
 * Elements may necessitate a change to, or re-specification of, the client Elements.
 * Note that a Dependency is entirely a model-level Relationship, without instance-level semantics.
 */
@Suppress("unused")
interface Dependency : Relationship {
    /**
     * The Element or Elements dependent on the supplier Elements.
     * 
     * `client : Element [1..*] {redefines source, ordered}`
     */
    val client: List<Element>

    override val source: List<Element>
        get() = client

    /**
     * The Element or Elements on which the client Elements depend in some respect.
     * 
     * `supplier : Element [1..*] {redefines target, ordered}`
     */
    val supplier: List<Element>

    override val target: List<Element>
        get() = supplier
}
