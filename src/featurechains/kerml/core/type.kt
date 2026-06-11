@file:Suppress("unused")

package sandbox.featurechains.kerml.core

import sandbox.kerml.core.Feature
import sandbox.kerml.core.FeatureDirectionKind
import sandbox.kerml.core.Multiplicity
import sandbox.kerml.core.Type
import sandbox.kerml.root.Membership
import sandbox.kerml.root.Namespace

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
