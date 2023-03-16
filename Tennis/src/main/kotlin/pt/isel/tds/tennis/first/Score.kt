package pt.isel.tds.tennis.first

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.first.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), GAME(45);
    fun next(): Points = values()[ordinal+1]
}

/**
 * This is a first attempt to implement the tennis scoring system.
 *
 * The type Score is immutable and represents the current score of the game.
 *
 * Each instance store the points of each player (A,B).
 *
 * Deuce -> (FORTY,FORTY); Advantage -> (GAME,FORTY) or (FORTY,GAME).
 *
 * Game -> (GAME,*) or (*,GAME) when the other player has less than FORTY points.
 *
 * This solution is not object-oriented and does not use polymorphism.
 *
 * This solution permits representation of invalid scores: e.g. (GAME,GAME).
 */
class Score(private val pointsA: Points, private val pointsB: Points) {
    val placard: String get() = when {
        pointsA==FORTY && pointsB==FORTY -> "Deuce"
        pointsA==GAME && pointsB==FORTY -> "Advantage A"
        pointsB==GAME && pointsA==FORTY -> "Advantage B"
        pointsA==GAME && pointsB!=FORTY -> "Game A"
        pointsB==GAME && pointsA!=FORTY -> "Game B"
        else -> "${pointsA.value} - ${pointsB.value}"
    }

    val isGame: Boolean = pointsA==GAME && pointsB!=FORTY
                       || pointsB==GAME && pointsA!=FORTY

    /**
     * Returns a new Score instance with the score updated according to the winner of the last point.
     */
    fun next(winner: Player): Score = when {
        // Advantage A to Game A
        winner==Player.A && pointsA==GAME && pointsB==FORTY -> Score(GAME,LOVE)
        // Advantage B to Game B
        winner==Player.B && pointsB==GAME && pointsA==FORTY -> Score(LOVE,GAME)
        // Advantage to Deuce
        winner==Player.B && pointsA==GAME && pointsB==FORTY ||
        winner==Player.A && pointsB==GAME && pointsA==FORTY -> Score(FORTY,FORTY)
        // Game over -> error
        isGame -> error("Game over")
        // Other cases
        else ->
            if (winner==Player.A) Score( pointsA.next(), pointsB )
            else Score( pointsA, pointsB.next())
    }
}

val InitialScore = Score(LOVE,LOVE)