package pt.isel.tds.storage

import kotlin.io.path.*

//TextFileStorage<String,Board>("games", BoardSerializer)

/**
 * A storage that uses text files to store data.
 * @param baseFolder the folder where the files are stored.
 * @param serializer the serializer to use to convert data to/from text.
 */
class TextFileStorage<Key,Data>(
    val baseFolder: String,
    val serializer: Serializer<Data,String>
) : Storage<Key,Data> {
    init {  // Create
        Path(baseFolder).run {
            if (!exists() ) createDirectory()
            else check( isDirectory() ) { "$baseFolder is not a directory" }
        }
    }
    // Obtain the path of the file for the given key.
    private fun path(key: Key) = Path("$baseFolder/$key.txt")
    // CRUD operations
    override fun create(key: Key, data: Data) =
        path(key).run {
            check( !exists() ) { "File already exists" }
            writeText(serializer.serialize(data))
        }
    override fun read(key: Key): Data? =
        path(key).run {
            if (!exists() ) null
            else serializer.deserialize(readText())
        }
    override fun update(key: Key, data: Data) =
        path(key).run {
            check( exists() ) { "File does not exist" }
            writeText(serializer.serialize(data))
        }
    override fun delete(key: Key) {
        path(key).run {
            check( exists() ) { "File does not exist" }
            deleteExisting()
        }
    }
}