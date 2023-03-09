import isel.tds.MutableStack
import kotlin.test.*

class MutableStackTest {
    @Test fun `Empty stack conditions`() {
        val sut = MutableStack<Int>()
        assertTrue(sut.isEmpty())
        assertFailsWith<NoSuchElementException> { sut.top() }
    }
    @Test fun `Single element on the stack`() {
        val sut = MutableStack<Int>()
        sut.push(1)
        assertFalse(sut.isEmpty())
        assertEquals(1, sut.top())
        assertEquals(1, sut.pop())
        assertTrue(sut.isEmpty())
    }
}