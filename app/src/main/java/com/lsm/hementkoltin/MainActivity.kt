package com.lsm.hementkoltin



import androidx.lifecycle.LifecycleObserver
import com.lsm.base.BaseActivity
import com.lsm.hementkoltin.ui.fragment.HomePageFragment
import com.lsm.hementkoltin.ui.fragment.MainFragment
import com.lsm.hementkoltin.ui.fragment.MainFragment.Companion.THIRD
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



    override fun initListener() {
        if (findFragment(MainFragment::class.java) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance())
        }
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
