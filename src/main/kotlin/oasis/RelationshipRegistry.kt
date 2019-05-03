package oasis

class RelationshipRegistry private constructor() {

    companion object {

        private val instance: RelationshipRegistry = RelationshipRegistry()

        infix fun <T, U> register(relSupplier: () -> Relationship<T, U>): Unit {
            val relationship = relSupplier.invoke()

            with (instance) {
                relationships.putIfAbsent(relationship.key, relationship)
            }
        }

        infix fun <T, U> registerJava(relSupplier: () -> JavaRelationship<T, U>): Unit {
            val relationship = relSupplier.invoke()

            with (instance) {
                javaRelationships.putIfAbsent(relationship.key, relationship)
            }
        }
    }

    val relationships = mutableMapOf<String, Relationship<*, *>>()
    val javaRelationships = mutableMapOf<String, JavaRelationship<*, *>>()

}