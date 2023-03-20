package isel.tds

private fun throwEmpty(): Nothing = throw NoSuchElementException("Empty stack")

interface Stack<T> {
    fun push(elem: T): Stack<T>
    fun top(): T
    fun pop(): Stack<T>
    fun isEmpty(): Boolean
}

private class Node<T>(val elem: T, val next: Node<T>?)

private class StackNotEmpty<T>(private val head: Node<T>): Stack<T> {
    override fun push(elem: T) = StackNotEmpty( Node(elem,head) )
    override fun top() = head.elem
    override fun pop() = head.next?.let { StackNotEmpty(it) } ?: Stack()
    override fun isEmpty() = false
}

private object StackEmpty: Stack<Any> {
    override fun push(elem: Any) = StackNotEmpty( Node(elem,null) )
    override fun top() = throwEmpty()
    override fun pop() = throwEmpty()
    override fun isEmpty() = true
}

/**
 * Creates an empty immutable stack.
 */
@Suppress("UNCHECKED_CAST")
fun <T> Stack() = StackEmpty as Stack<T>

/**
 * Creates an immutable stack with the given elements.
 */
fun <T> stackOf(vararg elems: T): Stack<T> =
    if (elems.isEmpty()) Stack()
    else StackNotEmpty( elems.drop(1).fold(Node(elems[0],null)){ n, e -> Node(e,n) } )

/**
 * Desafio proposto no sumário da aula de 13 Março
 * Creates an immutable stack with the given [size] and [init]ializer function.
 */
fun <T> Stack(size: Int, init: (Int)->T): Stack<T> =
    if (size == 0) Stack()
    else {
        val start = Node(init(size-1), null)
        StackNotEmpty(
            (size-2 downTo 0).fold(start){ node, index ->
                Node(init(index), node)
            }
        )
    }