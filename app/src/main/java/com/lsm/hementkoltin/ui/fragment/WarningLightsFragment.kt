package com.lsm.hementkoltin.ui.fragment

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.BaseFragment
import com.lsm.base.weight.MultipleStatusView
import com.lsm.hementkoltin.R


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/8/22 19:26
 */
class WarningLightsFragment: BaseFragment(){

    companion object{

        fun newInstance(): WarningLightsFragment {
            val args = Bundle()
            val fragment = WarningLightsFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun initListener() {
//        mToolBar.title = "WarningLightsFragment"
    }

    override fun getPresenter(): LifecycleObserver? {
        return null
    }

    override fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    override fun getLayoutId(): Int = R.layout.fragment_warning_lishts_layout

    override fun lazyLoad() {

    }

}