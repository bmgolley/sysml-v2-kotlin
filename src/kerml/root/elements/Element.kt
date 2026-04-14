@file:Suppress("unused")

package sandbox.kerml.root.elements

import sandbox.kerml.root.annotations.Annotation
import sandbox.kerml.root.annotations.Documentation
import sandbox.kerml.root.annotations.TextualRepresentation
import sandbox.kerml.root.namespaces.Namespace
import sandbox.kerml.root.namespaces.OwningMembership

/**
 * An Element is a constituent of a model that is uniquely identified relative to all other Elements. It can have
 * Relationships with other Elements. Some of these Relationships might imply ownership of other
 * Elements, which means that if an Element is deleted from a model, then so are all the Elements that it owns.
 */
interface Element {
    /**
     * Various alternative identifiers for this Element. Generally, these will be set by tools.
     * 
     * aliasIds : String [0..*] {ordered}
     */
    val aliasIds: List<String>

    /**
     * The declared name of this Element.
     * 
     * declaredName : String [0..1]
     */
    var declaredName: String?

    /**
     * An optional alternative name for the Element that is intended to be shorter or in some way more succinct than its
     * primary name. It may act as a modeler-specified identifier for the Element, though it is then the responsibility of
     * the modeler to maintain the uniqueness of this identifier within a model or relative to some other context.
     * 
     * declaredShortName : String [0..1]
     */
    var declaredShortName: String?

    /**
     * The [Documentation] owned by this Element.
     * 
     * /documentation : Documentation [0..*] {subsets ownedElement, annotatingElement, ordered}
     * 
     * Constraints
     * - deriveElementDocumentation
     *     The documentation of an Element is its ownedElements that are Documentation.
     *     ```ocl
     *     documentation = ownedElement->selectByKind(Documentation)
     *     ```
     */
    val documentation: List<Documentation>
        get() = ownedElement.filterIsInstance<Documentation>()

    /**
     * The globally unique identifier for this Element. This is intended to be set by tooling, and it must not change during
     * the lifetime of the Element.
     * 
     * elementId : String
     */
    val elementId: String

    /**
     * Whether all necessary implied Relationships have been included in the ownedRelationships of this Element.
     * This property may be true, even if there are not actually any ownedRelationships with isImplied = true,
     * meaning that no such Relationships are actually implied for this Element. However, if it is false, then
     * ownedRelationships may not contain any implied Relationships. That is, either all required implied
     * Relationships must be included, or none of them.
     * 
     * isImpliedIncluded : Boolean
     */
    val isImpliedIncluded: Boolean

    /**
     * Whether this [Element] is contained in the ownership tree of a library model.
     * 
     * /isLibraryElement : Boolean
     * 
     * Constraints:
     * - deriveElementIsLibraryElement
     *     An Element isLibraryElement if libraryNamespace() is not null.
     *     ```ocl
     *     isLibraryElement = libraryNamespace() <> null
     *     ```
     */
    val isLibraryElement: Boolean
        get() = libraryNamespace() != null

    /**
     * The name to be used for this Element during name resolution within its owningNamespace. This is derived using
     * the effectiveName() operation. By default, it is the same as the declaredName, but this is overridden for certain
     * kinds of Elements to compute a name even when the declaredName is null.
     * 
     * /name : String [0..1]
     * 
     * Constraints:
     * - deriveElementName
     *     The name of an Element is given by the result of the effectiveName() operation.
     *     ```ocl
     *     name = effectiveName()
     *     ```
     */
    val name: String?
        get() = effectiveName()

    /**
     * The ownedRelationships of this Element that are Annotations, for which this Element is the
     * annotatedElement.
     * 
     * /ownedAnnotation : Annotation [0..*] {subsets ownedRelationship, annotation, ordered}
     * 
     * Constraints:
     * - deriveElementOwnedAnnotation
     *     The ownedAnnotations of an Element are its ownedRelationships that are Annotations, for which the
     *     Element is the annotatedElement.
     *     ```ocl
     *     ownedAnnotation = ownedRelationship->
     *         selectByKind(Annotation)->
     *         select(a | a.annotatedElement = self)
     *     ```
     */
    val ownedAnnotation: List<Annotation>
        get() = ownedRelationship.filterIsInstance<Annotation>().filter { it.annotatedElement === this }

    /**
     * The Elements owned by this Element, derived as the ownedRelatedElements of the
     * ownedRelationships of this Element.
     * 
     * /ownedElement : Element [0..*] {ordered}
     * 
     * Constraints:
     * - deriveElementOwnedElement
     *     The ownedElements of an Element are the ownedRelatedElements of its ownedRelationships.
     *     ```ocl
     *     ownedElement = ownedRelationship.ownedRelatedElement
     *     ```
     */
    val ownedElement: List<Element>
        get() = ownedRelationship.flatMap(Relationship::ownedRelatedElement)

    /**
     * The Relationships for which this Element is the owningRelatedElement.
     * 
     * ownedRelationship : Relationship [0..*] {subsets relationship, ordered}
     */
    val ownedRelationship: List<Relationship>

    /**
     * The owner of this Element, derived as the owningRelatedElement of the owningRelationship of this
     * Element, if any.
     * 
     * /owner : Element [0..1]
     * 
     * Constraints:
     * - deriveElementOwner
     *     The owner of an Element is the owningRelatedElement of its owningRelationship.
     *     ```ocl
     *     owner = owningRelationship.owningRelatedElement
     *     ```
     */
    val owner: Element?
        get() = owningRelationship?.owningRelatedElement

    /**
     * The owningRelationship of this Element, if that Relationship is a Membership.
     * 
     * /owningMembership : OwningMembership [0..1] {subsets owningRelationship, membership}
     */
    val owningMembership: OwningMembership?
        get() = owningRelationship as? OwningMembership

    /**
     * The Namespace that owns this Element, which is the membershipOwningNamespace of the
     * owningMembership of this Element, if any.
     * 
     * /owningNamespace : Namespace [0..1] {subsets namespace}
     * 
     * Constraints:
     * - deriveOwningNamespace
     *     The owningNamespace of an Element is the membershipOwningNamspace of its owningMembership (if any).
     *     ```ocl
     *     owningNamespace =
     *         if owningMembership = null then null
     *         else owningMembership.membershipOwningNamespace
     *         endif
     *     ```
     */
    val owningNamespace: Namespace?
        get() = owningMembership?.membershipOwningNamespace

    /**
     * The Relationship for which this Element is an ownedRelatedElement, if any.
     * 
     * owningRelationship : Relationship [0..1] {subsets relationship}
     */
    var owningRelationship: Relationship?

    /**
     * The full ownership-qualified name of this `Element`, represented in a form that is valid according to the KerML
     * textual concrete syntax for qualified names (including use of unrestricted name notation and escaped characters,
     * as necessary). The `qualifiedName` is null if this Element has no [owningNamespace] or if there is not a complete
     * ownership chain of named [Namespaces][Namespace] from a root [Namespace] to this `Element`. If the
     * [owningNamespace] has other `Elements` with the same name as this one, then the `qualifiedName` is null for all
     * such `Elements` other than the first.
     * 
     * `/qualifiedName : String [0..1]`
     * 
     * Constraints:
     * - deriveElementQualifiedName
     *      If this Element does not have an owningNamespace, then its qualifiedName is null. If the owningNamespace
     *      of this Element is a root Namespace, then the qualifiedName of the Element is the escaped name of the
     *      Element (if any). If the owningNamespace is non-null but not a root Namespace, then the qualifiedName of
     *      this Element is constructed from the qualifiedName of the owningNamespace and the escaped name of the
     *      Element, unless the qualifiedName of the owningNamespace is null or the escaped name is null, in which case
     *      the qualifiedName of this Element is also null. Further, if the owningNamespace has other ownedMembers
     *      with the same non-null name as this Element, and this Element is not the first, then the qualifiedName of this
     *      Element is null.
     *      ```ocl
     *      qualifiedName =
     *            if owningNamespace = null then null
     *         else if name <> null and
     *               owningNamespace.ownedMember->
     *             select(m | m.name = name).indexOf(self) <> 1 then null
     *          else if owningNamespace.owner = null then escapedName()
     *            else if owningNamespace.qualifiedName = null or
     *              escapedName() = null then null
     *          else owningNamespace.qualifiedName + '::' + escapedName()
     *          endif endif endif endif
     *         ```
     */
    val qualifiedName: String?
        get() = owningNamespace?.let { owningNamespace ->
            when {
                name != null && owningNamespace.ownedMember.firstOrNull { it.name == name } !== this -> null
                owningNamespace.owner == null -> escapedName()
                owningNamespace.qualifiedName == null -> null
                else -> escapedName()?.let { escapedName ->
                    "${owningNamespace.qualifiedName}::$escapedName"
                }
            }
        }

    /**
     * The short name to be used for this Element during name resolution within its [owningNamespace]. This is derived
     * using the [effectiveShortName] operation. By default, it is the same as the [declaredShortName], but this is
     * overridden for certain kinds of `Elements` to compute a `shortName` even when the [declaredName] is null.
     * 
     * `/shortName : String [0..1]`
     * 
     * Constraints:
     * - deriveElementShortName
     *     The shortName of an Element is given by the result of the effectiveShortName() operation.
     *     ```ocl
     *     shortName = effectiveShortName()
     *     ```
     */
    val shortName: String?
        get() = effectiveShortName()

    /**
     * The [TextualRepresentations][TextualRepresentation] that annotate this `Element`.
     * 
     * `/textualRepresentation : TextualRepresentation [0..*] {subsets ownedElement, annotatingElement, ordered}`
     * 
     * Constraints:
     * - deriveElementTextualRepresentation
     *     The textualRepresentations of an Element are its ownedElements that are TextualRepresentations.
     *     ```ocl
     *     textualRepresentation = ownedElement->selectByKind(TextualRepresentation)
     *     ```
     */
    val textualRepresentation: List<TextualRepresentation>
        get() = ownedElement.filterIsInstance<TextualRepresentation>()

    /**
     * Return an effective [name] for this `Element`. By default this is the same as its [declaredName].
     * 
     * `effectiveName() : String [0..1]`
     * ```ocl
     * body: declaredName
     * ```
     */
    fun effectiveName(): String? = declaredName

    /**
     * Return an effective [shortName] for this Element. By default this is the same as its [declaredShortName].
     * 
     * effectiveShortName() : String [0..1]
     * ```ocl
     * body: declaredShortName
     * ```
     */
    fun effectiveShortName(): String? = declaredShortName

    /**
     * Return [name], if that is not `null`, otherwise the [shortName], if that is not `null`, otherwise `null`. If the
     * returned value is non-null, it is returned as-is if it has the form of a basic name, or, otherwise, represented
     * as a restricted name according to the lexical structure of the KerML textual notation (i.e., surrounded by single
     * quote characters and with special characters escaped).
     * 
     * `escapedName() : String [0..1]`
     */
    fun escapedName(): String?

    /**
     * By default, return the library [Namespace] of the [owningRelationship] of this `Element`, if it has one.
     * 
     * `libraryNamespace() : Namespace [0..1]`
     * ```ocl
     * body: if owningRelationship <> null then owningRelationship.libraryNamespace()
     * else null endif
     * ```
     */
    fun libraryNamespace(): Namespace? = owningRelationship?.libraryNamespace()

    /**
     * Return a unique description of the location of this Element in the containment structure rooted in a root
     * Namespace. If the Element has a non-null qualifiedName, then return that. Otherwise, if it has an
     * owningRelationship, then return the string constructed by appending to the path of it's owningRelationship
     * the character / followed by the string representation of its position in the list of ownedRelatedElements of the
     * owningRelationship (indexed starting at 1). Otherwise, return the empty string.
     * (Note that this operation is overridden for Relationships to use owningRelatedElement when appropriate.)
     * 
     * `path() : String`
     * ```ocl
     * body: if qualifiedName <> null then qualifiedName
     * else if owningRelationship <> null then
     * owningRelationship.path() + '/' +
     * owningRelationship.ownedRelatedElement->indexOf(self).toString()
     * -- A position index shall be converted to a decimal string representation
     * -- consisting of only decimal digits, with no sign, leading zeros or leading
     * -- or trailing whitespace.
     * else ''
     * endif endif
     * ```
     */
    fun path(): String = qualifiedName
        ?: owningRelationship?.let {
            "${it.path()}/${it.ownedRelatedElement.indexOf(this)}"
        }.orEmpty()
    
    object Validation : Validator<Element> {
        override fun Element.validate() = mapOf(
            this@Validation::validateElementIsImpliedIncluded to validateElementIsImpliedIncluded()
        )

        /**
         * If an Element has any ownedRelationships for which isImplied = true, then the Element must also have
         * isImpliedIncluded = true. (Note that an Element can have isImplied = true even if no
         * ownedRelationships have isImplied = true, indicating the Element simply has no implied
         * Relationships.
         * ```ocl
         * ownedRelationship->exists(isImplied) implies isImpliedIncluded
         * ```
         */
        fun Element.validateElementIsImpliedIncluded() = ownedRelationship.any(Relationship::isImplied) implies isImpliedIncluded
    }
}
/*
val Element.relationship: Collection<Relationship>
    get() = sourceRelationship.toList() + targetRelationship

val sourceRelationship: Set<Relationship>
    get()
    
val targetRelationship: Set<Relationship>
    get()
    */
