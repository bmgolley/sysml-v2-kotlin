import sandbox.kerml.root.Namespace
import sandbox.sysml.system.AttributeDefinition
import sandbox.sysml.system.AttributeUsage
import sandbox.sysml.system.PartDefinition
import sandbox.sysml.system.PartUsage

@ElementMarker
interface ElementBuilder<T : Element> {
    fun build(): T
}

interface NamespaceBuilder<T : Namespace> : ElementBuilder<T> {
    fun itemDef(
        name: String? = null,
        shortName: String? = null,
        visibility: VisibilityKind = VisibilityKind.PUBLIC,
        builder: ItemDefinitionBuilder.() -> Unit,
    ): ItemDefinition = ItemDefinitionBuilder(name, shortName, visibility).also { owner = this }.apply(builder)
    
    fun item(
        name: String? = null,
        shortName: String? = null,
        visibility: VisibilityKind = VisibilityKind.PUBLIC,
        builder: ItemUsageBuilder.() -> Unit,
    ): ItemUsage = ItemUsageBuilder(name, shortName, visibility).also { owner = this }.apply(builder)
    
    fun partDef(
        name: String? = null,
        shortName: String? = null,
        visibility: VisibilityKind = VisibilityKind.PUBLIC,
        builder: PartDefinitionBuilder.() -> Unit,
    ): PartDefinition = PartDefinitionBuilder(name, shortName, visibility).also { owner = this }.apply(builder)
    
    fun part(
        name: String? = null,
        shortName: String? = null,
        visibility: VisibilityKind = VisibilityKind.PUBLIC,
        builder: PartUsageBuilder.() -> Unit,
    ): PartUsage = PartUsageBuilder(name, shortName, visibility).also { owner = this }.apply(builder)
    
    fun attributeDef(
        name: String? = null,
        shortName: String? = null,
        visibility: VisibilityKind = VisibilityKind.PUBLIC,
        builder: AttributeDefinitionBuilder.() -> Unit,
    ): AttributeDefinition = AttributeDefinitionBuilder(name, shortName, visibility).also { owner = this }.apply(builder)
    
    fun attribute(
        name: String? = null,
        shortName: String? = null,
        value: Any? = null,
        visibility: VisibilityKind = VisibilityKind.PUBLIC,
        builder: AttributeUsageBuilder.() -> Unit,
    ): AttributeUsage = AttributeUsageBuilder(name, shortName, visibility).also { owner = this }.apply(builder)
}

interface TypeBuilder<T : Type> : NamespaceBuilder<T>
open class FeatureBuilder : TypeBuilder<Feature> {
    override fun build(): Feature
}

class ItemUsageBuilder : FeatureBuilder() {
    override fun build(): ItemUsage
}

infix fun <T : TypeBuilder> T.specializes(general: Type): T
fun <T : Type> T.specializes(
    general: Type,
    vararg additionalGeneral: Type,
): T
inline fun <T : TypeBuilder> T.specializes(
    general: Type,
    vararg additionalGeneral: Type,
    builder: T.() -> Unit,
): T

infix fun <T : TypeBuilder> T.specializes(generalName: String): T
fun <T : TypeBuilder> T.specializes(
    generalName: String,
    vararg additionalGeneralNames: String,
): T

inline fun <T : TypeBuilder> T.specializes(
    generalName: String,
    vararg additionalGeneralNames: String,
    builder: T.() -> Unit,
): T


infix fun <T : FeatureBuilder> T.definedBy(definingType: Type): T
fun <T : FeatureBuilder> T.definedBy(
    definingType: Type,
    vararg additionalTypes: Type,
): T

inline fun <T : FeatureBuilder> T.definedBy(
    definingType: Type,
    vararg additionalTypes: Type,
    builder: T.() -> Unit,
): T

infix fun <T : FeatureBuilder> T.definedBy(definingTypeName: String): T
fun <T : FeatureBuilder> T.redefines(
    definingTypeName: String,
    vararg additionalTypeNames: String,
): T

inline fun <T : FeatureBuilder> T.definedBy(
    definingTypeName: String,
    vararg additionalTypeNames: String,
    builder: T.() -> Unit,
): T

infix fun <T : FeatureBuilder> T.redefines(redefinedFeature: Feature): T
fun <T : FeatureBuilder> T.redefines(
    redefinedFeature: Feature,
    vararg additionalFeatures: Feature,
): T

inline fun <T : FeatureBuilder> T.redefines(
    redefinedFeature: Feature,
    vararg additionalFeatures: Feature,
    builder: T.() -> Unit,
): T

infix fun <T : FeatureBuilder> T.redefines(redefinedFeatureName: String): T
fun <T : FeatureBuilder> T.redefines(
    redefinedFeatureName: String,
    vararg additionalFeatureNames: String,
): T

inline fun <T : FeatureBuilder> T.redefines(
    redefinedFeatureName: String,
    vararg additionalFeatureNames: String,
    builder: T.() -> Unit,
): T

infix fun <T : FeatureBuilder> T.subsets(subsettedFeature: Feature): T
fun <T : FeatureBuilder> T.subsets(
    subsettedFeature: Feature,
    vararg additionalFeatures: Feature,
): T
inline fun <T : FeatureBuilder> T.subsets(
    subsettedFeature: Feature,
    vararg additionalFeatures: Feature,
    builder: T.() -> Unit,
): T

infix fun <T : FeatureBuilder> T.subsets(subsettedFeatureName: String): T
fun <T : FeatureBuilder> T.subsets(
    subsettedFeatureName: String,
    vararg additionalFeatureNames: String,
): T

inline fun <T : FeatureBuilder> T.subsets(
    subsettedFeatureName: String,
    vararg additionalFeatureNames: Feature,
    builder: T.() -> Unit,
): T

@DslMarker
annotation class ElementMarker

fun example() {
    ElementBuilder(rootNamespace) {
        val carDef = partDef("Car") {
            attribute("maxSpeed").defaultValue = 42
            part("wheel")
        }
        
        val myCar = part("myCar").definedBy(carDef) {
            attribute().redefines("maxSpeed").defaultValue = 6
        }
        
        val myOtherCar = part("myOtherCar") specializes myCar
    }.build()
}
