package practice.coroutines

import javafx.application.Application.launch
import kotlinx.coroutines.*
import org.jetbrains.annotations.TestOnly
import sun.rmi.runtime.Log
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Title:协程
 * <p>
 * Description:https://www.bilibili.com/video/av67107689/
 * </p>
 * @author javakam
 * @date 2020-08-07 16:50:37
 */
@Deprecated("建议配合 runBlocking 使用, 如下")
fun main1() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }
    println("Hello,") // 协程已在等待时主线程还在继续


    //Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
    //另外一种写法:
    runBlocking {     // 但是这个表达式阻塞了主线程
        delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
    }
}

//上面更好的写法
//使用 runBlocking 来包装 main 函数的执行
fun main2() = runBlocking<Unit> { // 开始执行主协程
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    /*
    可以将 GlobalScope.launch { …… } 替换为 thread { …… }，并将 delay(……) 替换为 Thread.sleep(……) 达到同样目的。
    试试看（不要忘记导入 kotlin.concurrent.thread）。
     */
//    thread {
//        Thread.sleep(1000)
//        println("World!")
//    }

    println("Hello,") // 主协程在这里会立即执行
    delay(2000L)      // 延迟 2 秒来保证 JVM 存活
}

//等待一个作业
fun main3() = runBlocking {
    val job: Job = GlobalScope.launch {  // 启动一个新协程并保持对这个作业的引用
        delay(1000L)
        println("World!")
    }

    println("Hello,")
    //delay(2000L)    //延迟 2 秒来保证 JVM 的存活
    job.join()
}

//结构化的并发  在使用 GlobalScope.launch 时，我们会创建一个顶层协程。
//https://www.kotlincn.net/docs/reference/coroutines/basics.html#%E7%BB%93%E6%9E%84%E5%8C%96%E7%9A%84%E5%B9%B6%E5%8F%91
fun main4() = runBlocking { // this: CoroutineScope
    launch { // 在 runBlocking 作用域中启动一个新协程
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}

/**
 *
作用域构建器

除了由不同的构建器提供协程作用域之外，还可以使用 coroutineScope 构建器声明自己的作用域。
它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。

runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有子协程结束。
主要区别在于，runBlocking 方法会阻塞当前线程来等待， 而 coroutineScope 只是挂起，会释放底层线程用于其他用途。
由于存在这点差异，runBlocking 是常规函数，而 coroutineScope 是挂起函数。
 */
fun main5() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("111")
    }

    coroutineScope { // 创建一个协程作用域, 内部协程全都完成,才会执行后面的代码
        launch {
            delay(5000L)
            println("222")
        }

        delay(100L)
        println("333") // 这一行会在内嵌 launch 之前输出
    }

    runBlocking {
        launch {
            delay(400L)
            println("aaa")
        }

        delay(300L)
        println("bbb") // 这一行会在内嵌 launch 之前输出
    }
    println("444") // 这一行在内嵌 launch 执行完毕后才输出
}
//3,1,2,b,a,4


//提取函数重构
fun main6() = runBlocking {
    launch { doWorld() }
    println("Hello,")
}

// 这是你的第一个挂起函数
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

//协程很轻量
fun main() = runBlocking {
    val beginTime = System.currentTimeMillis()
//    val endTime = testKotlin() //耗时: 1689
//    println("耗时: ${endTime - beginTime}")

//    val endTime = testJavaCachePool()//耗时: 23585


    //耗时:
    //todo 2020-08-10 17:12:43 有问题
    testJavaSchedulePool { endTime ->
        println("耗时: ${endTime - beginTime}")
    }

    //耗时:2295
//    testJavaFastestPool { endTime ->
//        println("耗时: ${endTime - beginTime}")
//    }

}

suspend fun testKotlin(block: (time: Long) -> Unit) {
    withContext(Dispatchers.Default) {

        var count: Long = 0L
        repeat(100_000) { // 启动大量的协程
            launch {
                delay(1000L)
                print("$it .")
                count++
            }

            if (count == 100_000L) {
                block(System.currentTimeMillis())
            }
        }
    }

}

suspend fun testJavaCachePool(block: (time: Long) -> Unit) {
    withContext(Dispatchers.Default) {
        val pool = Executors.newCachedThreadPool()
        Thread.sleep(1000)

        var count: Long = 0L
        repeat(100_000) { // 启动大量的协程
            pool.execute(Runnable {
                print("$it .")
            })

            val future: Future<Boolean> = pool.submit(Callable<Boolean> {
                print("$it .")
                true
            })
            if (future.get()) count++
            if (count == 100_000L) {
                block(System.currentTimeMillis())
            }
        }
    }
}

suspend fun testJavaSchedulePool(block: (time: Long) -> Unit) {
    withContext(Dispatchers.Default) {
        val pool = Executors.newSingleThreadScheduledExecutor()
        var count: Long = 0L

        repeat(100_000) { // 启动大量的协程
            val callable = Callable<Boolean> {
                print("$it .")
                count++
                true
            }
            pool.schedule(callable, 1, TimeUnit.SECONDS)
            if (count == 100_000L) {
                block(System.currentTimeMillis())
            }
        }
    }
}

suspend fun testJavaFastestPool(block: (time: Long) -> Unit) {
    withContext(Dispatchers.Default) {
        val pool = Executors.newSingleThreadExecutor()
        Thread.sleep(1000)

        var count: Long = 0L
        repeat(100_000) { // 启动大量的协程
            pool.execute(Runnable {
                print("$it .")
            })

            val future: Future<Boolean> = pool.submit(Callable<Boolean> {
                print("$it .")
                true
            })
            if (future.get()) count++
            if (count == 100_000L) {
                block(System.currentTimeMillis())
            }
        }

    }
}

class BasicCoroutines {

    var i = 0
    fun test(): Unit {
        GlobalScope.launch {
            var imageAvatar = suspendImageAvatar()

            async { }
            async { }

//            setAvatar(imageAvatar)
        }
    }

    private suspend fun suspendImageAvatar() {
        withContext(Dispatchers.IO) {

        }
    }

}