import kotlin.test.*
import pt.isel.tds.reversi.model.*

class ColumnTests {
    @Test fun `Column properties`() {
        val sut = Column('D')
        assertEquals('D', sut.symbol)
        assertEquals(3, sut.index)
        assertEquals("Column D", sut.toString())
    }
    @Test fun `Column equality`() {
        val sut = Column('D')
        val other = Column('D')
        assertEquals(sut, other)
        val other2 = Column('B')
        assertNotEquals(sut, other2)
    }
    @Test fun `Create invalid Columns fails`() {
        assertFails {  Column('3') }
        assertFails {  Column('#') }
        assertFails {  Column('A'+BOARD_DIM) }
    }
    @Test fun `Convert a symbol to a Column`() {
        assertEquals(Column('B'),'B'.toColumnOrNull())
        assertNull('3'.toColumnOrNull())
        assertNull(('A'+BOARD_DIM).toColumnOrNull())
        assertEquals(Column('C'),'C'.toColumn())
        val ex = assertFailsWith<IllegalArgumentException> { '?'.toColumn() }
        assertEquals("Invalid column ?", ex.message)
    }
    @Test fun `Column values are correct`() {
        assertEquals(BOARD_DIM, Column.values.size)
        assertEquals(0, Column.values[0].index)
        assertEquals('A'+BOARD_DIM-1, Column.values[BOARD_DIM-1].symbol)
    }
    @Test fun `Column instances are unique`() {
        val sut = Column('D')
        val sut2 = Column('D')
        assertEquals(sut, sut2)
        assertSame(sut, sut2)
    }
}