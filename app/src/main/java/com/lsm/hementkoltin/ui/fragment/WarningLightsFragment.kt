package com.lsm.hementkoltin.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.BaseFragment
import com.lsm.base.weight.MultipleStatusView
import com.lsm.hementkoltin.R
import kotlinx.android.synthetic.main.fragment_warning_lishts_layout.*


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/8/22 19:26
 */
class WarningLightsFragment: BaseFragment(), View.OnClickListener {


    override fun onClick(p0: View?) {
        pop()
    }

    private var mWarningLightState = false
    companion object{

        fun newInstance(): WarningLightsFragment {
            val args = Bundle()
            val fragment = WarningLightsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    //因为在子线程中更新UI， 所以需要使用Handler处理消息
    @SuppressLint("HandlerLeak")
    private val mWarningHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            mWarningLightState = if (mWarningLightState) {
                warn_top?.setImageResource(R.drawable.w_warning_light_on)
                warn_bottom?.setImageResource(R.drawable.w_warning_light_off)
                false
            } else {
                warn_top?.setImageResource(R.drawable.w_warning_light_off)
                warn_bottom?.setImageResource(R.drawable.w_warning_light_on)
                true
            }
            this.sendEmptyMessageDelayed(0, 1000)
        }
    }
    override fun initListener() {
        mWarningHandler.sendEmptyMessageDelayed(0, 0)
        back_warn.setOnClickListener(this)
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

    override fun onDestroy() {
        super.onDestroy()
        mWarningHandler.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        mWarningHandler.removeCallbacksAndMessages(null)
    }
}