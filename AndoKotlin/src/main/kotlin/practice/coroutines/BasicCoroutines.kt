package practice.coroutines

import kotlinx.coroutines.*

/**
 * Title:协程
 * <p>
 * Description:https://www.bilibili.com/video/av67107689/
 * </p>
 * @author javakam
 * @date 2020-08-07 16:50:37
 */
@Deprecated("建议配合 runBlocking 使用, 如下")
fun main2() {
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
fun main() = runBlocking<Unit> { // 开始执行主协程
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主协程在这里会立即执行
    delay(2000L)      // 延迟 2 秒来保证 JVM 存活
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