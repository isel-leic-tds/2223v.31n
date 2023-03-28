import pt.isel.tds.ttt.model.Position
import kotlin.test.*

class PositionTests {
    @Test fun `Create position by row and column`() {
        val pos = Position(1, 2)
        assertEquals(1, pos.row)
        assertEquals(2, pos.col)
    }
    @Test fun `Create position by index`() {
        val pos = Position(5)
        assertEquals(1, pos.row)
        assertEquals(2, pos.col)
        assertEquals("(1,2)",pos.toString())
    }
}