package oasis

import com.liferay.journal.model.JournalArticle
import com.liferay.journal.model.JournalFolder

fun main(args: Array<String>) {
    println("RelationTest")

    RelationshipRegistry registerJava {
        JournalArticle::class.java relatesTo JournalFolder::class.java by { article -> article.folder }
    }

    RelationshipRegistry.apply {
        registerJava {
            JournalArticle::class.java relatesTo JournalFolder::class.java by { article -> article.folder }
        }
    }

    RelationshipRegistry register {
        Apple::class relatesTo Tomato::class by { apple -> Tomato(1) }
    }

    //println("Created thing ${relationship}")
}

data class Apple(val type: String)

data class Tomato(val size: Int)