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
import kotlinx.android.synthetic.main.fragment_flicker_lishts_layout.*
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
class FlickerLightsFragment: BaseFragment(), View.OnClickListener {

    private val mImgIds = intArrayOf(
        R.id.te_img_right, R.id.te_img_right_top, //te_img_left_top
        R.id.te_img_top, R.id.te_img_left_top, R.id.te_img_left, R.id.te_img_left_bottom, R.id.te_img_center
    )


    private var curent_index = 0

    private var flash = false

    var hashMap = HashMap<Int, Int>()

    init {
        hashMap[R.id.te_img_right] = R.color.te_img_right
        hashMap[R.id.te_img_right_top] = R.color.te_img_right_top
        hashMap[R.id.te_img_top] = R.color.te_img_top
        hashMap[R.id.te_img_left_top] = R.color.te_img_left_top
        hashMap[R.id.te_img_left] = R.color.te_img_left
        hashMap[R.id.te_img_left_bottom] = R.color.te_img_left_bottom
        hashMap[R.id.te_img_center] = R.color.te_img_center
    }

    override fun onClick(p0: View?) {
        when {
            p0?.id==R.id.back_light -> pop()
            p0?.id==R.id.light_flashing -> flash = if (!flash) {
                light_flashing.setImageResource(R.drawable.x_flash_turn_on)
                handler.sendEmptyMessageDelayed(0, 0)
                true

            } else {
                light_flashing.setImageResource(R.drawable.x_flash_turn_off)
                handler.removeCallbacksAndMessages(null)
                false
            }
            else -> hashMap[p0?.id]?.let { te_interface.setBackgroundResource(it) }
        }
    }

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            hashMap[mImgIds[curent_index]]?.let { te_interface.setBackgroundResource(it) }

            curent_index++

            if (curent_index >= mImgIds.size) {
                curent_index = 0
            }
            this.sendEmptyMessageDelayed(0, 500)
        }
    }

    companion object{

        fun newInstance(): FlickerLightsFragment {
            val args = Bundle()
            val fragment = FlickerLightsFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun initListener() {
        back_light.setOnClickListener(this)
        light_flashing.setOnClickListener(this)
        for (i in mImgIds.indices) {
            val imageView = activity?.findViewById<View>(mImgIds[i]) as ImageView
            imageView.setOnClickListener(this)
        }

    }

    override fun getPresenter(): LifecycleObserver? {
        return null
    }

    override fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    override fun getLayoutId(): Int = R.layout.fragment_flicker_lishts_layout

    override fun lazyLoad() {

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }
}