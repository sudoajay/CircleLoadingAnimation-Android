package com.sudoajay.circleloadinganimation.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import com.sudoajay.circleloadinganimation.animator.AnimationState
import com.sudoajay.circleloadinganimation.component.ComponentViewAnimation

/**
 * @author jlmd
 */
class RightCircleView(context: Context?, parentWidth: Int, mainColor: Int, secondaryColor: Int) :
    ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var rightMargin = 0
    private var bottomMargin = 0
    private var paint: Paint? = null

    private fun init() {
        rightMargin = 150 * parentWidth / 700
        bottomMargin = 50 * parentWidth / 700
        initPaint()
    }

    private fun initPaint() {
        paint = Paint()
        paint!!.style = Paint.Style.FILL
        paint!!.color = secondaryColor
        paint!!.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
    }

    fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(
            width - rightMargin.toFloat(),
            parentCenter - bottomMargin,
            circleRadius,
            paint!!
        )
    }

    fun startSecondaryCircleAnimation() {
        val bottomMovementAddition = 260 * parentWidth / 700
        val translateAnimation = TranslateAnimation(x, x, y, y + bottomMovementAddition)
        translateAnimation.startOffset = 200
        translateAnimation.duration = 1000

        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.startOffset = (1300).toLong()
        alphaAnimation.duration = (200).toLong()

        val animationSet = AnimationSet(true)
        animationSet.addAnimation(translateAnimation)
        animationSet.addAnimation(alphaAnimation)
        animationSet.fillAfter = true
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animation) {
                setState(AnimationState.SECONDARY_CIRCLE_FINISHED)
            }

            override fun onAnimationRepeat(animation: Animation) {
                // Empty
            }
        })
        startAnimation(animationSet)
    }

    init {
        init()
    }
}