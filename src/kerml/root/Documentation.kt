@file:Suppress("unused")

package sandbox.kerml.root

/**
 * Documentation is a Comment that specifically documents a documentedElement, which must be its owner.
 */
interface Documentation : Comment {
    /**
     * The Element that is documented by this Documentation.
     * 
     * /documentedElement : Element {subsets owner, redefines annotatedElement}
     */
    val documentedElement: Element
        get() = checkNotNull(owner)

    override val annotatedElement: List<Element>
        get() = listOf(documentedElement)
}
