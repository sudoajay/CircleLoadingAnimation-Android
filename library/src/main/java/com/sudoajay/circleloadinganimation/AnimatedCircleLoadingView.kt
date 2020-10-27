package com.sudoajay.circleloadinganimation

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.MutableLiveData
import com.sudoajay.circleloadinganimation.animator.ViewAnimator
import com.sudoajay.circleloadinganimation.component.*
import com.sudoajay.circleloadinganimation.component.finish.FinishedFailureView
import com.sudoajay.circleloadinganimation.component.finish.FinishedOkView

/**
 * @author jlmd
 */
class AnimatedCircleLoadingView : FrameLayout {

    private var initialCenterCircleView: InitialCenterCircleView? = null
    private var mainCircleView: MainCircleView? = null
    private var rightCircleView: RightCircleView? = null
    private var sideArcsView: SideArcsView? = null
    private var topCircleBorderView: TopCircleBorderView? = null
    private var finishedOkView: FinishedOkView? = null
    private var finishedFailureView: FinishedFailureView? = null
    private var percentIndicatorView: PercentIndicatorView? = null
    private var viewAnimator: ViewAnimator? = null
    private var animationListener: AnimationListener? = null
    private var startAnimationIndeterminate = false
    private var startAnimationDeterminate = false
    private var stopAnimationOk = false
    private var stopAnimationFailure = false
    private var mainColor = 0
    private var secondaryColor = 0
    private var checkMarkTintColor = 0
    private var failureMarkTintColor = 0
    private var textColor = 0
    var progressFinished: MutableLiveData<Boolean> = MutableLiveData()


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.AnimatedCircleLoadingView)
        mainColor = attributes.getColor(
            R.styleable.AnimatedCircleLoadingView_animCircleLoadingView_mainColor,
            Color.parseColor(DEFAULT_HEX_MAIN_COLOR)
        )
        secondaryColor = attributes.getColor(
            R.styleable.AnimatedCircleLoadingView_animCircleLoadingView_secondaryColor,
            Color.parseColor(DEFAULT_HEX_SECONDARY_COLOR)
        )
        checkMarkTintColor = attributes.getColor(
            R.styleable.AnimatedCircleLoadingView_animCircleLoadingView_checkMarkTintColor,
            Color.parseColor(DEFAULT_HEX_TINT_COLOR)
        )
        failureMarkTintColor = attributes.getColor(
            R.styleable.AnimatedCircleLoadingView_animCircleLoadingView_failureMarkTintColor,
            Color.parseColor(DEFAULT_HEX_TINT_COLOR)
        )
        textColor = attributes.getColor(
            R.styleable.AnimatedCircleLoadingView_animCircleLoadingView_textColor,
            Color.parseColor(DEFAULT_HEX_TEXT_COLOR)
        )
        attributes.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        init()
        startAnimation()
    }

    private fun startAnimation() {
        if (width != 0 && height != 0) {
            if (startAnimationIndeterminate) {
                viewAnimator!!.startAnimator()
                startAnimationIndeterminate = false
            }
            if (startAnimationDeterminate) {
                addView(percentIndicatorView)
                viewAnimator!!.startAnimator()
                startAnimationDeterminate = false
            }
            if (stopAnimationOk) {
                stopOk()
            }
            if (stopAnimationFailure) {
                stopFailure()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Force view to be a square
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    private fun init() {
        initComponents()
        addComponentsViews()
        initAnimatorHelper()
    }

    private fun initComponents() {
        val width = width
        initialCenterCircleView = InitialCenterCircleView(context, width, mainColor, secondaryColor)
        rightCircleView = RightCircleView(context, width, mainColor, secondaryColor)
        sideArcsView = SideArcsView(context, width, mainColor, secondaryColor)
        topCircleBorderView = TopCircleBorderView(context, width, mainColor, secondaryColor)
        mainCircleView = MainCircleView(context, width, mainColor, secondaryColor)
        finishedOkView =
            FinishedOkView(context, width, mainColor, secondaryColor, checkMarkTintColor)
        finishedFailureView =
            FinishedFailureView(context, width, mainColor, secondaryColor, failureMarkTintColor)
        percentIndicatorView = PercentIndicatorView(context, width, textColor)
    }

    private fun addComponentsViews() {
        addView(initialCenterCircleView)
        addView(rightCircleView)
        addView(sideArcsView)
        addView(topCircleBorderView)
        addView(mainCircleView)
        addView(finishedOkView)
        addView(finishedFailureView)
    }

    private fun initAnimatorHelper() {
        viewAnimator = ViewAnimator()
        viewAnimator!!.setAnimationListener(animationListener)
        viewAnimator!!.setComponentViewAnimations(
            initialCenterCircleView, rightCircleView, sideArcsView,
            topCircleBorderView, mainCircleView, finishedOkView, finishedFailureView,
            percentIndicatorView, progressFinished
        )
    }

    fun startIndeterminate() {
        startAnimationIndeterminate = true
        startAnimation()
    }

    fun startDeterminate() {
        startAnimationDeterminate = true
        startAnimation()
    }

    fun setPercent(percent: Int) {
        if (percentIndicatorView != null) {
            percentIndicatorView!!.setPercent(percent)
            if (percent == 100) {
                viewAnimator!!.finishOk()
            }
        }
    }

    fun stopOk() {
        if (viewAnimator == null) {
            stopAnimationOk = true
        } else {
            viewAnimator!!.finishOk()
            percentIndicatorView = null
        }
    }

    fun stopFailure() {
        if (viewAnimator == null) {
            stopAnimationFailure = true
        } else {
            viewAnimator!!.finishFailure()
            percentIndicatorView = null
        }
    }

    fun resetLoading() {
        if (viewAnimator != null) {
            viewAnimator!!.resetAnimator()
        }
        setPercent(0)
    }

    fun setAnimationListener(animationListener: AnimationListener?) {
        this.animationListener = animationListener
    }

    interface AnimationListener {
        fun onAnimationEnd(success: Boolean)
    }

    companion object {
        private const val DEFAULT_HEX_MAIN_COLOR = "#FF9A00"
        private const val DEFAULT_HEX_SECONDARY_COLOR = "#BDBDBD"
        private const val DEFAULT_HEX_TINT_COLOR = "#FFFFFF"
        private const val DEFAULT_HEX_TEXT_COLOR = "#FFFFFF"
    }
}