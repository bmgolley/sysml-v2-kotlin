package sandbox.kerml.root.annotations

import sandbox.kerml.root.elements.Element

/**
 * An AnnotatingElement is an Element that provides additional description of or metadata on some other
 * Element. An AnnotatingElement is either attached to its annotatedElements by Annotation
 * Relationships, or it implicitly annotates its owningNamespace.
 */
interface AnnotatingElement : Element {
    /**
     * The Elements that are annotated by this AnnotatingElement. If annotation is not empty, these are the
     * annotatedElements of the annotations. If annotation is empty, then it is the owningNamespace of the
     * AnnotatingElement.
     * 
     * `/annotatedElement : Element [1..*] {ordered}`
     * 
     * Constraint:
     * - deriveAnnotatingElementAnnotatedElement
     *     If an AnnotatingElement has annotations, then its annotatedElements are the annotatedElements of all
     *     its annotations. Otherwise, it's single annotatedElement is its owningNamespace.
     *     ```ocl
     *     annotatedElement =
     *         if annotation->notEmpty() then annotation.annotatedElement
     *         else Sequence{owningNamespace} endif
     *     ```
     */
    val annotatedElement: List<Element>
        get() = if (annotation.isNotEmpty()) {
            annotation.map(Annotation::annotatedElement)
        } else {
            listOf(checkNotNull(owningNamespace))
        }

    /**
     * The Annotations that relate this AnnotatingElement to its annotatedElements. This includes the
     * owningAnnotatingRelationship (if any) followed by all the ownedAnnotatingRelationshps.
     * 
     * `/annotation : Annotation [0..*] {subsets sourceRelationship, ordered}`
     * 
     * Constraint:
     * - deriveAnnotatingElementAnnotation
     *     The annotations of an AnnotatingElement are its owningAnnotatingRelationship (if any) followed by
     *     all its ownedAnnotatingRelationships.
     *     annotation =
     *     if owningAnnotatingRelationship = null then ownedAnnotatingRelationship
     *         else owningAnnotatingRelationship->prepend(owningAnnotatingRelationship)
     *     endif
     */
    val annotation: List<Annotation>
        get() = if (owningAnnotatingRelationship == null) {
            ownedAnnotatingRelationship
        } else {
            buildList {
                add(owningAnnotatingRelationship!!)
                addAll(ownedAnnotatingRelationship)
            }
        }

    /**
     * The ownedRelationships of this AnnotatingElement that are Annotations, for which this
     * AnnotatingElement is the annotatingElement.
     * 
     * `/ownedAnnotatingRelationship : Annotation [0..*] {subsets annotation, ownedRelationship, ordered}`
     * 
     * Constraint:
     * - deriveAnnotatingElementOwnedAnnotatingRelationship
     *     The ownedAnnotatingRelationships of an AnnotatingElement are its ownedRelationships that are
     *     Annotations, for which the AnnotatingElement is not the annotatedElement.
     *     ownedAnnotatingRelationship = ownedRelationship->
     *         selectByKind(Annotation)->
     *         select(a | a.annotatedElement <> self)
     */
    val ownedAnnotatingRelationship: List<Annotation>
        get() = ownedRelationship
            .filterIsInstance<Annotation>()
            .filter { it.annotatedElement !== this }

    /**
     * The owningRelationship of this AnnotatingRelationship, if it is an Annotation
     * 
     * /owningAnnotatingRelationship : Annotation [0..1] {subsets owningRelationship, annotation}
     */
    val owningAnnotatingRelationship: Annotation?
        get() = owningRelationship as? Annotation
}
