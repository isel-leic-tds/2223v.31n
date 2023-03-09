package isel.tds

//data class PopReturn<T>(val elem: T, val stack: Stack<T>)

class Stack1<T> private constructor(private val head: Node<T>?) {
    private class Node<U>(val elem: U, val next: Node<U>?)
    private val headNotNull get() = head ?: throw NoSuchElementException("Empty stack")

    constructor() : this(null)
    fun push(elem: T) = Stack1( Node(elem,head) )
    fun top(): T = headNotNull.elem
    fun pop() = Stack1( headNotNull.next )
    fun isEmpty() = head == null
    fun pop2() = top() to pop()
}

interface Stack<T> {
    fun push(elem: T): Stack<T>
    fun top(): T
    fun pop(): Stack<T>
    fun isEmpty(): Boolean
}

class Node<T>(val elem: T, val next: Node<T>?)

class StackNotEmpty<T>(private val head: Node<T>): Stack<T> {
    override fun push(elem: T) = StackNotEmpty( Node(elem,head) )
    override fun top() = head.elem
    override fun pop(): Stack<T> =
        if (head.next==null) StackEmpty() else StackNotEmpty( head.next )
    override fun isEmpty() = false
}

class StackEmpty<T> : Stack<T> {
    override fun push(elem: T) = StackNotEmpty( Node(elem,null) )
    override fun top() = throw NoSuchElementException()
    override fun pop() = throw NoSuchElementException()
    override fun isEmpty() = true
}

fun <T> Stack(): Stack<T> = StackEmpty()