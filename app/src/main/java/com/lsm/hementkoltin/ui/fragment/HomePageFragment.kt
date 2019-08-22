package com.lsm.hementkoltin.ui.fragment

import android.content.Context
import android.hardware.Camera
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.weight.MultipleStatusView
import kotlinx.android.synthetic.main.fragment_tab_first.*
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
class HomePageFragment : BaseMainFragment(), View.OnClickListener {



    private var light = false

    companion object {

        fun newInstance(): HomePageFragment {
            val args = Bundle()
            val fragment = HomePageFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun initListener() {
        mToolBar.title = "HomePageFragment"
        light_turn_off.setOnClickListener(this)
        light_turn_btn.setOnClickListener(this)
    }

    override fun getPresenter(): LifecycleObserver? {
        return null
    }

    override fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    override fun getLayoutId(): Int = com.lsm.hementkoltin.R.layout.fragment_tab_first

    override fun lazyLoad() {

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            com.lsm.hementkoltin.R.id.light_turn_off -> {

            }
            com.lsm.hementkoltin.R.id.light_turn_btn -> {
                if (!light) {
                    light_turn_btn.setImageResource(com.lsm.hementkoltin.R.drawable.turn_on)
                    light_turn_on.visibility = View.VISIBLE
                    Thread(Runnable { openFlash() }).start()
                    light = true
                } else {
                    cancel()
                    light = false
                }
            }
        }
    }

    private fun openFlash() {
        var systemService: CameraManager? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            systemService =activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager?
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val s = systemService!!.cameraIdList[0]
                    systemService.setTorchMode(s, true)
                }
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }

        }

    }
    private fun closeFlash() {
        var systemService: CameraManager? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            systemService = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager?
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val s = systemService!!.cameraIdList[0]
                    systemService.setTorchMode(s, false)
                }
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }

        }
    }
    private fun cancel() {
        Thread(Runnable { closeFlash() }).start()
        light = false
        light_turn_btn.setImageResource(com.lsm.hementkoltin.R.drawable.turn_off)
        light_turn_on.visibility = View.GONE
    }

     override fun onDestroy() {
        super.onDestroy()
    }
}