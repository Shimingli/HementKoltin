package com.lsm.hementkoltin


import androidx.lifecycle.LifecycleObserver
import com.lsm.base.BaseActivity

/**
 * AppCompatActivity  继承他 就可以 我日了狗了哦
 */
class MainActivity : BaseActivity() {

    override fun getPresenter(): LifecycleObserver?{
        println("shiming")
      return  null
    }


    override fun initListener() {
        println("shiming")
    }

    override fun initData() {
        println("shiming")
    }

    override fun layoutId(): Int=R.layout.activity_main



}
