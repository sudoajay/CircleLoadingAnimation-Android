package com.sudoajay.circleloadinganimation.component.finish

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import com.sudoajay.circleloadinganimation.animator.AnimationState
import com.sudoajay.circleloadinganimation.component.ComponentViewAnimation

/**
 * @author jlmd
 */
abstract class FinishedView(
    context: Context?, parentWidth: Int, mainColor: Int, secondaryColor: Int,
    protected val tintColor: Int
) : ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var maxImageSize = 0
    private var circleMaxRadius = 0
    private var originalFinishedBitmap: Bitmap? = null
    private var currentCircleRadius = 0f
    private var imageSize = 0
    private fun init() {
        maxImageSize = 140 * parentWidth / 700
        circleMaxRadius = 140 * parentWidth / 700
        currentCircleRadius = circleRadius
        imageSize = MIN_IMAGE_SIZE
        originalFinishedBitmap = BitmapFactory.decodeResource(resources, drawable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
        drawCheckedMark(canvas)
    }

    private fun drawCheckedMark(canvas: Canvas) {
        val paint = Paint()
        paint.colorFilter = LightingColorFilter(drawableTintColor, 0)
        val bitmap = Bitmap.createScaledBitmap(originalFinishedBitmap!!, imageSize, imageSize, true)
        canvas.drawBitmap(
            bitmap, parentCenter - bitmap.width / 2,
            parentCenter - bitmap.height / 2, paint
        )
    }

    fun drawCircle(canvas: Canvas) {
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = circleColor
        paint.isAntiAlias = true
        canvas.drawCircle(parentCenter, parentCenter, currentCircleRadius, paint)
    }

    fun startScaleAnimation() {
        startScaleCircleAnimation()
        startScaleImageAnimation()
    }

    private fun startScaleCircleAnimation() {
        val valueCircleAnimator =
            ValueAnimator.ofFloat(circleRadius + strokeWidth / 2, circleMaxRadius.toFloat())
        valueCircleAnimator.duration = 1000
        valueCircleAnimator.addUpdateListener { animation ->
            currentCircleRadius = animation.animatedValue as Float
            invalidate()
        }
        valueCircleAnimator.start()
    }

    private fun startScaleImageAnimation() {
        val valueImageAnimator = ValueAnimator.ofInt(MIN_IMAGE_SIZE, maxImageSize)
        valueImageAnimator.duration = 1000
        valueImageAnimator.addUpdateListener { animation ->
            imageSize = animation.animatedValue as Int
            invalidate()
        }
        valueImageAnimator.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.ANIMATION_END)
            }

            override fun onAnimationCancel(animation: Animator) {
                // Empty
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Empty
            }
        })
        valueImageAnimator.start()
    }

    protected abstract val drawable: Int
    protected abstract val drawableTintColor: Int
    protected abstract val circleColor: Int

    companion object {
        private const val MIN_IMAGE_SIZE = 1
    }

    init {
        init()
    }
}