package isel.tds

/**
 * A mutable stack of elements of type T.
 * Implemented with an immutable list.
 */
class MutableStack1<T> {
    private var elems: List<T> = emptyList()

    fun push(elem: T) { elems = elems + elem }
    fun top(): T = elems.last()
    fun pop(): T {
        val top = top()
        elems = elems.dropLast(1)
        return top
    }
    fun isEmpty() = elems.isEmpty()
}

/**
 * A mutable stack of elements of type T.
 * Implemented with a mutable list.
 */
class MutableStack2<T> {
    private val elems = mutableListOf<T>()

    fun push(elem: T) { elems.add(elem) }
    fun top(): T = elems.last()
    fun pop(): T {
        val top = top()
        elems.removeLast()
        return top
    }
    fun isEmpty() = elems.isEmpty()
}

/**
 * A mutable stack of elements of type T.
 * Implemented with a single linked list.
 */
class MutableStack<T> {
    private class Node<U>(val elem: U, val next: Node<U>?)
    private var head: Node<T>? = null

    fun push(elem: T) { head = Node(elem, head) }
    fun top(): T = head?.elem ?: throw NoSuchElementException("Empty stack")
    fun pop(): T {
        val elem = top()
        head = head?.next
        return elem
    }
    fun isEmpty() = head == null
}



