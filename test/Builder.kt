import sandbox.kerml.root.namespaces.Namespace

fun Namespace.itemDef(
    name: String? = null,
    shortName: String? = null,
    visibility: VisibilityKind = VisibilityKind.PUBLIC,
    builder: Builder<ItemDefinition>,
): ItemDefinition = ItemDefinition(name, shortName, visibility).also { owner = this }

fun Namespace.item(
    name: String? = null,
    shortName: String? = null,
    visibility: VisibilityKind = VisibilityKind.PUBLIC,
    builder: Builder<ItemUsage>,
): ItemUsage = ItemUsage(name, shortName, visibility).also { owner = this }

fun Namespace.partDef(
    name: String? = null,
    shortName: String? = null,
    visibility: VisibilityKind = VisibilityKind.PUBLIC,
    builder: Builder<PartDefinition>,
): PartDefinition = PartDefinition(name, shortName, visibility).also { owner = this }

fun Namespace.part(
    name: String? = null,
    shortName: String? = null,
    visibility: VisibilityKind = VisibilityKind.PUBLIC,
    builder: Builder<ItemUsage>,
): PartUsage = PartUsage(name, shortName, visibility).also { owner = this }

fun Namespace.attributeDef(
    name: String? = null,
    shortName: String? = null,
    visibility: VisibilityKind = VisibilityKind.PUBLIC,
    builder: Builder<AttributeDefinition>,
): AttributeDefinition = AttributeDefinition(name, shortName, visibility).also { owner = this }

fun Namespace.attribute(
    name: String? = null,
    shortName: String? = null,
    value: Any? = null,
    visibility: VisibilityKind = VisibilityKind.PUBLIC,
    builder: Builder<AttributeUsage>,
): AttributeUsage = AttributeUsage(name, shortName, visibility).also { owner = this }


infix fun <T : Type> T.specializes(generalName: String): T
fun <T : Feature> T.specializes(
    generalName: String,
    vararg additionalGeneralNames: String,
): T

inline fun <T : Type> T.specializes(
    generalName: String,
    vararg additionalGeneralNames: String,
    builder: Builder<T>,
): T


infix fun <T : Feature> T.definedBy(definingType: Type): T
fun <T : Feature> T.definedBy(
    definingType: Type,
    vararg additionalTypes: Type,
): T

inline fun <T : Feature> T.definedBy(
    definingType: Type,
    vararg additionalTypes: Type,
    builder: Builder<T>,
): T

infix fun <T : Feature> T.definedBy(definingTypeName: String): T
fun <T : Feature> T.redefines(
    definingTypeName: String,
    vararg additionalTypeNames: String,
): T

inline fun <T : Feature> T.definedBy(
    definingTypeName: String,
    vararg additionalTypeNames: String,
    builder: Builder<T>,
): T

infix fun <T : Feature> T.redefines(redefinedFeature: Feature): T
fun <T : Feature> T.redefines(
    redefinedFeature: Feature,
    vararg additionalFeatures: Feature,
): T

inline fun <T : Feature> T.redefines(
    redefinedFeature: Feature,
    vararg additionalFeatures: Feature,
    builder: Builder<T>,
): T

infix fun <T : Feature> T.redefines(redefinedFeatureName: String): T
fun <T : Feature> T.redefines(
    redefinedFeatureName: String,
    vararg additionalFeatureNames: String,
): T

inline fun <T : Feature> T.redefines(
    redefinedFeatureName: String,
    vararg additionalFeatureNames: String,
    builder: Builder<T>,
): T

infix fun <T : Feature> T.subsets(subsettedFeature: Feature): T
fun <T : Feature> T.subsets(
    subsettedFeature: Feature,
    vararg additionalFeatures: Feature,
): T
inline fun <T : Feature> T.subsets(
    subsettedFeature: Feature,
    vararg additionalFeatures: Feature,
    builder: Builder<T>,
): T

infix fun <T : Feature> T.subsets(subsettedFeatureName: String): T
fun <T : Feature> T.subsets(
    subsettedFeatureName: String,
    vararg additionalFeatureNames: String,
): T

inline fun <T : Feature> T.subsets(
    subsettedFeatureName: String,
    vararg additionalFeatureNames: Feature,
    builder: Builder<T>,
): T

infix fun <T : Type> T.specializes(general: Type): T
fun <T : Type> T.specializes(
    general: Type,
    vararg additionalGeneral: Type,
): T
inline fun <T : Type> T.specializes(
    general: Type,
    vararg additionalGeneral: Type,
    builder: Builder<T>,
): T

@DslMarker
annotation class ElementMarker

typealias Builder<T> = @ElementMarker T.() -> Unit

fun example() {
    with(rootNamespace) {
        val carDef = partDef("Car") {
            attribute("maxSpeed").defaultValue = 42
            part("wheel")
        }
        
        val myCar = part("myCar").definedBy(carDef) {
            attribute().redefines("maxSpeed").defaultValue = 6
        }
        
        val myOtherCar = part("myOtherCar") specializes myCar
    }
}
