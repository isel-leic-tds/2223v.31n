import kotlin.test.*
import pt.isel.tds.reversi.model.*

/*
class RowTests {
    @Test fun `Test row properties`() {
        val sut = Row(4)
        assertEquals(4, sut.number)
        assertEquals(3, sut.index)
        assertEquals("Row 4", sut.toString())
        val other = Row(4)
        assertEquals(sut, other)
        assertSame(sut, other)
    }
    @Test fun `Convert a number to a Row`() {
        assertSame(Row(4),4.toRowOrNull())
        assertNull(0.toRowOrNull())
        assertNull((BOARD_DIM+1).toRowOrNull())
        assertSame(Row(4),4.toRow())
        val ex = assertFailsWith<IllegalArgumentException> { 27.toRow() }
        assertEquals("Invalid row 27", ex.message)
    }
    @Test fun `Row values are correct`() {
        assertEquals(BOARD_DIM, Row.values.size)
        assertEquals(0, Row.values[0].index)
        assertEquals(BOARD_DIM, Row.values[BOARD_DIM-1].number)
    }
}
*/