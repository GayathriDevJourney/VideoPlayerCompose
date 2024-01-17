package com.gayathri.videplayercompose

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    println("Main Starts")
    val job = GlobalScope.launch {
        println("Coroutine Starts")
        delay(1000L)
        println("Coroutine Ends")
    }
    job.join()
    println("Main Starts")
}
