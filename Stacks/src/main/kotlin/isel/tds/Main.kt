package isel.tds

/**
 * Code snippet to use a generic MutableStack.
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


    var s = Stack<String>()
    s = s.push("ISEL").push("LEIC").push("TDS")
    println(s.top())
    while (! s.isEmpty()) {
        val elem = s.top()
        s = s.pop()
        //val (elem,stk) = s.pop2()
        //val pair = s.pop2()
        //val stk = pair.component2()
        //val elem = pair.component1()
        //s = stk
        println(elem)
    }
}