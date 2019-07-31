package com.lsm.net

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.base.net.utils.SPUtilsConstant
import com.lsm.base.BaseApplication.Companion.context
import com.lsm.net.utils.NetworkDeviceUtils
import com.lsm.net.utils.SPUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * <p>
 *  kotlin 的单利实现
 *  Lazy是接受一个 lambda 并返回一个 Lazy 实例的函数，返回的实例可以作为实现延迟属性的委托：
 *  第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 14:16
 */
class RetrofitManager private constructor() {

    private var token: String by SPUtils(SPUtilsConstant.token, "")

    companion object {
//      谷歌爸爸提供的单利
        @Volatile private var instance: RetrofitManager? = null
        fun getInstance()=
             instance ?: synchronized(this) {
                instance ?: RetrofitManager().also {
                    instance =it
                }
            }


        fun <T> getService(servcie: Class<T>): T {
            getInstance()
            return instance?.getRetrofit()!!.create(servcie)
        }



    }

    private var retrofit: Retrofit? = null
    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_ADDRESS_PERSONAL)  //自己配置
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    private var okhttp: OkHttpClient? = null
    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //设置 请求的缓存的大小跟位置
        val cacheFile = File(context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小
        if (okhttp == null) {
            okhttp = OkHttpClient.Builder()
                .addInterceptor(addHeaderInterceptor()) // token过滤
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .cache(cache)  //添加缓存
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()
        }
        return okhttp!!
    }


    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                // Provide your custom header here
                .header("token", token)
                .header("Content-Type", "application/json")
                .header("version", BuildConfig.VERSION_NAME)
                .header("client-type", "4")
                // https://www.jianshu.com/p/3141d4e46240#HTTP%E7%BC%93%E5%AD%98%E6%9C%BA%E5%88%B6
                .header("Cache-Control", "no-cache")
                .header("Pragma", "no-cache")
//                .header("ua", getUAInfo())
//                .header("Accept-Encoding", "gzip, deflate")
//                .header("Accept-Charset", "UTF-8,*;q=0.5")
//                .header("Cache-Control", "no-cache")
//                .method(originalRequest.method(), originalRequest.body())
            //这一行错误 java.io.IOException: failed to rename
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    private fun getUAInfo(): String {
        //App版本/手机系统平台(Android、ios)/手机系统版本/手机型号
        // /渠道号/子渠道号/网卡MAC/包名/屏高/屏宽/唯一标识/网络(0:2G;1:3G;2:4G;3:WIFI;-1:未知)
        val sb = StringBuffer()
        sb.append("PaperLess/" + BuildConfig.VERSION_NAME + "/")
        sb.append("Android/" + android.os.Build.VERSION.RELEASE + "/")
        sb.append(android.os.Build.MODEL + "/")
        //sb.append(getMacAddress() + "/")
        sb.append(context.packageName + "/")
        sb.append(screenDisplayMetrics().heightPixels.toString() + "/")
        sb.append(screenDisplayMetrics().widthPixels.toString() + "/")
        sb.append(UUID.randomUUID().toString() + "/")
        sb.append(NetworkDeviceUtils.getNetworkType(context))
        return sb.toString()
    }

    private fun screenDisplayMetrics(): DisplayMetrics {
        val mWm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        mWm.defaultDisplay.getMetrics(metrics)
        // 有时候密度出错，所以通过Dpi来判断
        return metrics
    }


    private fun getMacAddress(): String {
        var interfaces: Enumeration<NetworkInterface>? = null
        try {
            interfaces = NetworkInterface.getNetworkInterfaces()
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        while (interfaces != null && interfaces.hasMoreElements()) {
            val iF = interfaces.nextElement()
            var address: ByteArray? = ByteArray(0)
            try {
                address = iF.hardwareAddress
            } catch (e: SocketException) {
                e.printStackTrace()
            }
            if (address == null || address.isEmpty()) {
                continue
            }

            val buf = StringBuilder()
            for (b in address) {
                buf.append(String.format("%02X:", b))
            }
            if (buf.isNotEmpty()) {
                buf.deleteCharAt(buf.length - 1)
            }
            val mac = buf.toString()
            if ("wlan0" == iF.getName()) {
                return mac
            }
        }
        return ""
    }

}

