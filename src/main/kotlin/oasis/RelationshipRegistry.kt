package oasis

import kotlin.reflect.KClass

class RelationshipRegistry private constructor() {

    companion object {

        private val instance: RelationshipRegistry = RelationshipRegistry()

        @Suppress("IMPLICIT_CAST_TO_ANY")
        infix fun <T: Any, U: Any> register(relSupplier: () -> Relationship<T, U>) {
            val relationship = relSupplier.invoke()

            with (instance) {
                val (from) = relationship

                if (relationships.containsKey(from)) {
                    relationships[from].let { map -> map?.putIfAbsent(relationship.key, relationship) }
                } else {
                    relationships.put(from, mutableMapOf(Pair(relationship.key, relationship)))
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T: Any> getRelationship(from: KClass<T>): Set<Relationship<T, *>>? {
            with (instance) {
                return relationships[from] as Set<Relationship<T, *>>
            }
        }
    }

    val relationships = mutableMapOf<KClass<*>, MutableMap<String, Relationship<*, *>>>()

}