package oasis

import kotlin.reflect.KClass

/**
 * The infix function to start building a relationship between models
 */
infix fun <T: Any, U: Any> KClass<T>.relatesTo(that: KClass<U>): RelationshipPair<T, U> = RelationshipPair(this, that)

/**
 * Data class storing a relationship pair between two model types
 */
data class RelationshipPair<T: Any, U: Any>(val first: KClass<T>, val second: KClass<U>)

/**
 * The infix function that defines the relationship between an already defined pair of model types
 */
infix fun <T: Any, U: Any> RelationshipPair<T, U>.by(relation: (T) -> Collection<U>): Relationship<T, U> = Relationship(this, relation)

/**
 * The function single can be used in relation definitions where the relation returns only a single value. Eg.: apple -> single { apple.tree }
 */
inline fun <T> single(value: () -> T): Collection<T> = listOf<T>(value.invoke())

/**
 * Relationship class representing a relationship. The relationship is described by a type pair and a relation function
 */
class Relationship<T: Any, U: Any>(val pair: RelationshipPair<T, U>, val relation: (T) -> Collection<U>) {

    operator fun component1() = pair.first

    operator fun component2() = pair.second

    operator fun component3(): (T) -> Collection<U> = relation

    override fun toString(): String {
        val (pairThis, pairThat) = pair

        return "Relationship between $pairThis and $pairThat with relation $relation"
    }

    val key: String
        get() = "${pair.first}#${pair.second}"

}

@Suppress("UNCHECKED_CAST")
fun <T: Any> T.outgoing(): Collection<Any> {
    return RelationshipRegistry.getRelationships(this::class)
            .map { it as Relationship<T, Any> }
            .map { (_, _, relation) -> relation.invoke(this) }
            .toSet()
}

fun <T: Any> T.incoming(): Collection<Any> {
    RelationshipRegistry.getRelationships(this::class)

    return emptyList()
}

fun <T: Any> T.both(): Collection<Any> {
    RelationshipRegistry.getRelationships(this::class)

    return emptyList()
}