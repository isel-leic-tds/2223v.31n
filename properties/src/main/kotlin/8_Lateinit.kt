
class Z {
    lateinit var later : String
    //var later: String? = null
    fun check() = this::later.isInitialized
    //fun check() = later!=null
}

fun main() {
    val z = Z()
    //println(z.later)
    println(z.check())
    z.later = "Ok"
    println(z.later)
    println(z.check())
}