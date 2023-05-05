
private class Student3(n: Int, id:String) {
    private val name: String = id // Access primary constructor parameters
    init {                        // Order of execution = declaration order
        print("Init -> ")
    }
    private val number: Int = n.also{ print("n=$it ") }
    init { print("Other ") }
    constructor() : this(0,"") {
        print("C() -> ")
    }
    init {
        print("Last ")
    }
    fun show() = println("$number - ($name)")
}

fun main() {
    Student3().show()
    Student3(48000,"Pedro Pereira").show()
}