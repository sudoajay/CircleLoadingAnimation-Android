package com.sudoajay.circleloadinganimation.component

import android.content.Context
import android.view.View
import com.sudoajay.circleloadinganimation.animator.AnimationState
import com.sudoajay.circleloadinganimation.exception.NullStateListenerException

/**
 * @author jlmd
 */
abstract class ComponentViewAnimation(
    context: Context?, protected val parentWidth: Int, protected val mainColor: Int,
    protected val secondaryColor: Int
) : View(context) {
    @JvmField
    protected var parentCenter = 0f
    @JvmField
    protected var circleRadius = 0f
    @JvmField
    protected var strokeWidth = 0
    private var stateListener: StateListener? = null
    private fun init() {
        hideView()
        circleRadius = parentWidth / 10.toFloat()
        parentCenter = parentWidth / 2.toFloat()
        strokeWidth = 12 * parentWidth / 700
    }

    fun hideView() {
        visibility = GONE
    }

    fun showView() {
        visibility = VISIBLE
    }

    fun setState(state: AnimationState?) {
        if (stateListener != null) {
            stateListener!!.onStateChanged(state)
        } else {
            throw NullStateListenerException()
        }
    }

    fun setStateListener(stateListener: StateListener?) {
        this.stateListener = stateListener
    }

    interface StateListener {
        fun onStateChanged(state: AnimationState?)
    }

    init {
        init()
    }
}