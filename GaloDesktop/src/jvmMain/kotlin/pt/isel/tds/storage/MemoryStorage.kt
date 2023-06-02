package pt.isel.tds.storage

/**
 * A storage used for debug that uses a map in memory to store data.
 * @param serializer the serializer to use to convert data to/from text.
 */
class MemoryStorage<Key,Data> : Storage<Key,Data> {
    private val map = mutableMapOf<Key,Data>()

    // CRUD operations
    override fun create(key: Key, data: Data) {
        check( !map.containsKey(key) ) { "Key already exists" }
        map[key] = data
    }
    override suspend fun read(key: Key): Data? = map[key]
    override fun update(key: Key, data: Data) {
        check( map.containsKey(key) ) { "Key does not exist" }
        map[key] = data
    }
    override fun delete(key: Key) {
        check( map.containsKey(key) ) { "Key does not exist" }
        map.remove(key)
    }
}