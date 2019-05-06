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
        Apple::class relatesTo Tomato::class by { apple -> single { apple.toTomato() } }
    }

    val appleRel = Apple::class relatesTo Tomato::class by { apple -> single { apple.toTomato() } }

    val apple = Apple("test")

    apple.outgoing()
}

class Apple(val type: String) {

    public fun toTomato() : Tomato = Tomato(1)

    public fun manyTomatoes(): List<Tomato> = listOf(Tomato(1), Tomato(2))

}

data class Tomato(val size: Int)