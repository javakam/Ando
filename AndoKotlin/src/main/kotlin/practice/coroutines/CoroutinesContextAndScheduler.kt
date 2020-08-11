package practice.coroutines

import javafx.application.Application.launch
import kotlinx.coroutines.*


//协程上下文与调度器

//调度器与线程
fun main30() {
    runBlocking {
        launch { // 运行在父协程的上下文中，即 runBlocking 主协程
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // 不受限的——将工作在主线程中
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) { // 将会获取默认调度器
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.Default) {
            println("Default GlobalScope   : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
    }
}
//Unconfined            : I'm working in thread main
//Default               : I'm working in thread DefaultDispatcher-worker-1
//Default GlobalScope   : I'm working in thread DefaultDispatcher-worker-1
//newSingleThreadContext: I'm working in thread MyOwnThread
//main runBlocking      : I'm working in thread main

//非受限调度器 vs 受限调度器
/*
非受限的调度器是一种高级机制，可以在某些极端情况下提供帮助而不需要调度协程以便稍后执行或产生不希望的副作用， 因为某些操作必须立即在协程中执行。
非受限调度器不应该在通常的代码中使用。
 */
fun main31() = runBlocking<Unit> {
    launch(Dispatchers.Unconfined) { // 非受限的——将和主线程一起工作
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // 父协程的上下文，主 runBlocking 协程
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
}
//Unconfined      : I'm working in thread main
//main runBlocking: I'm working in thread main
//Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
//main runBlocking: After delay in thread main



fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")


//调试协程与线程 ,需要配置 JVM 参数,在运行 -Dkotlinx.coroutines.debug
//这里有三个协程，包括 runBlocking 内的主协程 (#1) ， 以及计算延期的值的另外两个协程 a (#2) 和 b (#3)。
//它们都在 runBlocking 上下文中执行并且被限制在了主线程内。
fun main32() = runBlocking<Unit> {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}
//[main @coroutine#2] I'm computing a piece of the answer
//[main @coroutine#3] I'm computing another piece of the answer
//[main @coroutine#1] The answer is 42


//在不同线程间跳转
//使用 -Dkotlinx.coroutines.debug JVM 参数运行下面的代码
fun main() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                newSingleThreadContext("Ctx3").use { ctx3 ->
                    runBlocking (ctx3){
                        log("Working in ctx3")
                    }
                }
                log("Back to ctx1")
            }
        }
    }
}
//[Ctx1 @coroutine#1] Started in ctx1
//[Ctx2 @coroutine#1] Working in ctx2
//[Ctx1 @coroutine#1] Back to ctx1












