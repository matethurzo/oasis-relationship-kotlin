package oasis

class RelationshipRegistry {

    public fun register(relationship: Relationship<*, *>): Boolean = relationships.add(relationship)

    public fun register(relationship: JavaRelationship<*, *>): Boolean = javaRelationships.add(relationship)

    val relationships = mutableSetOf<Relationship<*, *>>()
    val javaRelationships = mutableSetOf<JavaRelationship<*, *>>()

}