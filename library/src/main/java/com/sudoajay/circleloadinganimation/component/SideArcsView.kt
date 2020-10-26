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
class SideArcsView(context: Context?, parentWidth: Int, mainColor: Int, secondaryColor: Int) :
    ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var startLeftArcAngle = INITIAL_LEFT_ARC_START_ANGLE
    private var startRightArcAngle = INITIAL_RIGHT_ARC_START_ANGLE
    private var paint: Paint? = null
    private var oval: RectF? = null
    private var arcAngle = 0
    private val addedTime = 0
    private fun init() {
        initPaint()
        arcAngle = MAX_RESIZE_ANGLE
        initOval()
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
        oval!![padding, padding, parentWidth - padding] = parentWidth - padding
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawArcs(canvas)
    }

    private fun drawArcs(canvas: Canvas) {
        canvas.drawArc(oval!!, startLeftArcAngle.toFloat(), arcAngle.toFloat(), false, paint!!)
        canvas.drawArc(oval!!, startRightArcAngle.toFloat(), -arcAngle.toFloat(), false, paint!!)
    }

    fun startRotateAnimation() {
        val valueAnimator = ValueAnimator.ofInt(MIN_START_ANGLE, MAX_START_ANGLE)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = 550 + addedTime.toLong()
        valueAnimator.addUpdateListener { animation ->
            startLeftArcAngle = INITIAL_LEFT_ARC_START_ANGLE + animation.animatedValue as Int
            startRightArcAngle = INITIAL_RIGHT_ARC_START_ANGLE - animation.animatedValue as Int
            invalidate()
        }
        valueAnimator.start()
    }

    fun startResizeDownAnimation() {
        val valueAnimator = ValueAnimator.ofInt(MAX_RESIZE_ANGLE, MIN_RESIZE_ANGLE)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = 620 + addedTime.toLong()
        valueAnimator.addUpdateListener { animation ->
            arcAngle = animation.animatedValue as Int
            invalidate()
        }
        valueAnimator.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.SIDE_ARCS_RESIZED_TOP)
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
        private const val MIN_RESIZE_ANGLE = 8
        private const val MAX_RESIZE_ANGLE = 45
        private const val INITIAL_LEFT_ARC_START_ANGLE = 100
        private const val INITIAL_RIGHT_ARC_START_ANGLE = 80
        private const val MIN_START_ANGLE = 0
        private const val MAX_START_ANGLE = 165
    }

    init {
        init()
    }
}