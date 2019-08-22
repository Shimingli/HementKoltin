package com.lsm.hementkoltin.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.BaseFragment
import com.lsm.base.weight.MultipleStatusView
import com.lsm.hementkoltin.R
import com.lsm.hementkoltin.utils.CameraUtil.closeFlash
import com.lsm.hementkoltin.utils.CameraUtil.openFlash
import com.lsm.hementkoltin.utils.WarningTask
import kotlinx.android.synthetic.main.fragment_flicker_lishts_layout.*
import kotlinx.android.synthetic.main.fragment_screen_lishts_layout.*
import kotlinx.android.synthetic.main.fragment_warning_lishts_layout.*
import java.util.HashMap


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/8/22 19:26
 */
class ScreenLightFragment: BaseFragment(), View.OnClickListener, WarningTask.IFlashControl {



    override fun onClick(p0: View?) {
        pop()
    }



    companion object{

        fun newInstance(): ScreenLightFragment {
            val args = Bundle()
            val fragment = ScreenLightFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun initListener() {
        screen_back.setOnClickListener(this)
        selectWarningMode(true)

    }
    private var mWarningTask: WarningTask? = null

    private fun selectWarningMode(selected: Boolean) {
        if (selected) {
            mWarningTask?.stop()
            closeFlash()
            mWarningTask = WarningTask(this)
            mWarningTask?.start()
        } else {
            if (mWarningTask != null) {
                mWarningTask!!.stop()
                mWarningTask = null
            }

            openFlash()
        }
    }

    override fun closeFlash() {
        screen_layout.setBackgroundColor(-0x1000000)
    }

    override fun openFlash() {
        if (mWarningTask != null) {
            if (mWarningTask!!.counter < 6) {
                screen_layout.setBackgroundColor(-0xffff03)
            } else {
                screen_layout.setBackgroundColor(-0x30000)
            }
        }
    }


    override fun getPresenter(): LifecycleObserver? {
        return null
    }

    override fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    override fun getLayoutId(): Int = R.layout.fragment_screen_lishts_layout

    override fun lazyLoad() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mWarningTask?.stop()
    }

    override fun onPause() {
        super.onPause()
        mWarningTask?.stop()
    }
}