package com.lsm.hementkoltin



import androidx.lifecycle.LifecycleObserver
import com.lsm.base.BaseActivity
import com.lsm.hementkoltin.ui.fragment.HomePageFragment
import com.lsm.hementkoltin.ui.fragment.MeFragment
import com.lsm.hementkoltin.ui.fragment.MiddleFragment
import com.lsm.hementkoltin.ui.view.BottomBar
import com.lsm.hementkoltin.ui.view.BottomBarTab
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * AppCompatActivity  继承他 就可以 我日了狗了哦
 * 为什么 你知道么？你就是一个大傻逼 草拟吗
 * 因为你的基类有些使用了kotlin呀 难受的很 妈的
 */
class MainActivity : BaseActivity() {


    companion object{

        private val REQ_MSG = 10
        val FIRST = 0
        val SECOND = 1
        val THIRD = 2
        private val mFragments = arrayOfNulls<SupportFragment>(3)


    }



    override fun initListener() {
        val firstFragment = findFragment(HomePageFragment::class.java)
        if (firstFragment == null) {
            mFragments[FIRST] = HomePageFragment.newInstance()
            mFragments[SECOND] = MiddleFragment.newInstance()
            mFragments[THIRD] = MeFragment.newInstance()

            loadMultipleRootFragment(
                R.id.fl_tab_container, FIRST,
                mFragments[FIRST],
                mFragments[SECOND],
                mFragments[THIRD]
            )
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findFragment(MiddleFragment::class.java)
            mFragments[THIRD] = findFragment(MeFragment::class.java)
        }


        mBottomBar
            .addItem(BottomBarTab(this, R.drawable.ic_message_white_24dp, getString(R.string.home)))
            .addItem(BottomBarTab(this, R.drawable.ic_account_circle_white_24dp, getString(R.string.middle)))
            .addItem(BottomBarTab(this, R.drawable.ic_discover_white_24dp, getString(R.string.more)))

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

        fl_tab_container
    }

    override fun getPresenter(): LifecycleObserver? {
      return null
    }

    override fun initData() {

    }

    override fun layoutId(): Int=R.layout.activity_main


    override fun onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return DefaultHorizontalAnimator()
    }
}
