package com.lsm.hementkoltin.ui.fragment

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.BaseFragment
import com.lsm.base.weight.MultipleStatusView
import com.lsm.hementkoltin.R
import com.lsm.hementkoltin.ui.view.BottomBar
import com.lsm.hementkoltin.ui.view.BottomBarTab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_mian_layout.*
import me.yokeyword.fragmentation.SupportFragment

/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/8/22 19:37
 */
class MainFragment:BaseFragment(){



    companion object{

        private val REQ_MSG = 10
        val FIRST = 0
        val SECOND = 1
        val THIRD = 2
        private val mFragments = arrayOfNulls<SupportFragment>(3)


        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initListener() {
        val firstFragment = findFragment(HomePageFragment::class.java)
        if (firstFragment == null) {
            mFragments[FIRST] = HomePageFragment.newInstance()
            mFragments[SECOND] = MiddleFragment.newInstance()
            mFragments[MainFragment.THIRD] = MeFragment.newInstance()

            loadMultipleRootFragment(
                R.id.fl_tab_container, FIRST,
                mFragments[FIRST],
                mFragments[SECOND],
                mFragments[MainFragment.THIRD]
            )
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findFragment(MiddleFragment::class.java)
            mFragments[MainFragment.THIRD] = findFragment(MeFragment::class.java)
        }


        mBottomBar
            .addItem(BottomBarTab(_mActivity, R.drawable.ic_message_white_24dp, getString(R.string.home)))
            .addItem(BottomBarTab(_mActivity, R.drawable.ic_account_circle_white_24dp, getString(R.string.middle)))
            .addItem(BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp, getString(R.string.more)))

        mBottomBar.setOnTabSelectedListener(object : BottomBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int, prePosition: Int) {
                showHideFragment(mFragments[position], mFragments[prePosition])

                val tab = mBottomBar.getItem(FIRST)
                if (position == FIRST) {
                    tab.unreadCount = 0
                } else {
                    tab.unreadCount = tab.unreadCount + 1
                }
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                // EventBusActivityScope.getDefault(_mActivity).post(TabSelectedEvent(position))
            }
        })

    }

    override fun getPresenter(): LifecycleObserver? {
     return null
    }

    override fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    override fun getLayoutId(): Int = R.layout.fragment_mian_layout



    override fun lazyLoad() {

    }

    fun startBrotherFragment(newInstance: SupportFragment) {
        start(newInstance)
    }

}