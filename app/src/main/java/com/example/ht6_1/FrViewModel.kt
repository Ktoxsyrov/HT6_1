package com.example.ht6_1

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.util.*
import kotlin.random.Random

class FrViewModel: ViewModel() {
    val numbers = MutableLiveData<Int>()
    val secondsPassed = MutableLiveData<Int>()
    var i = 0
    var job: Job? = null
    var isRunning: Boolean = true
    val threadLocker = Object()
    fun start(){
        val numbersThread = Thread {
            while (true) {
                Thread.sleep(50)
                numbers.postValue(Random.nextInt(1, 9))
                synchronized(threadLocker) {
                    while (!isRunning)
                        try {
                            threadLocker.wait()
                        } catch (e: InterruptedException) {
                        }
                }
            }
        }

        numbersThread.start()

        val timerThread = Thread {
            while (true) {
                Thread.sleep(10)
                i += 1
                secondsPassed.postValue(i)
                synchronized(threadLocker) {
                    while (!isRunning)
                        try {
                            threadLocker.wait()
                        } catch (e: InterruptedException) {
                        }
                }
            }
        }
        timerThread.start()
    }
    fun reset(){
        numbers.postValue(-1)
        secondsPassed.postValue(0)
        i = 0
    }
    fun pause(){
        synchronized(threadLocker) {
            isRunning = false
        }
    }
    fun run(){
        synchronized(threadLocker) {
            isRunning = true
            threadLocker.notifyAll()
        }
    }
}