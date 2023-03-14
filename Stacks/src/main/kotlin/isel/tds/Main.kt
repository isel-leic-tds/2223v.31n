package isel.tds

/**
 * Code snippet to use a generic MutableStack and Stack.
 */
fun main() {
    val x: Any = MutableStack<String>()
    val ms = MutableStack<String>()
    println(x == ms)

    ms.push("ISEL")
    ms.push("LEIC")
    ms.push("TDS")
    println(ms.top())
    while(! ms.isEmpty()) {
        val elem = ms.pop()
        println(elem)
    }
    var s = stackOf("ISEL","LEIC","TDS")
    println(s.top())
    while (! s.isEmpty()) {
        val elem = s.top()
        s = s.pop()
        println(elem)
    }
}