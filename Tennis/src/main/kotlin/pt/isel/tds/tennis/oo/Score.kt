package pt.isel.tds.tennis.oo

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.oo.Points.*

private enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40); //, GAME(45);
    fun next(): Points = values()[ordinal+1]
}

/**
 * Base class for all scores representations.
 * Access to next function and isGame property is polymorphic.
 * This solution Makes Illegal States Unrepresentable (MISU).
 * @property placard the string representation of the score
 * @property isGame true if the score represents a game
 * @property next the function that computes the next score given the winner
 *
 */
abstract class Score(val placard: String) {
    open val isGame: Boolean = false
    abstract fun next(winner: Player): Score
}

// MISU - ByPoints(FORTY,FORTY) is not a valid score
private class ByPoints(val pointsA: Points, val pointsB: Points): Score(
    "${pointsA.value} - ${pointsB.value}"
) {
    private fun pointsOf(player: Player) = if (player==Player.A) pointsA else pointsB
    override fun next(winner: Player): Score = when {
        pointsOf(winner)== FORTY-> Game(winner)
        pointsOf(winner)== THIRTY && pointsOf(winner.other())==FORTY -> Deuce
        winner==Player.A -> ByPoints( pointsA.next(), pointsB )
        else -> ByPoints( pointsA, pointsB.next())
    }
}

private class Game(winner: Player): Score("Game $winner") {
    override val isGame = true
    override fun next(winner: Player) = error("Game over")
}

private object Deuce: Score("Deuce") {
    override fun next(winner: Player) = Advantage(winner)
}

private class Advantage(val player: Player): Score("Advantage $player") {
    override fun next(winner: Player) = if (winner==player) Game(winner) else Deuce
}

val InitialScore: Score = ByPoints(LOVE,LOVE)