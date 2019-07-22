package com.lsm.base.utils

import android.content.Context
import android.util.DisplayMetrics
import com.xfxb.paperless.base.BaseActivity

/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/4/27 16:07
 */
object DisplayManager{

    private var displayMetrics: DisplayMetrics? = null

    private var screenWidth: Int? = null

    private var screenHeight: Int? = null

    private var screenDpi: Int? = null

    private var density: Float? = null

    private var width: Int? = null

    private var height: Int? = null

    fun init(context: Context) {
        displayMetrics = context.resources.displayMetrics
        screenWidth = displayMetrics?.widthPixels
        screenHeight = displayMetrics?.heightPixels
        screenDpi = displayMetrics?.densityDpi
        density = displayMetrics?.density
    }

    fun getDensity(): Float? {
        return density
    }

    fun getScreenWidth(): Int? {
        return screenWidth
    }
    fun getScreenHeight(): Int? {
        return screenHeight
    }


    fun dip2px(dipValue: Int): Float? {
        return (dipValue * density!! + 0.5f)
    }


    fun initActivity(baseActivity: BaseActivity) {
           width=baseActivity.windowManager.defaultDisplay.width
           height=baseActivity.windowManager.defaultDisplay.height
    }

}