package pt.isel.tds.storage

import com.mongodb.MongoWriteException
import kotlin.io.path.exists

// MongoStorage<String,Board>("games", driver, BoardSerializer)

/**
 * A storage that uses a MongoDB collection to store data.
 * @param collectionName the name of the collection.
 * @param driver the driver to use to access the database.
 * @param serializer the serializer to use to convert data to/from text.
 */
class MongoStorage<Key: Any, Data>(
    collectionName: String,
    driver: MongoDriver,
    private val serializer: Serializer<Data,String>
) : Storage<Key,Data> {

    class Doc<K>(val _id: K, val data: String)
    val docs = driver.getCollection<Doc<Key>>(collectionName)

    // CRUD operations
    override fun create(key: Key, data: Data) {
        val doc = Doc(key, serializer.serialize(data))
        try {
            docs.insertDocument(doc)
        } catch (e: MongoWriteException) {
            error("File already exists")
        }
    }
    override suspend fun read(key: Key): Data? =
        docs.getDocument(key)?.let { serializer.deserialize(it.data) }

    override fun update(key: Key, data: Data) {
        docs.replaceDocument(Doc(key, serializer.serialize(data)))
    }
    override fun delete(key: Key) {
        docs.deleteDocument(key)
    }
}