package oasis

import kotlin.reflect.KClass

class RelationshipRegistry private constructor() {

    companion object {

        private val instance: RelationshipRegistry = RelationshipRegistry()

        @Suppress("IMPLICIT_CAST_TO_ANY")
        infix fun <T: Any, U: Any> register(relSupplier: () -> Relationship<T, U>) {
            val relationship = relSupplier.invoke()

            with (instance) {
                relationships.putIfAbsent(relationship.key, relationship)

                val (from) = relationship

                if (relationshipMapping.containsKey(from)) {
                    relationshipMapping[from].let { set -> set?.add(relationship.key) }
                } else {
                    relationshipMapping.put(from, mutableSetOf(relationship.key))
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T: Any> getRelationships(from: KClass<T>): Set<Relationship<T, Any>> {
            with (instance) {
                val mappings =
                        if (relationshipMapping.contains(from)) {
                            relationshipMapping[from]
                        } else {
                            emptySet<String>()
                        }

                return mappings!!.map { relationships[it] }.toSet() as Set<Relationship<T, Any>>
            }
        }
    }

    val relationships = mutableMapOf<String, Relationship<*, *>>()
    val relationshipMapping = mutableMapOf<KClass<*>, MutableSet<String>>()

}