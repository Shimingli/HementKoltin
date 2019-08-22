package com.lsm.hementkoltin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.weight.MultipleStatusView
import com.lsm.hementkoltin.MainActivity
import com.lsm.hementkoltin.R
import kotlinx.android.synthetic.main.fragment_tab_middle.*
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
class MiddleFragment :BaseMainFragment(), View.OnClickListener {


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
        mCardViewTop.setOnClickListener(this)
        mCardViewOne.setOnClickListener(this)
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
    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.mCardViewTop->{
               (parentFragment as MainFragment).startBrotherFragment(WarningLightsFragment.newInstance())
           }
           R.id.mCardViewOne->{
               (parentFragment as MainFragment).startBrotherFragment(FlickerLightsFragment.newInstance())
           }
       }
    }
}