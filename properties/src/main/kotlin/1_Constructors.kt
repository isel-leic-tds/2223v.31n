
class Student1 {
    private val number: Int   // Must be initialized in init block
    private val name: String  // Consumes instance memory

    init { print("Init -> ") } // Executed by both constructors
    constructor(n: Int, id:String) {
        print("C(n,id) -> ")
        number = n
        name = id
    }
    constructor() {
        print("C() -> ")
        number = 0
        name =""
    }
    fun show() = println("$number - ($name)")
}

fun main() {
    Student1().show()
    Student1(48000,"Pedro Pereira").show()
}