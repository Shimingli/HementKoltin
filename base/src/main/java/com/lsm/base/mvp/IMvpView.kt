package com.lsm.base.mvp

import com.lsm.base.BaseApplication
import com.lsm.base.utils.ToastNativeLayoutUtils

/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 15:11
 */
 interface IMvpView {

    /**
     * 数据失败的同一处理
     * @param errorCode
     * @param errorMsg
     */
    fun getDataFail(errorCode: String, errorMsg: String){

    }

    /**
     * 发成异常的同一处理
     * @param e
     */
    fun onError(e: Throwable){

    }

    /**
     * 重新登录
     */
   open fun checkLogin(code: Int, message: String?) {
         //ToastNativeLayoutUtils.toast(BaseApplication.context,message!!)
    }

    fun showLoading(){

    }

    fun dismissLoading(){

    }
}