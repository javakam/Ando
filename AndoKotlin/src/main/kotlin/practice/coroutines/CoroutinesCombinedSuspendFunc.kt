package practice.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

//组合挂起函数
//https://www.kotlincn.net/docs/reference/coroutines/composing-suspending-functions.html

suspend fun doSomethingUsefulOne(): Int {
    delay(2000L) // 假设我们在这里做了一些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}

fun main20() = runBlocking {
    val time = measureTimeMillis {
        val one: Int = doSomethingUsefulOne()
        val two: Int = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}
//Completed in 3013 ms

//使用 async 并发  -> 比上面的方式快了两倍，因为两个协程并发执行。 请注意，使用协程进行并发总是显式的。
/* 在概念上，async 就类似于 launch。它启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程一起并发的工作。
不同之处在于 launch 返回一个 Job 并且不附带任何结果值，而 async 返回一个 Deferred —— 一个轻量级的非阻塞 future，
这代表了一个将会在稍后提供结果的 promise。你可以使用 .await() 在一个延期的值上得到它的最终结果， 但是 Deferred 也是一个 Job，
所以如果需要的话，你可以取消它。 */
fun main21() = runBlocking {
    val time = measureTimeMillis {
        val one: Deferred<Int> = async { doSomethingUsefulOne() }
        val two: Deferred<Int> = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}
//Completed in 2028 ms

//惰性启动的 async
//在计算一个值涉及挂起函数时，这个 async(start = CoroutineStart.LAZY) 的用例用于替代标准库中的 lazy 函数。
//在 CoroutineStart.LAZY 模式下，只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调用的时候
fun main22() = runBlocking {
    val time = measureTimeMillis {
        val one: Deferred<Int> = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two: Deferred<Int> = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // 执行一些计算 . 注释掉后,变为 Completed in 3028 ms 跟为开启并发前一样慢了
        one.start() // 启动第一个
        two.start() // 启动第二个
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}
//Completed in 2021 ms

//async 风格的函数
// somethingUsefulOneAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulOneAsync(): Deferred<Int> = GlobalScope.async {
    doSomethingUsefulOne()
}

// somethingUsefulTwoAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulTwoAsync(): Deferred<Int> = GlobalScope.async {
    doSomethingUsefulTwo()
}

/*
[不推荐] , 用下面结构化并发的方式!!!

注意，这些 xxxAsync 函数不是 挂起 函数。它们可以在任何地方使用。
然而，它们总是在调用它们的代码中意味着异步（这里的意思是 并发 ）执行。
 */
// 下面的例子展示了它们在协程的外面是如何使用的：
// 注意，在这个示例中我们在 `main` 函数的右边没有加上 `runBlocking`
fun main23() {
    val time = measureTimeMillis {
        // 我们可以在协程外面启动异步执行
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // 但是等待结果必须调用其它的挂起或者阻塞
        // 当我们等待结果的时候，这里我们使用 `runBlocking { …… }` 来阻塞主线程
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}
//这种带有异步函数的编程风格仅供参考，因为这在其它编程语言中是一种受欢迎的风格。在 Kotlin 的协程中使用这种风格是强烈不推荐的， 原因如下所述。

//使用 async 的结构化并发
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

fun main24() = runBlocking<Unit> {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}
//Completed in 2030 ms

//取消始终通过协程的层次结构来进行传递：
suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // 模拟一个长时间的运算
            42
        } catch (e: Exception) {
            println("First child was 被队友坑了")
            -1
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

fun main25() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}
//请注意，如果其中一个子协程（即 two）失败，第一个 async 以及等待中的父协程都会被取消：
//推荐方式






