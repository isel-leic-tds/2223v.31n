
class Student5(private val number: Int=0, private val name:String="") {
    init { print("Init -> ") }
    //constructor() : this(0,"")
    fun show() = println("$number - ($name)")
}

fun main() {
    Student5().show()
    Student5(48000,"Pedro Pereira").show()
    Student5(47000).show()
    Student5(name="Ana Paula").show()
}