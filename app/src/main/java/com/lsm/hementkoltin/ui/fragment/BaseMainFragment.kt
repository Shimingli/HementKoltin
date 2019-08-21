package com.lsm.hementkoltin.ui.fragment

import android.widget.Toast
import com.lsm.base.BaseFragment
import com.lsm.base.utils.ToastNativeLayoutUtils
import com.lsm.hementkoltin.R

/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/8/21 10:11
 */
abstract class BaseMainFragment :BaseFragment(){

    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun onBackPressedSupport(): Boolean {

        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish()
        } else {
            TOUCH_TIME = System.currentTimeMillis()
            ToastNativeLayoutUtils.toast(_mActivity,"再按一次退出")
        }
        return super.onBackPressedSupport()
    }

}