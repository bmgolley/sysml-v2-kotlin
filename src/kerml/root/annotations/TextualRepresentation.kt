package sandbox.kerml.root.annotations

import sandbox.kerml.root.elements.Element

/**
 * A TextualRepresentation is an AnnotatingElement whose body represents the representedElement in a
 * given language. The representedElement must be the owner of the TextualRepresentation. The named
 * language can be a natural language, in which case the body is an informal representation, or an artificial language,
 * in which case the body is expected to be a formal, machine-parsable representation.
 * 
 * If the named language of a TextualRepresentation is machine-parsable, then the body text should be legal
 * input text as defined for that language. The interpretation of the named language string shall be case insensitive.
 * The following language names are defined to correspond to the given standard languages:
 * | ---------------------------------- |
 * | kerml | Kernel Modeling Language   |
 * |  ocl  | Object Constraint Language |
 * |  alf  | Action Language for fUML   |
 * | ---------------------------------- |
 * 
 * Other specifications may define specific language strings, other than those shown above, to be used to indicate the
 * use of languages from those specifications in KerML TextualRepresentation.
 * 
 * If the language of a TextualRepresentation is "kerml", then the body text shall be a legal representation of
 * the representedElement in the KerML textual concrete syntax. A conforming tool can use such a
 * TextualRepresentation Annotation to record the original KerML concrete syntax text from which an
 * Element was parsed. In this case, it is a tool responsibility to ensure that the body of the
 * TextualRepresentation remains correct (or the Annotation is removed) if the annotated Element changes other
 * than by re-parsing the body text.
 * 
 * An Element with a TextualRepresentation in a language other than KerML is essentially a semantically
 * "opaque" Element specified in the other language. However, a conforming KerML tool may interpret such an
 * element consistently with the specification of the named language.
 */
@Suppress("unused")
interface TextualRepresentation : AnnotatingElement {
    /**
     * The textual representation of the representedElement in the given language.
     * 
     * body : String
     */
    var body: String

    /**
     * The natural or artifical language in which the body text is written.
     * 
     * language : String
     */
    var language: String

    /**
     * The Element that is represented by this TextualRepresentation.
     * 
     * /representedElement : Element {subsets owner, redefines annotatedElement}
     */
    val representedElement: Element
        get() = checkNotNull(owner)

    override val annotatedElement: List<Element>
        get() = listOf(representedElement)
}
