package com.sudoajay.circleloadinganimation.component.finish

import android.content.Context
import com.sudoajay.circleloadinganimation.R

/**
 * @author jlmd
 */
open class FinishedOkView(
    context: Context?, parentWidth: Int, mainColor: Int, secondaryColor: Int,
    tintColor: Int
) : FinishedView(context, parentWidth, mainColor, secondaryColor, tintColor) {
    override val drawable: Int
        get() = R.drawable.ic_checked_mark
    override val drawableTintColor: Int
        get() = tintColor
    override val circleColor: Int
        get() = mainColor
}