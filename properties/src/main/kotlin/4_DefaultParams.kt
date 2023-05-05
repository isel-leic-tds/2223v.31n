
private class Student4(n: Int=0, id:String="") {
    private val number: Int = n
    private val name: String = id
    init { print("Init -> ") }
    fun show() = println("$number - ($name)")
}

fun main() {
    Student4().show()
    Student4(48000,"Pedro Pereira").show()
    Student4(n = 47000).show()
    Student4(id = "Ana Paula").show()
}