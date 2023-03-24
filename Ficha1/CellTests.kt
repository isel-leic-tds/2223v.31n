import kotlin.test.*
import pt.isel.tds.reversi.model.*

class CellTests {
    @Test fun `Get a cell by Row and Column and test properties`() {
        val sut = Cell(Row(1), Column('B'))
        assertEquals(Row(1), sut.row)
        assertEquals(Column('B'), sut.col)
        assertEquals(0, sut.rowIndex)
        assertEquals(1, sut.colIndex)
        assertEquals("1B", sut.toString())
    }
    @Test fun `Get a cell by Row index and Column index`() {
        val sut = Cell(0, 1)
        assertEquals(Row(1), sut.row)
        assertEquals(Column('B'), sut.col)
        assertEquals(0, sut.rowIndex)
        assertEquals(1, sut.colIndex)
        assertEquals("1B", sut.toString())
    }
    @Test fun `Get a Cell instance`() {
        val sut = Cell(Row(2), Column('D'))
        assertEquals(sut, Cell(1,3))
        assertSame(sut, Cell(1,3))
    }
    @Test fun `Get all valid cells`() {
        assertEquals(BOARD_DIM*BOARD_DIM, Cell.values.size)
        assertEquals(Cell(Row(1), Column('A')), Cell.values[0])
        assertEquals(Cell(Row(BOARD_DIM), Column('A'+ BOARD_DIM-1)), Cell.values[BOARD_DIM*BOARD_DIM-1])
    }
    @Test fun `Get an invalid Cell by indexes`() {
        assertEquals(Cell.INVALID, Cell(-1,2))
        assertEquals(Cell.INVALID, Cell(2, BOARD_DIM))
    }
    @Test fun `Parse a string to a Cell`() {
        val sut = Cell(0,1)
        assertEquals(sut, "1B".toCellOrNull())
        assertSame(sut, "1B".toCellOrNull())
        assertNull("1".toCellOrNull())
        assertNull("B".toCellOrNull())
        assertNull("1BB".toCellOrNull())
        assertNull("1Z".toCellOrNull())
        assertEquals(sut, "1B".toCell())
        val ex = assertFailsWith<IllegalArgumentException> { "1CC".toCell() }
        assertEquals("Cell must have row and column", ex.message)
        assertEquals("Invalid column Z",assertFailsWith<IllegalArgumentException> { "1Z".toCell() }.message)
        assertEquals("Invalid row 0",assertFailsWith<IllegalArgumentException> { "0B".toCell() }.message)
    }
    @Test fun `Add a cell and a direction`() {
        assertEquals(Cell(0,1), Cell(0,0) + Direction.RIGHT)
        assertEquals(Cell(1,1), Cell(0,0) + Direction.DOWN_RIGHT)
        assertEquals(Cell(1,BOARD_DIM-2), Cell(0,BOARD_DIM-1) + Direction.DOWN_LEFT)
        assertEquals(Cell.INVALID, Cell(0,0) + Direction.UP)
        assertEquals(Cell.INVALID, Cell(0, BOARD_DIM-1) + Direction.DOWN_RIGHT)
    }
    @Test fun `Get line of cells from a cell and a direction`() {
        val from = Cell(3,2)
        val dir = Direction.LEFT
        val line1 = cellsInDirection(from, dir)
        assertEquals(listOf(from+dir,from+dir+dir), line1)
        val line2 = cellsInDirection(from, Direction.DOWN_RIGHT)
        assertEquals(List(BOARD_DIM-4) { Cell(3+it+1,2+it+1) }, line2)
    }
}