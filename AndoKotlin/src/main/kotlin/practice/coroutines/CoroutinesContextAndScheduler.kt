package practice.coroutines

import kotlinx.coroutines.*

//协程上下文与调度器
//https://www.kotlincn.net/docs/reference/coroutines/coroutine-context-and-dispatchers.html

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
fun main33() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                newSingleThreadContext("Ctx3").use { ctx3 ->
                    //卡住,不在往下执行
//                    runBlocking(ctx1) {
//                        log("Working in ctx1...")
//                    }
                    runBlocking(ctx3) {
                        log("Working in ctx3...")
                    }
                    coroutineScope {
                        log("Working in ctx1 coroutineScope...")
                    }
                    runBlocking(ctx2) {
                        log("Working in ctx2...")
                    }
                }
                log("Back to ctx1")
            }
        }
    }
}
//[Ctx1 @coroutine#1] Started in ctx1
//[Ctx2 @coroutine#1] Working in ctx2
//[Ctx3 @coroutine#2] Working in ctx3...
//[Ctx1 @coroutine#1] Working in ctx1 coroutineScope...
//[Ctx2 @coroutine#3] Working in ctx2...
//[Ctx1 @coroutine#1] Back to ctx1

//上下文中的作业
//请注意，CoroutineScope 中的 isActive 只是 coroutineContext[Job]?.isActive == true 的一种方便的快捷方式。
fun main34() = runBlocking<Unit> {
    println("My job is ${coroutineContext[Job]}")
}
//My job is "coroutine#1":BlockingCoroutine{Active}@2d8e6db6

//子协程
/*
当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
然而，当使用 GlobalScope 来启动一个协程时，则新协程的作业没有父作业。 因此它与这个启动的作用域无关且独立运作。
 */
fun main35() = runBlocking<Unit> {
    // 启动一个协程来处理某种传入请求（request）
    val request = launch {
        // 孵化了两个子作业, 其中一个通过 GlobalScope 启动
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // 另一个则承袭了父协程的上下文
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // 取消请求（request）的执行
    delay(1000) // 延迟一秒钟来看看发生了什么
    println("main: Who has survived request cancellation?")
}
//job1: I run in GlobalScope and execute independently!
//job2: I am a child of the request coroutine
//job1: I am not affected by cancellation of the request
//main: Who has survived request cancellation?


//父协程的职责
//一个父协程总是等待所有的子协程执行结束。父协程并不显式的跟踪所有子协程的启动，并且不必使用 Job.join 在最后的时候等待它们：
fun main36() = runBlocking<Unit> {
    // 启动一个协程来处理某种传入请求（request）
    val request = launch {
        repeat(3) { i -> // 启动少量的子作业
            launch {
                delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒的时间
                println("Coroutine $i is done")
            }
        }
        println("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // 等待请求的完成，包括其所有子协程
    println("Now processing of the request is complete")
}
//request: I'm done and I don't explicitly join my children that are still active
//Coroutine 0 is done
//Coroutine 1 is done
//Coroutine 2 is done
//Now processing of the request is complete

//注释掉 -> request.join()
//Now processing of the request is complete
//request: I'm done and I don't explicitly join my children that are still active
//Coroutine 0 is done
//Coroutine 1 is done
//Coroutine 2 is done

//命名协程以用于调试
fun main37() = runBlocking(CoroutineName("main")) {
    log("Started main coroutine")
    // 运行两个后台值计算
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
}
//[main @main#1] Started main coroutine
//[main @v1coroutine#2] Computing v1
//[main @v2coroutine#3] Computing v2
//[main @main#1] The answer for v1 / v2 = 42


//组合上下文中的元素  -Dkotlinx.coroutines.debug
//有时我们需要在协程上下文中定义多个元素。我们可以使用 + 操作符来实现。 比如说，我们可以显式指定一个调度器来启动协程并且同时显式指定一个命名：
fun main38() = runBlocking<Unit> {
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
}
//I'm working in thread DefaultDispatcher-worker-1 @test#2


//协程作用域
class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default) // use Default for test purposes

    fun destroy() {
        mainScope.cancel()
    }

    fun doSomething() {
        // 在示例中启动了 10 个协程，且每个都工作了不同的时长
        repeat(10) { i ->
            mainScope.launch {
                delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒等等不同的时间
                println("Coroutine $i is done")
            }
        }
    }
} // Activity 类结束

fun main39() = runBlocking<Unit> {
    val activity = Activity()
    activity.doSomething() // 运行测试函数
    println("Launched coroutines")
    delay(500L) // 延迟半秒钟
    println("Destroying activity!")
    activity.destroy() // 取消所有的协程
    delay(1000) // 为了在视觉上确认它们没有工作
}
//Launched coroutines
//Coroutine 0 is done
//Coroutine 1 is done
//Destroying activity!

//线程局部数据
val threadLocal = ThreadLocal<String>() // 声明线程局部变量

fun main399() = runBlocking<Unit> {
    threadLocal.set("main")
    println("1, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("2, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("3, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }
    job.join()
    println("4, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
}
//1, current thread: Thread[main @coroutine#1,5,main], thread local value: 'main'
//2, current thread: Thread[DefaultDispatcher-worker-1 @coroutine#2,5,main], thread local value: 'launch'
//3, current thread: Thread[DefaultDispatcher-worker-1 @coroutine#2,5,main], thread local value: 'launch'
//4, current thread: Thread[main @coroutine#1,5,main], thread local value: 'main'