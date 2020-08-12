package practice.coroutines

import kotlinx.coroutines.runBlocking

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main(){

    //命名协程以用于调试 -> -Dkotlinx.coroutines.debug
    main37()

    runBlocking<Unit>{

    }
}

