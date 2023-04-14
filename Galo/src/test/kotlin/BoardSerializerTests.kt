import pt.isel.tds.ttt.model.*
import kotlin.test.*

class BoardSerializerTests {
    @Test fun `Test board serialization`() {
        val sut = createBoard(Player.X).play(5.toPosition()).play(0.toPosition())
        val expected = "Run:X\n5:X 0:O\n"
        assertEquals(expected,BoardSerializer.serialize(sut))
    }
    @Test fun `Test board deserialization`() {
        val sut = "Run:X\n4:X 0:O\n"
        val result = BoardSerializer.deserialize(sut)
        assertIs<BoardRun>(result)
        assertEquals(Player.X, result.turn)
        assertEquals("{4=X, 0=O}", result.moves.toString())
    }
}