package pt.isel.tds.storage

/**
 * CRUD operations on a storage.
 * @param Key the type of the key.
 * @param Data the type of the data.
 */
interface Storage<Key,Data> {
    /**
     * Creates a new entry in the storage.
     * @param key the key of the entry.
     * @param data the data to store.
     * @throws IllegalStateException if the key already exists.
     */
    fun create(key: Key, data: Data)
    /**
     * Reads an entry from the storage.
     * @param key the key of the entry.
     * @return the data stored or null if the key does not exist.
     */
    suspend fun read(key: Key): Data?
    /**
     * Updates an entry in the storage.
     * @param key the key of the entry.
     * @param data the new data to store.
     * @throws IllegalStateException if the key does not exist.
     */
    fun update(key: Key, data: Data)
    /**
     * Deletes an entry from the storage.
     * @param key the key of the entry.
     * @throws IllegalStateException if the key does not exist.
     */
    fun delete(key: Key)
}

/**
 * Serialization to use in a storage.
 * data == deserialize(serialize(data))
 */
interface Serializer<Data,Stream> {
    fun serialize(data: Data): Stream
    fun deserialize(stream: Stream): Data
}