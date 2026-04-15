@file:Suppress("unused")

package sandbox.util

import sandbox.kerml.root.elements.Element
import kotlin.collections.associateWith

interface Validator<T : Element> {
    val rules: List<ValidationRule<T>>

    fun T.validate(): Map<ValidationRule<T>, Boolean> = rules.associateWith { it(this) }

    fun validate(element: T) = element.validate()

    typealias ValidationRule<E> = E.() -> Boolean
}
