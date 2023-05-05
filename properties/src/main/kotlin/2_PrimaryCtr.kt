
class Student2(n: Int, id:String) {
    private val number: Int
    private val name: String
    init {  // Can access primary constructor parameters
        print("Init -> ")
        number = n
        name = id
    }
    constructor() : this(0,"") {
        print("C() -> ")
    }
    fun show() = println("$number - ($name)")
}

fun main() {
    Student2().show()
    Student2(48000,"Pedro Pereira").show()
}