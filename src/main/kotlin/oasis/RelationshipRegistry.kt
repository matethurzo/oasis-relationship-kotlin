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

                if (outgoingRelationships.containsKey(from)) {
                    outgoingRelationships[from].let { map -> map?.putIfAbsent(relationship.key, relationship) }
                } else {
                    outgoingRelationships.put(from, mutableMapOf(Pair(relationship.key, relationship)))
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T: Any> getRelationships(from: KClass<T>): Set<Relationship<T, Any>> {
            with (instance) {
                val modelRels =
                        if (outgoingRelationships.contains(from)) {
                            outgoingRelationships[from]
                        } else {
                            emptyMap<String, Relationship<*, *>>()
                        }

                return modelRels!!.values.toSet() as Set<Relationship<T, Any>>
            }
        }
    }

    val outgoingRelationships = mutableMapOf<KClass<*>, MutableMap<String, Relationship<*, *>>>()
    val incomingRelationships = mutableMapOf<KClass<*>, MutableMap<String, Relationship<*, *>>>()

}