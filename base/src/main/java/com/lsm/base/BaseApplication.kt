package com.lsm.base

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.multidex.MultiDexApplication
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.Delegates


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 14:27
 */
open class BaseApplication : MultiDexApplication() {


    companion object {

        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()

        //val sActivityEventBusScopePool = ConcurrentHashMap<Activity, LazyEventBusInstance>()
    }

    private var mainThreadHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        mainThreadHandler = Handler(Looper.getMainLooper())
    }

    fun post(r: Runnable) {
        mainThreadHandler?.post(r)
    }

    fun postDelayed(r: Runnable, delayMillis: Long) {
        mainThreadHandler?.postDelayed(r, delayMillis)
    }

    fun removeCallbacks(r: Runnable) {
        mainThreadHandler?.removeCallbacks(r)
    }

}