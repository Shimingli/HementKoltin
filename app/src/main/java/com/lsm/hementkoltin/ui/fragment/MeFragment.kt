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
class MeFragment :BaseMainFragment(){

    companion object{

        fun newInstance(): MeFragment {
            val args = Bundle()
            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun initListener() {
        mToolBar.title = "MeFragment"
    }

    override fun getPresenter(): LifecycleObserver? {
        return null
    }

    override fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    override fun getLayoutId(): Int = R.layout.fragment_tab_me

    override fun lazyLoad() {

    }

}