package com.base.net

import android.widget.Toast
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 16:50
 */
/**
 * Created by shiming on 2017/4/3.
 */
abstract class SubscriberListener<T> {

    fun onSubscribe(disposable: Disposable) {

    }

    abstract fun onSuccess(response: T)

    //why  后台定义来改动
    abstract fun onFail(errorCode: String, errorMsg: String)

    /**
     * 这个异常子类最好去实现，因为发生了异常，需要菊花圈停止转动，等等 ui上的操作
     * @param e
     */
    fun onError(e: Throwable) {
        if (e is HttpException) {
            val httpException = e as HttpException
            val code = httpException.code()
            if (code == 502 || code == 404) {
                Toast.makeText(
                    BaseApplication.context,
                    R.string.server_exception_please_try_again_later,
                    Toast.LENGTH_SHORT
                ).show()
            } else if (code == 504) {
                Toast.makeText(
                    BaseApplication.context,
                    R.string.the_network_is_not_giving_strength,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(BaseApplication.context, R.string.no_network_connected, Toast.LENGTH_SHORT).show()
            }
        } else if (e is UnknownHostException || e is SocketException) {
            Toast.makeText(BaseApplication.context, R.string.no_network_connected, Toast.LENGTH_SHORT).show()
        } else if (e is SocketTimeoutException) {
            Toast.makeText(BaseApplication.context, R.string.no_network_connected, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(BaseApplication.context, R.string.no_network_connected, Toast.LENGTH_SHORT).show()
        }
    }

    fun onCompleted() {

    }

    //如果app需要检查你的账号是否在别的设备上登录，根据返回吗在这里做登录的操作
    //每当用户在app中请求了一次网络的请求的话，就会走到这个方法里，然后在这个方法中做统一的操作
    //比如退出app，重新登录。。。
    fun checkLogin(errorCode: Int, erroMsg: String) {}
}
