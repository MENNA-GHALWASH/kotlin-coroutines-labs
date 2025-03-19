package com.example.kotlincoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun channels() = GlobalScope.produce<Int>{
    for (x in 0..20 step 2){
        send(x)
    }
}

fun recieving(){
    runBlocking {
        val recieved = channels()
        recieved.consumeEach{
            println(it)
        }
    }

}
///////////////////////////////

fun getNumbers() = flow<Int>{
    for (i in  1..10){
        delay(1000)
        emit(i)
    }
}

suspend fun recieveNum(): Unit = coroutineScope {
    getNumbers().filter{ item -> item % 2 == 0 }.collect{
        println(it)
    }
}
/////////////////////////////////
fun getNumbersEven() = flow<Int>{
//    for (i in  0..40){
//        delay(1000)
//        emit(i)
//    }
    //another logic / approach
    //count = 0
    //while count <20
    //fire a random number
    //use transform to multiply it by 2 to ensure it's even
    //send it
    //receive it on the other side
    var count =0
    while (count<20){
        var num = Random.nextInt(0,100)
        num = num*2
        delay(1000)
        emit(num)
        count++
    }
}

suspend fun recieveNumEven(): Unit = coroutineScope {
    getNumbersEven().collect{
        println(it)
    }
} //very not right

suspend fun main(){
    println("channels")
    recieving()

    println("flows")
    recieveNum()

    println("even numbers")
    recieveNumEven()

}