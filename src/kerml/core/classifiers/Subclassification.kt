@file:Suppress("unused")

package sandbox.kerml.core.classifiers

import sandbox.kerml.core.types.Specialization

/**
 * `Subclassification` is [Specialization] in which both the specific and general [Types][sandbox.kerml.core.types.Type]
 * are `Classifier`. This means all instances of the specific `Classifier` are also instances of the general
 * `Classifier`.
 */
interface Subclassification : Specialization {
    /**
     * The [Classifier] that owns this `Subclassification` relationship, which must also be its [subclassifier].
     */
    val owningClassifier: Classifier?

    /**
     * The more specific [Classifier] in this `Subclassification`.
     */
    var subclassifier: Classifier

    /**
     * The more general [Classifier] in this `Subclassification`.
     */
    var superclassifier: Classifier
}