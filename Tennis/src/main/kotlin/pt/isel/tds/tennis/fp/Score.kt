package pt.isel.tds.tennis.fp

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.fp.Points.*

private enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30)
}
private fun Points.next(): Points = Points.values()[ordinal+1]

class Score(
    val placard: String,
    val isGame: Boolean = false,
    val next: (Player) -> Score
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
