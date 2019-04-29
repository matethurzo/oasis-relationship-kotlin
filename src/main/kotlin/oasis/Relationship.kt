package oasis

import java.io.Serializable

data class RelationshipPair<T, U>(val first: T, val second: U) : Serializable {

    override fun toString(): String = "Relationship pair between ${first} and ${second}"

}

infix fun <T, U> RelationshipPair<T, U>.by(relation: (T) -> U):  Relationship<T, U> {
    return Relationship(this, relation)
}

data class JavaRelationshipPair<T, U>(val first: Class<T>, val second: Class<U>) {

    override fun toString(): String = "Relationship pair between ${first} and ${second}"

}

infix fun <T, U> JavaRelationshipPair<T, U>.by(relation: (T) -> U):  JavaRelationship<T, U> {
    return JavaRelationship(this, relation)
}

class Relationship<T, U>(val pair: RelationshipPair<T, U>, val relation: (T) -> U) {

    override fun toString(): String {
        val (pairThis, pairThat) = pair

        return "Relationship between ${pairThis} and ${pairThat} with relation ${relation}"
    }

}

class JavaRelationship<T, U>(val pair: JavaRelationshipPair<T, U>, val relation: (T) -> U) {

    override fun toString(): String {
        val (pairThis, pairThat) = pair

        return "Relationship between ${pairThis} and ${pairThat} with relation ${relation}"
    }

}

infix fun Any.relatesTo(that: Any): RelationshipPair<Any, Any> {
    return RelationshipPair(this, that)
}

infix fun <T, U> java.lang.Class<T>.relatesTo(that: java.lang.Class<U>): JavaRelationshipPair<T, U> = JavaRelationshipPair(this, that)