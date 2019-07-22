package com.lsm.base.utils

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.Toast
import android.app.Activity
import android.content.Context



/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/4/27 16:58
 */
public object ToastNativeLayoutUtils{

    private var sToast: Toast? = null

    /**
     * 显示一个Toast
     *
     * @param context  Context
     * @param strResId 字符串资源ID
     */
     fun toast(context: Context, strResId: Int) {
        toast(context, strResId, Toast.LENGTH_SHORT)
    }

    /**
     * 显示一个Toast
     *
     * @param context Context
     * @param msg     信息
     */
    fun toast(context: Context, msg: String) {
        toast(context, msg, Toast.LENGTH_SHORT)
    }

    /**
     * 显示一个Toast
     *
     * @param context  Context
     * @param strResId 字符串资源ID
     * @param time     显示时间
     */
    fun toast(context: Context, strResId: Int, time: Int) {
        toast(context, context.getString(strResId), time)
    }

    /**
     * 显示一个Toast
     *
     * @param context Context
     * @param msg     信息
     * @param time    显示时间
     */
    @SuppressLint("ShowToast")
    fun toast(context: Context?, msg: String, time: Int) {
        if (context == null || context is Activity && (context as Activity).isFinishing) {
            return
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, time)
        }
        //sToast!!.view.setBackgroundColor(context.resources.getColor(R.color.clr_000000))
        sToast!!.setText(msg)
        sToast!!.duration = time
        sToast!!.setGravity(Gravity.CENTER, 0, 0)
        sToast!!.show()
    }

}