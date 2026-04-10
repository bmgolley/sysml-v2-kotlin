package sandbox.kerml.core.types

/**
 * FeatureDirectionKind enumerates the possible kinds of direction that a Feature may be given as a member
 * of a Type.
 */
enum class FeatureDirectionKind(val text: String) {
    /**
     * Values of the Feature on each instance of its domain are determined externally to that instance and used internally.
     */
    IN("in"),
    
    /**
     * Values of the Feature on each instance are determined either as in or out directions, or both.
     */
    INOUT("inout"),
    
    /**
     * Values of the Feature on each instance of its domain are determined internally to that instance and used externally.
     */
    OUT("out"),
}
