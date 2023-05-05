
/**
 * val <propertyName>[: <PropertyType>] [= <property_initializer>]
 *      [<getter>]
 */

class X {
    val first = 3
        get() { print("get first: "); return field }
    val second: Int?        // why nullable?
    init { second = 2 }
    val third: Int          // Consumes instance memory?
        get() = first + (second?:0)  // No backing field
    val fourth get() = 4
    private val fifth
        get() = fourth + first
    fun useFifth() {        // Debug to see backing fields and getters
        println(fifth)
    }
}

// Global properties
var size = 3
val isEmpty get() = size==0

// Extension properties
val String.size get() = length  // Backing field?

fun main() {
    val x = X()
    println( x.first )      // java: x.getFirst()
    println( x.second )     // java: x.getSecond()
    println( x.third )      // java: x.getThird()
    println( x.fourth )     // java: x.getFourth()
    x.useFifth()
    // val prop get() = 27     // No local properties
    println(isEmpty)
    println("abc".size)
}