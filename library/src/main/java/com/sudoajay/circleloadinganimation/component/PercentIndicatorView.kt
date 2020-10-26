package com.sudoajay.circleloadinganimation.component

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.animation.AlphaAnimation
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author jlmd
 */
class PercentIndicatorView(context: Context?, private val parentWidth: Int, textColor: Int) :
    AppCompatTextView(
        context!!
    ) {
    private var textColor: Int? = null
    private fun init() {
        val textSize = 35 * parentWidth / 700
        setTextSize(textSize.toFloat())
        setTextColor(textColor!!)
        gravity = Gravity.CENTER
        alpha = 0.8f
    }

    fun setPercent(percent: Int) {
        text = "$percent%"
    }

    fun startAlphaAnimation() {
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = 700
        alphaAnimation.fillAfter = true
        startAnimation(alphaAnimation)
    }

    init {
        this.textColor = textColor
        init()
    }
}