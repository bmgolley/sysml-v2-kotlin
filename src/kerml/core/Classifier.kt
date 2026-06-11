@file:Suppress("unused")

package sandbox.kerml.core

import sandbox.util.Validator

/**
 * A `Classifier` is a [Type] that classifies:
 *
 * - Things (in the universe) regardless of how [Features][Feature] relate them. (These are
 *   interpreted semantically as sequences of exactly one thing.)
 * - How the above things are related by [Features][Feature]. (These are interpreted
 *   semantically as sequences of multiple things, such that the last thing in the sequence is also classified by the
 *   `Classifier`. Note that this means that a `Classifier` modeled as specializing a
 *   [Feature][Feature] cannot classify anything.)
 */
interface Classifier : Type {
    /**
     * The [ownedSpecializations][ownedSpecialization] of this [Classifier] that are
     * [Subclassifications][Subclassification], for which this [Classifier]
     * is the [subclassifier][Subclassification.subclassifier].
     *
     * ```ocl
     * /ownedSubclassification : Subclassification [0..*] {subsets ownedSpecialization}
     *
     * ownedSubclassification = ownedSpecialization->selectByKind(Subclassification)
     * ```
     */
    val ownedSubclassification: List<Subclassification>
        get() = ownedSpecialization.filterIsInstance<Subclassification>()

    object Validation : Validator<Classifier> {
        override val rules: List<Validator.ValidationRule<Classifier>> = buildList {
            addAll(Type.Validation.rules)
            add(::validateClassifierMultiplicityDomain)
        }

        /**
         * If a `Classifier` has a multiplicity, then the multiplicity must have no featuringTypes (meaning that
         * its domain is implicitly Base::Anything).
         *
         * ```ocl
         * multiplicity <> null implies multiplicity.featuringType->isEmpty()
         * ```
         */
        fun validateClassifierMultiplicityDomain(classifier: Classifier): Boolean = with(classifier) {
            multiplicity?.featuringType?.isEmpty() ?: true
        }
    }
}