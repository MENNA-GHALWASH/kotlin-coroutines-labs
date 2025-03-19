package com.example.kotlincoroutines

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

val sharedflow = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 2)

suspend fun fireString(){
    sharedflow.emit("apples")
    sharedflow.emit("bananas")
    sharedflow.emit("peach")
    sharedflow.emit("oranges")
    sharedflow.emit("grapes")
}

suspend fun fireStringWithTry(){
    sharedflow.tryEmit("apples")
    sharedflow.tryEmit("bananas")
    sharedflow.tryEmit("peach")
    sharedflow.tryEmit("oranges")
    sharedflow.tryEmit("grapes")
}

suspend fun receiveAllFruits() {
    sharedflow.collect{
        println(it)
    }
}

suspend fun receiveLastFruits() {
    sharedflow.buffer(2).collectLatest{
        println(it)
    }
}

suspend fun main(): Unit = coroutineScope {
    val collectJob = launch { receiveAllFruits() } // subscribers must come first before producers
    launch { fireString() } // this is a producer

    delay(2000)

    println("\nSwitching to collectLatest...\n")

    collectJob.cancel()

    val collectjob2 = launch { receiveLastFruits() }
    delay(3000)
    launch { fireStringWithTry() }
    //launch { fireString() }
    delay(3000)

    collectjob2.cancel()
    println("\nswitching back from tryemit....\n")

    launch { receiveLastFruits() }
    launch { fireString() }

    //try enmit is not a suspend function and requires delay for its results to be seen
}