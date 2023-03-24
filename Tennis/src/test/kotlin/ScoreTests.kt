import kotlin.test.*
import pt.isel.tds.tennis.fp.*
import pt.isel.tds.tennis.*

class ScoreTests {
    @Test
    fun `InitialScore conditions`() {
        val sut = InitialScore
        assertFalse(sut.isGame)
        assertEquals("0 - 0", sut.placard)
    }
    @Test
    fun `InitialScore next`() {
        val sut = InitialScore
        val actual = sut.next(Player.A)
        assertFalse(actual.isGame)
        assertEquals("15 - 0", actual.placard)
    }
    @Test
    fun `Placard Sequence Points`() {
        var sut = InitialScore
        listOf("15 - 0", "30 - 0", "40 - 0", "Game A").forEach {
            sut = sut.next(Player.A)
            assertEquals(it, sut.placard)
        }
    }

    private fun Score.play(winnerSequence: String): Score =
        winnerSequence.fold(this) { score, winner -> score.next(winner.toString().toPlayer()) }
    @Test
    fun `Deuce conditions`() {
        val sut = InitialScore.play("ABABAB")
        assertFalse(sut.isGame)
        assertEquals("Deuce", sut.placard)
    }
    @Test
    fun `Game conditions`() {
        val sut = InitialScore.play("AAAA")
        assertTrue(sut.isGame)
        assertEquals("Game A", sut.placard)
        assertFailsWith<IllegalStateException> { sut.next(Player.A) }
    }
    @Test
    fun `Deuce to Advantage`() {
        val deuce = InitialScore.play("ABABAB")
        val advA =  deuce.next(Player.A)
        assertEquals("Advantage A",advA.placard)
        assertFalse(advA.isGame)
        val advB =  deuce.next(Player.B)
        assertEquals("Advantage B",advB.placard)
        assertFalse(advB.isGame)
    }
    @Test
    fun `Deuce again`() {
        val deuce = InitialScore.play("ABABAB")
        val sut = deuce.play("AB")
        assertEquals("Deuce",sut.placard)
        assertFalse(sut.isGame)
    }
    @Test
    fun `Advantage to Game`() {
        val advA = InitialScore.play("ABABABA")
        val sut =  advA.next(Player.A)
        assertEquals("Game A",sut.placard)
        assertTrue(sut.isGame)
    }
}
