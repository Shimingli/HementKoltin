package com.lsm.hementkoltin.ui.fragment

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.weight.MultipleStatusView
import com.lsm.hementkoltin.R
import kotlinx.android.synthetic.main.toolbar.*


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/8/21 10:13
 */
class MiddleFragment :BaseMainFragment(){

    companion object{

        fun newInstance(): MiddleFragment {
            val args = Bundle()
            val fragment = MiddleFragment()
            fragment.arguments = args
            return fragment
        }
    }



    override fun initListener() {
        mToolBar.title = "MiddleFragment"
    }

    override fun getPresenter(): LifecycleObserver? {
        return null
    }

    override fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    override fun getLayoutId(): Int = R.layout.fragment_tab_middle

    override fun lazyLoad() {

    }

}