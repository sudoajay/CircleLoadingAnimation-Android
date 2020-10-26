package com.sudoajay.circleloadinganimation.component

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.animation.DecelerateInterpolator
import com.sudoajay.circleloadinganimation.animator.AnimationState

/**
 * @author jlmd
 */
class TopCircleBorderView(
    context: Context?,
    parentWidth: Int,
    mainColor: Int,
    secondaryColor: Int
) : ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var paint: Paint? = null
    private var oval: RectF? = null
    private var arcAngle = 0
    private fun init() {
        initPaint()
        initOval()
        arcAngle = MIN_ANGLE
    }

    private fun initPaint() {
        paint = Paint()
        paint!!.color = mainColor
        paint!!.strokeWidth = strokeWidth.toFloat()
        paint!!.style = Paint.Style.STROKE
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
        canvas.drawArc(oval!!, 270f, arcAngle.toFloat(), false, paint!!)
        canvas.drawArc(oval!!, 270f, -arcAngle.toFloat(), false, paint!!)
    }

    fun startDrawCircleAnimation() {
        val valueAnimator = ValueAnimator.ofInt(MIN_ANGLE, MAX_ANGLE)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = 400
        valueAnimator.addUpdateListener { animation ->
            arcAngle = animation.animatedValue as Int
            invalidate()
        }
        valueAnimator.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.MAIN_CIRCLE_DRAWN_TOP)
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

    companion object {
        private const val MIN_ANGLE = 25
        private const val MAX_ANGLE = 180
    }

    init {
        init()
    }
}