package pt.isel.tds.tennis.pm

import pt.isel.tds.tennis.Player

private enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30); // FORTY(40); //, GAME(45);
    fun next(): Points = values()[ordinal+1]
}

/**
 * In this solution uses a hierarchy of classes representing the possible states of the score.
 * These classes store only the information of each state.
 * The next function and property getters do pattern matching (pm) between this and one of the types.
 */
sealed class Score
private class ByPoints(val pointsA: Points, val pointsB: Points): Score()
private class Forty(val player: Player, val pointsOther: Points): Score()
private class Game(val winner: Player): Score()
private object Deuce: Score()
private class Advantage(val player: Player): Score()

val Score.placard get() = when (this) {
    is ByPoints -> "${pointsA.value} - ${pointsB.value}"
    is Forty -> if (player==Player.A) "40 - ${pointsOther.value}" else "${pointsOther.value} - 40"
    is Game -> "Game $winner"
    Deuce -> "Deuce"
    is Advantage -> "Advantage $player"
}

val Score.isGame get() = this is Game

private fun ByPoints.pointsOf(player: Player) = if (player==Player.A) pointsA else pointsB

fun Score.next(winner: Player): Score = when (this) {
    is ByPoints -> when {
        pointsOf(winner)== Points.THIRTY -> Forty(winner, pointsOf(winner.other()))
        winner==Player.A -> ByPoints( pointsA.next(), pointsB )
        else -> ByPoints( pointsA, pointsB.next())
    }
    is Forty -> when {
        winner==player -> Game(winner)
        pointsOther==Points.THIRTY -> Deuce
        else -> Forty(player, pointsOther.next())
    }
    is Game -> error("Game over")
    Deuce -> Advantage(winner)
    is Advantage -> if (winner==player) Game(winner) else Deuce
}

val InitialScore: Score = ByPoints(Points.LOVE,Points.LOVE)