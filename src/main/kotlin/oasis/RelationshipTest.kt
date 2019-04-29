package oasis

import com.liferay.journal.model.JournalArticle
import com.liferay.journal.model.JournalFolder

fun main(args: Array<String>) {
    println("RelationTest")

    RelationshipRegistry().apply {
        register(
                JournalArticle::class.java relatesTo JournalFolder::class.java by { article ->
                    article.folder
                }
        )
        register(
                JournalArticle::class.java relatesTo JournalFolder::class.java by { article ->
                    article.folder
                }
        )
    }

    //println("Created thing ${relationship}")
}

/*String relatesTo Boolean by { str ->
    when (str) {
        "true", "yes", "1" -> true
        "false", "no", "0" -> false
        else -> false
    }
}*/