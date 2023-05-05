
/**
 * var <propertyName>[: <PropertyType>] [= <property_initializer>]
 *      [<getter>]
 *      [<setter>]
 */

class Y {
    var counter = 0
    var text: String
        get() = "counter=$counter"
        set(str) { counter = str.substringAfter('=',"0").toIntOrNull() ?: 0 }
    var readOthers = 10
        private set
    fun changeReadOthers() { readOthers++ }
    var number: Int = 0
        set(value) {
            if (value>=0) field = value  // number = value
        }
}

// Global vars properties
var counter = 3
var prop: String = "unknown"
    set(value) { field = "txt:$value"}

// Extension vars
var Int.strange :Int  // No backing fields for extension properties?
    get() = counter
    set(value) { counter+=value }

fun main() {
    val y = Y()
    println( y.counter )
    y.counter++
    println( y.text )
    y.text = "x=5"
    println(y.counter)
    y.changeReadOthers()
    println(y.readOthers)
    y.number = -3
    println(y.number)
    println(prop)
    prop = "ISEL"
    println(prop)
    15.strange = -1
    println(27.strange)
}