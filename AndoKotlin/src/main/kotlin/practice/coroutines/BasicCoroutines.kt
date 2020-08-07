package practice.coroutines

import kotlinx.coroutines.*

/**
 * Title:协程
 * <p>
 * Description:https://www.bilibili.com/video/av67107689/
 * </p>
 * @author Changbao
 * @date 2019/9/10 9:55
 */
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