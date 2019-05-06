package oasis

import com.liferay.journal.model.JournalArticle
import com.liferay.journal.model.JournalFolder
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

    registry register {
        Tomato::class relatesTo Apple::class by { tomato -> single { tomato.toApple() } }
    }

    val apple = Apple("test")

    apple.related().forEach { println(it) }

    val tomato = Tomato(2)

    tomato.related().forEach { println(it) }
}

class Apple(val type: String) {

    public fun toTomato() : Tomato = Tomato(1)

    public fun manyTomatoes(): Collection<Tomato> = listOf(Tomato(1), Tomato(2))

    override fun toString() = "Apple $type"

}

class Tomato(val size: Int) {

    public fun toApple() : Apple = Apple("tom")

    override fun toString() = "Tomato $size"

}