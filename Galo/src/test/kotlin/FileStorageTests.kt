import kotlin.test.*
import pt.isel.tds.storage.*
import kotlin.io.path.*

class FileStorageTests {
    companion object {
        val directSerializer = object : Serializer<String,String> {
            override fun serialize(data: String) = data
            override fun deserialize(stream: String) = stream
        }
        @OptIn(ExperimentalPathApi::class)
        fun clearFolder(folder: String) {
            Path(folder).run { if (exists() ) deleteRecursively() }
        }
    }
    @Test fun `Test file storage CRUD operations`() {
        clearFolder("test")
        val sut = TextFileStorage<String,String>("test", directSerializer)
        sut.create("key", "data")
        Path("test/key.txt").run { assertEquals("data", readText()) }
        assertEquals("data", sut.read("key"))
        sut.update("key", "new data")
        Path("test/key.txt").run { assertEquals("new data", readText()) }
        assertEquals("new data", sut.read("key"))
        sut.delete("key")
        Path("test/key.txt").run { assertFalse(exists()) }
        Path("test").run { deleteExisting() }
    }
}