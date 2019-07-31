package com.xfxb.paperless.base

import android.os.Build
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.lsm.base.BaseFragment


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/24 18:48
 */
abstract class BaseFragmentActivity: BaseImpShowDialogActivity(), View.OnClickListener {



    private var fragments: ArrayList<BaseFragment>? = null


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun initFragmentAction() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }


    /**
     * 使用replace方法显示Fragment
     * addToBackStack ：需要需要返回上一层
     * 如果你需要同步提交Fragment并且无需添加到回退栈中，则使用commitNow()。
     * Support库中在 FragmentPagerAdapter中使用这个函数，来确保更新Adapter的时候页面被正确的添加和删除。
     * 一般来说，只要不添加到回退栈中，都可以使用这个函数来提交。
    如果执行的提交不需要是同步的，或者需要将提交都添加到回退栈中，那么就使用commit()。
    如果你需要把多次提交的操作在同一个时间点一起执行，则使用 executePendingTransactions()
    如果你需要在Activity执行完onSaveInstanceState()之后还要进行提交，
    而且不关心恢复时是否会丢失此次提交，那么可以使用commitAllowingStateLoss()或commitNowAllowingStateLoss()。
     */
    fun replaceContent(fragment: BaseFragment, addToBackStack: Boolean) {
        initFragments()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(getIdRes(), fragment)
        if (addToBackStack) {
            ft.addToBackStack(null)
        } else {
            removePrevious()
        }
        //意思这个方法包含了
        ft.commitAllowingStateLoss()
        //ft.commitNow()
        //shiming
        //调用这两个方法类似于先执行commit()/commitAllowingStateLoss()然后执行executePendingTransactions()方法。
       //supportFragmentManager.executePendingTransactions()



        fragments!!.add(fragment)
        setFragment()
    }

    private fun initFragments() {
        if (fragments == null) {
            fragments = ArrayList()
        }
    }

    /**
     * 替换当前显示的Fragment
     * fragment :现在需要显示的Fragment
     * addToBackStack: 是否能够返回
     */
    fun addContent(fragment: BaseFragment, addToBackStack: Boolean) {
        initFragments()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(getIdRes(), fragment)
        
        if (addToBackStack) {
            ft.addToBackStack(null)
        } else {
            removePrevious()
        }
        ft.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
        fragments!!.add(fragment)
        setFragment()
    }

    @IdRes
    abstract fun getIdRes(): Int

    // 当前的Fragment
    private var fragment: BaseFragment? = null
    /**
     * 设置当前的Fragment
     */
    private fun setFragment() {
        if (fragments != null && fragments!!.size > 0) {
            fragment= fragments!![fragments!!.size-1]
        } else {
            fragment = null
        }
    }

    /**
     * 移除前面一个Fragment
     */
    private fun removePrevious() {
        if (fragments != null && fragments!!.size > 0) {
            fragments!!.removeAt(fragments!!.size - 1)
        }
    }

    fun backTopFragment() {
        if (fragments != null && fragments!!.size > 1) {
            removeContent()
            backTopFragment()
        }
    }

    /**
     * 移除当前的Fragment 返回到前面的Fragment
     */
    fun removeContent() {
        removePrevious()
        setFragment()
        supportFragmentManager.popBackStackImmediate()
    }

    /**
     * 从后堆栈中删除所有Fragment
     */
    protected fun removeAllStackFragment() {
        clearFragments()
        setFragment()
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    protected fun clearFragments() {
        if (fragments != null) {
            fragments!!.clear()
        }
    }

    /**
     * 获取fragment的数量
     */
    fun getFragmentNum(): Int {
        return if (fragments != null) fragments!!.size else 0
    }


}