package oasis

import com.liferay.journal.model.JournalArticle
import com.liferay.journal.model.JournalFolder
import java.util.LinkedHashSet
import kotlin.reflect.KClass
import oasis.RelationshipRegistry as registry

fun main(args: Array<String>) {
    println("RelationTest")

    registry register {
        JournalArticle::class relatesTo JournalFolder::class by { article -> listOf(article.folder) }
    }

    registry.apply {
        register {
            JournalArticle::class relatesTo JournalFolder::class by { article -> listOf(article.folder) }
        }
    }

    registry register {
        Apple::class relatesTo Tomato::class by { apple -> apple.manyTomatoes() }
    }

    val apple = Apple("test")

    apple.outgoing().forEach { println(it) }
}

class Apple(val type: String) {

    public fun toTomato() : Tomato = Tomato(1)

    public fun manyTomatoes(): Collection<Tomato> = listOf(Tomato(1), Tomato(2))

}

data class Tomato(val size: Int)