package com.lsm.net.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.lsm.base.BaseApplication
import java.io.*
import kotlin.reflect.KProperty

/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 14:42
 */
class SPUtils<T>(val name:String, private val default:T) {

    companion object {
        private const val file_name = "paperLess"

        val prefs: SharedPreferences by lazy {
            BaseApplication.context.getSharedPreferences(file_name, Context.MODE_PRIVATE)
        }
        /**
         * 删除全部数据
         */
        @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
        fun clearPreference(){
            prefs.edit().clear().apply()
        }

        /**
         * 根据key删除存储数据
         */
        @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
        fun clearPreference(key : String){
            prefs.edit().remove(key).apply()
        }


    }




    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharedPreferences(name, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
//        putSharedPreferences(name, value)
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("CommitPrefEdits")
    private fun putSharedPreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name,serialize(value))
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSharedPreferences(name: String, default: T): T = with(prefs) {
//        val res: Any = when (default) {
//            is Long -> getLong(name, default)
//            is String -> getString(name, default )
//            is Int -> getInt(name, default)
//            is Boolean -> getBoolean(name, default)
//            is Float -> getFloat(name, default)
//            else ->  deSerialization(getString(name,serialize(default)))
//        }
        val res = null
        return res as T
    }



    /**
     * 序列化对象

     * @param person
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun<A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
            byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象

     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun<A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }


    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    fun getAll(): Map<String, *> {
        return prefs.all
    }

}