@file:Suppress("unused")

package sandbox.kerml.root

/**
 * A Comment is an AnnotatingElement whose body in some way describes its annotatedElements.
 */
interface Comment : AnnotatingElement {
    /**
     * The annotation text for the Comment.
     * 
     * body : String
     */
    var body: String

    /**
     * Identification of the language of the body text and, optionally, the region and/or encoding. The format shall be a
     * POSIX locale conformant to ISO/IEC 15897, with the format
     * \[language\[_territory]\[.codeset]\[@modifier]].
     * 
     * locale : String [0..1]
     */
    var locale: String?
}
