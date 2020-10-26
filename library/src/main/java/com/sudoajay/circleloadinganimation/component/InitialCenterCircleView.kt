package com.sudoajay.circleloadinganimation.component

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.sudoajay.circleloadinganimation.animator.AnimationState
import com.sudoajay.circleloadinganimation.component.ComponentViewAnimation

/**
 * @author jlmd
 */
class InitialCenterCircleView(
    context: Context?, parentWidth: Int, mainColor: Int,
    secondaryColor: Int
) : ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var paint: Paint? = null
    private var oval: RectF? = null
    private var minRadius = 0f
    private var currentCircleWidth = 0f
    private var currentCircleHeight = 0f
    private fun init() {
        initOval()
        initPaint()
    }

    private fun initPaint() {
        paint = Paint()
        paint!!.style = Paint.Style.FILL_AND_STROKE
        paint!!.color = mainColor
        paint!!.isAntiAlias = true
    }

    private fun initOval() {
        oval = RectF()
        minRadius = 15 * parentWidth / 700.toFloat()
        currentCircleWidth = minRadius
        currentCircleHeight = minRadius
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
    }

    fun drawCircle(canvas: Canvas) {
        val oval = RectF()
        oval[parentCenter - currentCircleWidth, parentCenter - currentCircleHeight, parentCenter + currentCircleWidth] =
            parentCenter + currentCircleHeight
        canvas.drawOval(oval, paint!!)
    }

    fun startTranslateTopAnimation() {
        val translationYTo = -(255 * parentWidth) / 700.toFloat()
        val translationY = ObjectAnimator.ofFloat(this, "translationY", 0f, translationYTo)
        translationY.duration = 1100
        translationY.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.MAIN_CIRCLE_TRANSLATED_TOP)
            }

            override fun onAnimationCancel(animation: Animator) {
                // Empty
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Empty
            }
        })
        translationY.start()
    }

    fun startScaleAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(minRadius, circleRadius)
        valueAnimator.duration = 1400
        valueAnimator.addUpdateListener { animation ->
            currentCircleWidth = animation.animatedValue as Float
            currentCircleHeight = animation.animatedValue as Float
            invalidate()
        }
        valueAnimator.start()
    }

    fun startTranslateBottomAnimation() {
        val translationYFrom = -(260 * parentWidth) / 700.toFloat()
        val translationYTo = 360 * parentWidth / 700.toFloat()
        val translationY =
            ObjectAnimator.ofFloat(this, "translationY", translationYFrom, translationYTo)
        translationY.duration = 650
        translationY.start()
    }

    fun startScaleDisappear() {
        val maxScaleSize = 250 * parentWidth / 700.toFloat()
        val valueScaleWidthAnimator = ValueAnimator.ofFloat(circleRadius, maxScaleSize)
        valueScaleWidthAnimator.duration = 260
        valueScaleWidthAnimator.startDelay = 430
        valueScaleWidthAnimator.addUpdateListener { animation ->
            currentCircleWidth = animation.animatedValue as Float
            invalidate()
        }
        valueScaleWidthAnimator.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.MAIN_CIRCLE_SCALED_DISAPPEAR)
                currentCircleWidth = circleRadius + strokeWidth / 2
                currentCircleHeight = circleRadius + strokeWidth / 2
            }

            override fun onAnimationCancel(animation: Animator) {
                // Empty
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Empty
            }
        })
        valueScaleWidthAnimator.start()
        val valueScaleHeightAnimator = ValueAnimator.ofFloat(circleRadius, circleRadius / 2)
        valueScaleHeightAnimator.duration = 260
        valueScaleHeightAnimator.startDelay = 430
        valueScaleHeightAnimator.addUpdateListener { animation ->
            currentCircleHeight = animation.animatedValue as Float
            invalidate()
        }
        valueScaleHeightAnimator.start()
    }

    fun startTranslateCenterAnimation() {
        val translationYFrom = -(260 * parentWidth) / 700.toFloat()
        val translationY = ObjectAnimator.ofFloat(this, "translationY", translationYFrom, 0f)
        translationY.duration = 650
        translationY.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.MAIN_CIRCLE_TRANSLATED_CENTER)
            }

            override fun onAnimationCancel(animation: Animator) {
                // Empty
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Empty
            }
        })
        translationY.start()
    }

    init {
        init()
    }
}