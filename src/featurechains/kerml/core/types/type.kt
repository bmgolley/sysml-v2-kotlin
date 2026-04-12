@file:Suppress("unused")

package sandbox.featurechains.kerml.core.types

import sandbox.kerml.core.features.Feature
import sandbox.kerml.core.types.FeatureDirectionKind
import sandbox.kerml.core.types.Multiplicity
import sandbox.kerml.core.types.Type
import sandbox.kerml.root.namespaces.Membership
import sandbox.kerml.root.namespaces.Namespace

/** @see Type.multiplicity */
val Iterable<Type>.multiplicity: List<Multiplicity?>
    get() = map(Type::multiplicity)

/** @see Type.directionOfExcluding */
fun Iterable<Type>.directionOfExcluding(feature: Feature, excluded: Collection<Type>): List<FeatureDirectionKind?> =
    map { it.directionOfExcluding(feature, excluded) }

/** @see Type.nonPrivateMemberships */
fun Iterable<Type>.nonPrivateMemberships(
    excludedNamespaces: Collection<Namespace> = emptySet(),
    excludedTypes: Collection<Type> = emptySet(),
    excludeImplied: Boolean = false
): List<Membership> = flatMap { it.nonPrivateMemberships(excludedNamespaces, excludedTypes, excludeImplied) }
