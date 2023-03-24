package pt.isel.tds.tennis.fp

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.fp.Points.*

private enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30)
}
private fun Points.next(): Points = Points.values()[ordinal+1]

/**
 * This solution uses only one class [Score] to represent all states of the score.
 * Each instance of [Score] stores the information of the state and the next function (by value).
 *
 * The functions [deuce], [advantage], [game], [byPoints] and [forty] are used to create instances of [Score].
 * @property placard the string representation of the score
 * @property isGame true if the score is a game (terminal state)
 * @property next the function that computes the next score state given the winner of the point
 */
class Score(
    val placard: String,
    val isGame: Boolean = false,
    val next: (winner: Player) -> Score
)

private fun deuce() = Score("Deuce", next= ::advantage)

private fun advantage(player: Player): Score = Score("Advantage $player") {
    if (it==player) game(it) else deuce()
}

private fun game(winner: Player) = Score("Game $winner",true) { error("Game over") }

private fun byPoints(pointsA: Points, pointsB: Points): Score = Score(
    "${pointsA.value} - ${pointsB.value}"
) {
    fun pointsOf(player: Player) = if (player==Player.A) pointsA else pointsB
    when {
        pointsOf(it)==THIRTY -> forty(it, pointsOf(it.other()))
        it==Player.A -> byPoints( pointsA.next(), pointsB )
        else -> byPoints( pointsA, pointsB.next())
    }
}

private fun forty(player: Player, pointsOther: Points): Score = Score(
    if (player==Player.A) "40 - ${pointsOther.value}" else "${pointsOther.value} - 40"
) {
    when {
        it==player -> game(it)
        pointsOther==THIRTY -> deuce()
        else -> forty(player, pointsOther.next())
    }
}

val InitialScore = byPoints(LOVE,LOVE)
