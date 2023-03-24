package pt.isel.tds.tennis
//import pt.isel.tds.tennis.first.*
//import pt.isel.tds.tennis.oo.*
//import pt.isel.tds.tennis.pm.*
import pt.isel.tds.tennis.fp.*

fun main() {
    var score: Score = InitialScore
    while (true) {
        println("  ${score.placard}")
        if( score.isGame ) break
        score = score.next(readWinner())
    }
}

enum class Player{ A, B;
    fun other() = if (this==A) B else A
}

val playerNames = Player.values().joinToString(" or ")

fun String.toPlayerOrNull() = Player.values().firstOrNull{ it.name==this }
fun String.toPlayer() = requireNotNull( toPlayerOrNull() )

tailrec fun readWinner(): Player {
    print("Winner $playerNames? ")
    return readln().trim().uppercase().toPlayerOrNull() ?: readWinner()
}