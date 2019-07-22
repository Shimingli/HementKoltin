package com.xfxb.paperless.base

import android.app.Dialog
import android.os.Build
import com.base.net.BasePresenter
import com.base.net.IMvpView
import com.xfxb.paperless.weight.LoadingDialog
import java.lang.Exception

/**
 * <p>
 *  需要展示Dialog的时候
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/22 09:57
 */
abstract class BaseImpShowDialogActivity : BaseActivity() {

    private var dialog: Dialog? = null

    override fun showLoading() {
        super.showLoading()
        baseShowDialog()
    }

    override fun dismissLoading() {
        super.dismissLoading()
        baseDismissDialog()
    }

    private fun baseShowDialog() {
        if (dialog == null) {
            dialog = LoadingDialog(this, false)
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.setCancelable(false)
            //dialog?.setCancelable(true)
            dialog?.setOnCancelListener { it ->
                it.dismiss()
                //取消网络请求
                //(getPresenter() as BasePresenter<*>).detachView()
            }
        }
        if (dialog != null) {
            if (dialog!!.isShowing) {
                return
            }else{
                //保证由于这个的问题，app不会闪退
                try {
                    if(this!=null&&!this.isFinishing){
                        dialog?.show()
                    }
                }catch (e :Exception){

                }
            }
        }

    }

    private fun baseDismissDialog() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        baseDismissDialog()
    }

}