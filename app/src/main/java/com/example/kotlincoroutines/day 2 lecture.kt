package com.example.kotlincoroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

//shared flow:
//suspend, buffer last something, before emission and after emission , drop latest
//prof mahmoud samy of stats was the one who showed me the correct way to learn
//i'd follow along, he'd hive us time to think, then he'd solve it with us later after we'd have tried it

suspend fun main():Unit = coroutineScope{
    val sharedflow = MutableSharedFlow<Int>(/*replay = 3*/) //instance, what for?

    launch{
        sharedflow.collect{
            //action done when recieving data ?
            println("first = $it")
        }
    }

    launch{
        sharedflow.collect{
            //action done when recieving data ?
            println("second = $it")
        }
    }


    sharedflow.emit(1)
    sharedflow.emit(2)
    sharedflow.emit(3)
    sharedflow.emit(4)
    sharedflow.emit(5)//some data that will be recieved by our subscribers above
//
//    launch{
//        sharedflow.collect{
//            //action done when recieving data ?
//            println("second = $it")
//        }
//    }

    val stateflow = MutableStateFlow<Int>(0)

    launch {
        stateflow.collect{
            println("stateflow $it")
        }
    }

    stateflow.emit(6)
    stateflow.emit(7)
    stateflow.emit(8)
    stateflow.emit(9)
    stateflow.emit(10)






}


