import kotlin.test.*
import isel.tds.Date
import isel.tds.addDays

class DateTest {
    @Test
    fun createDate() {
        val d1 = Date(2023, 3, 2)
        assertEquals(2023, d1.year)
        assertEquals(3, d1.month)
        assertEquals(2, d1.day)
    }

    @Test
    fun `Create Date only with year`() {
        val d2 = Date(2023)
        assertEquals(2023, d2.year)
        assertEquals(1, d2.month)
        assertEquals(1, d2.day)
    }

    @Test
    fun `Create Date with leap year`() {
        val d3 = Date(2020, 3, 2)
        assertTrue(d3.leapYear)
    }

    @Test
    fun `Create Date with non-leap year`() {
        val d4 = Date(2023, 3, 2)
        assertFalse(d4.leapYear)
    }
    @Test
    fun `Create Date with last day of month`() {
        val d5 = Date(2023, 3, 31)
        assertEquals(31, d5.lastDayOfMonth)
        val d6 = Date(2020, 2, 29)
        assertEquals(29, d6.lastDayOfMonth)
    }
    @Test
    fun `Convert Date to text`() {
        val d1 = Date(2023,3,2)
        assertEquals("2023-3-2",d1.toString())
    }
    @Test
    fun `Verify equals Dates`() {
        val d1 = Date(2023,3,2)
        val d2 = Date(2023,3,2)
        assertTrue(d1.equals(d2))
        val d3 = Date(2023,3,3)
        assertFalse(d1.equals(d3))
        //assertTrue(d3.equals("2023-3-3"))
    }
    @Test
    fun `Verify equals Dates by hashCode`() {
        val d1 = Date(2023,3,2)
        val d2 = Date(2023,3,2)
        assertEquals(d1.hashCode(),d2.hashCode())
    }
    @Test
    fun `Create invalid Dates`() {
        assertFailsWith<IllegalArgumentException> { Date(2023, 0, 2) }
        assertFailsWith<IllegalArgumentException> { Date(2023, 13, 2) }
        assertFailsWith<IllegalArgumentException> { Date(2023, 3, 0) }
        assertFailsWith<IllegalArgumentException> { Date(2023, 3, 32) }
        assertFailsWith<IllegalArgumentException> { Date(2023, 2, 29) }
    }
    @Test
    fun `Add days to a Date`() {
        val d1 = Date(2023,3,2)
        val d2 = d1.addDays(3)
        assertEquals("2023-3-5",d2.toString())
        val d3 = Date(2023,3,31).addDays(1)
        assertEquals("2023-4-1",d3.toString())
        val d4 = Date(2023,12,31).addDays(1)
        assertEquals("2024-1-1",d4.toString())
        val d5 = d1.addDays(400000)
        assertEquals("3118-5-1",d5.toString())
    }
}