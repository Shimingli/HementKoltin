package com.xfxb.paperless.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.xfxb.paperless.weight.MultipleStatusView
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * <p>
 *  懒加载
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/14 14:14
 */
abstract class BaseFragment: Fragment(){
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
    /**
     * 多种状态的 View 的切换
     */
     var mLayoutStatusView: MultipleStatusView? = null

    public var compositeDisposable = CompositeDisposable()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(),null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
        mLayoutStatusView= getMultipleStatusView()
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
        initListener()
        getPresenter()?.let { lifecycle.addObserver(it) }
    }
    abstract fun initListener()
    abstract fun getPresenter(): LifecycleObserver?

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    abstract fun getMultipleStatusView():MultipleStatusView

    abstract fun getLayoutId(): Int


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Timber.tag("BaseFragment").d("setUserVisibleHint")
        Timber.tag("BaseFragment").d(isVisibleToUser.toString())
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    /**
     * && !hasLoadData
     * 需要每次可见的时候都去加载数据
     */
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare ) {
            lazyLoad()
            hasLoadData = true
        }
    }

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()

    }
}