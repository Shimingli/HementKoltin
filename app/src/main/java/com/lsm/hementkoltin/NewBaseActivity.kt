package com.lsm.hementkoltin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/7/31 11:31
 */
abstract class NewBaseActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState==null){
            super.onCreate( Bundle())
        }else{
            super.onCreate(savedInstanceState)
        }
        setContentView(layoutId())
        if (getPresenter()!=null){
            lifecycle.addObserver(getPresenter()!!)
        }
        getTransmitData()
        initListener()
        initData()

    }

    /**
     * 获取上个页面传递过来的数据
     */
    protected open fun getTransmitData() {

    }

    protected open  fun initListener(){

    }

    /**
     *  网络请求的数据
     */
    protected open  fun initData(){}

    abstract fun layoutId(): Int

    abstract fun getPresenter(): LifecycleObserver?
}