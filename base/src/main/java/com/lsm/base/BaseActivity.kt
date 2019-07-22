package com.xfxb.paperless.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.lsm.base.ActivityManager
import com.lsm.base.BaseApplication
import com.lsm.base.mvp.IMvpView
import com.lsm.base.utils.DisplayManager
import com.lsm.base.utils.ToastNativeLayoutUtils
import com.lsm.base.weight.MultipleStatusView
import io.reactivex.disposables.CompositeDisposable
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber


/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/4/27 15:15
 */
open abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks , IMvpView {

    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    public var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragmentAction()
        ActivityManager.getInstance().addActivity(this)
        DisplayManager.initActivity(this)
        setContentView(layoutId())

        lifecycle.addObserver(getPresenter())
        getTransmitData()
        initLeftLogoLayout()
        initListener()
        initData()
        mLayoutStatusView= getMultipleStatusView()
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open fun getMultipleStatusView(): MultipleStatusView? {
        return null
    }

    open fun initFragmentAction() {

    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    /**
     * 开始请求
     */
    open fun start(){

    }

    abstract fun getPresenter(): LifecycleObserver?


    protected open fun initLeftLogoLayout() {

    }

    /**
     * 获取上个页面传递过来的数据
     */
    protected open fun getTransmitData() {

    }

    abstract fun initListener()


    /**
     *  网络请求的数据
     */
    abstract fun initData()

    abstract fun layoutId(): Int

    /**
     * 打卡软键盘
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }



    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     * com.lsm.mobilephonetools.ui.SoundMeterActivity: EasyPermissions: 获取成功的权限[android.permission.RECORD_AUDIO, android.permission.WRITE_EXTERNAL_STORAGE]
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Timber.tag(this.componentName.className).d("EasyPermissions: %s ", "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            ToastNativeLayoutUtils.toast(this, "已拒绝权限" + sb + "并不再询问")
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                .setPositiveButton("去设置")
                .setNegativeButton("关闭弹窗")
                .build()
                .show()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
        ActivityManager.getInstance().removeActivity(this)

    }

//    override fun checkLogin(code: Int, message: String?) {
//        super.checkLogin(code, message)
//        ToastNativeLayoutUtils.toast(BaseApplication.context,message!!)
//        val manager = ActivityManager.getInstance()
//        val currentActivity = manager.currentActivity
//        if (currentActivity != null && currentActivity !is LoginActivity) {  //不是在登录页
//            currentActivity.startActivity(Intent(currentActivity, LoginActivity::class.java))
//            val activityStack = manager.activityStack//遍历activity栈，清除loginActivity以外的所有activity
//            for (baseActivity in activityStack) {
//                if (baseActivity !is LoginActivity) {
//                    baseActivity.finish()
//                }
//            }
//        }
//    }

}