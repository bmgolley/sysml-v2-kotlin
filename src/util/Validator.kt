package sandbox.util

import sandbox.kerml.root.elements.Element

interface Validator<T : Element> {
    val rules: List<ValidationRule>
    // @JvmName("$validate")
    // @JvmSynthetic
    fun T.validate(): Map<ValidationRule, Boolean> = rules.associateWith {
        it(this)
    }

    // fun validate(element: T) = element.validate()
    
    typealias ValidationRule = T.() -> Boolean
}
