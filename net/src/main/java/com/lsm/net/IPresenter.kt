package com.lsm.net

import com.lsm.base.mvp.IMvpView


interface IPresenter<in V: IMvpView> {

    fun attachView(mRootView: V)

    fun detachView()

}
