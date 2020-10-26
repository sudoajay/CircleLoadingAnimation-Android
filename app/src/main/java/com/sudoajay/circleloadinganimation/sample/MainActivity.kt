package com.sudoajay.circleloadinganimation.sample

import android.app.Activity
import android.os.Bundle
import com.sudoajay.circleloadinganimation.AnimatedCircleLoadingView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : Activity() {
    private var animatedCircleLoadingView: AnimatedCircleLoadingView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animatedCircleLoadingView = circle_loading_view
        startLoading()
        startPercentMockThread()

    }

    private fun startLoading() {
        animatedCircleLoadingView!!.startDeterminate()
    }

    private fun startPercentMockThread() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            for (i in 0..100) {
                delay(100)
                withContext(Dispatchers.Main) {
                    changePercent(i)
                }
            }
        }
    }

    private fun changePercent(percent: Int) {
        animatedCircleLoadingView!!.setPercent(percent)
    }

    fun resetLoading() {
        animatedCircleLoadingView!!.resetLoading()
    }
}