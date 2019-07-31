package com.lsm.net

import com.base.net.SubscriberListener
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber


/**
 * Created by shiming on 2017/4/3.
 * http://v.juhe.cn/todayOnhistory/queryEvent.php?key=b15674dbd34ec00ded57b369dfdabd90&date=1/1
 * 聚合数据的接口
 * 订阅者
 */

open class BaseObserver<T : Response<*>> : Observer<T> {


    constructor(baseSubscriber: SubscriberListener<T>){
        this.mBaseSubscriber=baseSubscriber
    }

    private var mBaseSubscriber: SubscriberListener<T>?=null



    override fun onError(e: Throwable) {
        Timber.tag(TAG).i("onError:$e")
        mBaseSubscriber?.onError(e)
    }

    override fun onComplete() {
        Timber.tag(TAG).i("onComplete:")
        mBaseSubscriber?.onCompleted()
    }


    override fun onSubscribe(d: Disposable) {
        //如果调用 d.dispose() 那么就不会忘下进行了
        Timber.tag(TAG).i("onSubscribe:" + d.isDisposed)
        mBaseSubscriber?.onSubscribe(d)
    }

    /**
     * 1025 登录失效，请重新登录
     * 1002 未登录或登录失效
     * @param response
     */
    override fun onNext(response: T) {
        Timber.tag(TAG).i("onNext:")
        if (mBaseSubscriber != null) {
            if (response.code === 0) {
                mBaseSubscriber?.onSuccess(response)
            } else if (response.code === 1025 || response.code === 1002) {
                //mBaseSubscriber.checkLogin(response.code, response.message)
            } else {
                mBaseSubscriber?.onFail(response.code.toString(), response.message.toString())
            }
        }

    }

    companion object {
        private val TAG = "BaseObserver"
    }

}
