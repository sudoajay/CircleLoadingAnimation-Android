package com.sudoajay.circleloadinganimation.component

import android.animation.Animator
import android.animation.Animator.AnimatorListener
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
class MainCircleView(context: Context?, parentWidth: Int, mainColor: Int, secondaryColor: Int) :
    ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var paint: Paint? = null
    private var oval: RectF? = null
    private var arcFillAngle = 0
    private var arcStartAngle = 0
    private fun init() {
        initPaint()
        initOval()
    }

    private fun initPaint() {
        paint = Paint()
        paint!!.color = mainColor
        paint!!.strokeWidth = strokeWidth.toFloat()
        paint!!.style = Paint.Style.FILL_AND_STROKE
        paint!!.isAntiAlias = true
    }

    private fun initOval() {
        val padding = paint!!.strokeWidth / 2
        oval = RectF()
        oval!![parentCenter - circleRadius, padding, parentCenter + circleRadius] = circleRadius * 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawArcs(canvas)
    }

    private fun drawArcs(canvas: Canvas) {
        canvas.drawArc(oval!!, arcStartAngle.toFloat(), arcFillAngle.toFloat(), false, paint!!)
    }

    fun startFillCircleAnimation() {
        val valueAnimator = ValueAnimator.ofInt(90, 360)
        valueAnimator.duration = 800
        valueAnimator.addUpdateListener { animation ->
            arcStartAngle = animation.animatedValue as Int
            arcFillAngle = (90 - arcStartAngle) * 2
            invalidate()
        }
        valueAnimator.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.MAIN_CIRCLE_FILLED_TOP)
            }

            override fun onAnimationCancel(animation: Animator) {
                // Empty
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Empty
            }
        })
        valueAnimator.start()
    }

    init {
        init()
    }
}