import kotlin.test.*
import isel.tds.Stack

class StackTests {
    @Test fun `Make a stack given a dimension and initializer`() {
        var sut = Stack(5) { it * 2 }
        (0..8 step 2).forEach {
            assertEquals(it, sut.top())
            sut = sut.pop()
        }
        assertTrue(sut.isEmpty())
    }
}