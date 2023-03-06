package isel.tds

/**
 * Code snippet to use a generic MutableStack.
 */
fun main() {
    val ms = MutableStack<String>()
    ms.push("ISEL")
    ms.push("LEIC")
    ms.push("TDS")
    println(ms.top())
    while(! ms.isEmpty()) {
        val elem = ms.pop()
        println(elem)
    }
}