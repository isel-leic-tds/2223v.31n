package pt.isel.tds.storage

import com.mongodb.*
import com.mongodb.client.*
import org.litote.kmongo.*
import java.io.Closeable

/**
 * Name of the environment variable that contains the connection string in format:
 *   mongodb+srv://<username>:<password>@<host>/[<database>][?<options>]
 * Example: MONGO_CONNECTION=mongodb+srv://palex:tds123abc@cluster0.brsewd2.mongodb.net/reversi?retryWrites=true&w=majority
 */
private const val ENV_CONNECTION = "MONGO_CONNECTION"

/**
 * Name of the environment variable that contains the database name.
 * If not defined in the connection string or to replace indicated in connection string.
 * Example: MONGO_DB_NAME=TDS2223v
 */
private const val ENV_DB_NAME = "MONGO_DB_NAME"

/**
 * Represents the MongoDB driver. Must be closed at the end.
 * The connection string of the environment variable is used to connect to the remote database,
 * if the variable is not defined a local instance is used.
 * The database name is defined in the following order:
 * 1- constructor parameter; 2 - environment variable; 3 - in the connection string.
 * @param nameDb Database name (override environment variable and connection string)
 */
class MongoDriver(nameDb: String? =null) : Closeable {
    /**
     * Reference to database.
     * Receiver of database functions to call.
     */
    val db: MongoDatabase

    private val client: MongoClient
    init {
        val envConnection = System.getenv(ENV_CONNECTION) ?: System.getProperty(ENV_CONNECTION)
        val envDbName = System.getenv(ENV_DB_NAME) ?: System.getProperty(ENV_DB_NAME)
        val dbName = nameDb ?: envDbName ?: envConnection?.let { ConnectionString(it).database }
        require(dbName!=null) { "Database name is required in constructor, environment variable or connection string" }
        client = envConnection?.let { KMongo.createClient(it) } ?: KMongo.createClient()
        db = client.getDatabase(dbName)
    }

    /**
     * Close the drive.
     * Must be called at the end of use.
     */
    override fun close() = client.close()

    /**
     * Gets one collection from the database.
     * @param id Collection identification.
     * @return The collection of documents of type T.
     */
    inline fun <reified T : Any> getCollection(id: String): Documents<T> =
        Documents(db.getCollection(id, T::class.java))

    /**
     * Gets all collections from the database that contains documents of type T.
     * @return The list of document collections of type T.
     */
    inline fun <reified T : Any> getAllCollections(): Iterable<Documents<T>> =
        db.listCollectionNames().map { getCollection<T>(it) }
}


/**
 * Represents a collection of documents of type T.
 */
class Documents<T>(val collection: MongoCollection<T>)

/**
 * Get all documents of type T from the collection.
 * @return The list of all documents (of type T) in the collection.
 */
fun <T> Documents<T>.getAllDocuments(): Iterable<T> = collection.find()

/**
 * Get a single document from the collection.
 * @param id Identification of document (assume _id property is of type K)
 * @return The document or null
 */
fun <T,K: Any> Documents<T>.getDocument(id: K): T? = collection.findOneById(id)

/**
 * Insert a new document (of type T) in the collection.
 * @param doc Document to insert.
 * @return true if writing was acknowledged.
 */
fun <T> Documents<T>.insertDocument(doc: T): Boolean = collection.insertOne(doc).wasAcknowledged()

/**
 * Updates the document with new content. Assumes the document has an _id property.
 * @param doc Document to update.
 * @return true if writing was acknowledged.
 */
inline fun <reified T: Any> Documents<T>.replaceDocument(doc: T): Boolean = collection.replaceOne(doc).wasAcknowledged()

/**
 * Deletes the document with the identifier.
 * @param id Identification of document (_id property is of type K)
 * @return true if deleting was acknowledged.
 */
fun <T,K: Any> Documents<T>.deleteDocument(id: K): Boolean = collection.deleteOneById(id).wasAcknowledged()

/**
 * Clear all documents from the collection.
 */
fun <T> Documents<T>.deleteAllDocuments(): Boolean = collection.deleteMany().wasAcknowledged()