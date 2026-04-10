package sandbox.kerml.root.annotations

import sandbox.kerml.root.elements.Element
import sandbox.kerml.root.elements.Relationship

/**
 * An Annotation is a Relationship between an AnnotatingElement and the Element that is annotated by that
 * AnnotatingElement.
 * 
 * Constraints:
 * - validateAnnotationAnnotatedElementOwnership
 *     An Annotation owns its annotatingElement if and only if it is owned by its annotatedElement.
 *     (owningAnnotatedElement <> null) = (ownedAnnotatingElement <> null)
 * - validateAnnotationAnnotatingElement
 *     Either the ownedAnnotatingElement of an Annotation must be non-null, or the owningAnnotatingElement
 *     must be non-null, but not both.
 *     ownedAnnotatingElement <> null xor owningAnnotatingElement <> null
 */
@Suppress("unused")
interface Annotation : Relationship {
    /**
     * The Element that is annotated by the annotatingElement of this Annotation.
     * 
     * annotatedElement : Element {redefines target}
     */
    var annotatedElement: Element

    override val target: List<Element>
        get() = listOf(annotatedElement)

    /**
     * The AnnotatingElement that annotates the annotatedElement of this Annotation. This is always either the
     * ownedAnnotatingElement or the owningAnnotatingElement.
     * 
     * `/annotatingElement : AnnotatingElement {redefines source}`
     * 
     * Constraint:
     * - deriveAnnotationAnnotatingElement
     *     The annotatingElement of an Annotation is either its ownedAnnotatingElement or its
     *     owningAnnotatingElement.
     *     annotatingElement =
     *         if ownedAnnotatingElement <> null then ownedAnnotatingElement
     *         else owningAnnotatingElement
     *         endif
     */
    val annotatingElement: AnnotatingElement
        get() = ownedAnnotatingElement
            ?: owningAnnotatingElement
            ?: error("Annotation must have either a non-null ownedAnnotatingElement or owningAnnotatingElement")

    /**
     * The annotatingElement of this Annotation, when it is an ownedRelatedElement.
     * 
     * /ownedAnnotatingElement : AnnotatingElement [0..1] {subsets annotatingElement, ownedRelatedElement}
     * 
     * Constraint:
     * - deriveAnnotationOwnedAnnotatingElement
     *     The ownedAnnotatingElement of an Annotation is the first ownedRelatedElement that is an
     *     AnnotatingElement, if any.
     *     ownedAnnotatingElement =
     *         let ownedAnnotatingElements : Sequence(AnnotatingElement) =
     *             ownedRelatedElement->selectByKind(AnnotatingElement) in
     *         if ownedAnnotatingElements->isEmpty() then null
     *         else ownedAnnotatingElements->first()
     *         endif
     */
    val ownedAnnotatingElement: AnnotatingElement?
        get() = ownedRelatedElement.firstOrNull(AnnotatingElement::class::isInstance) as AnnotatingElement?

    /**
     * The annotatedElement of this Annotation, when it is also the owningRelatedElement.
     * 
     * `/owningAnnotatedElement : Element [0..1] {subsets annotatedElement, owningRelatedElement}`
     */
    val owningAnnotatedElement: Element?
        get() = annotatedElement.takeIf { it === owningRelatedElement }


    /**
     * The annotatingElement of this Annotation, when it is the owningRelatedElement.
     * 
     * `/owningAnnotatingElement : AnnotatingElement [0..1] {subsets annotatingElement, owningRelatedElement}`
     */
    val owningAnnotatingElement: AnnotatingElement?
        get() = annotatingElement.takeIf { it === owningRelatedElement }
}
