package com.lsm.net

import java.io.Serializable
import android.text.TextUtils



/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 16:42
 */
abstract class BaseOneBean :Serializable{

    override fun hashCode(): Int {
        val uniqueKey = uniqueKey()
        if (uniqueKey != null && uniqueKey.isNotEmpty()) {
            var result = 0
            for (i in uniqueKey.indices) {
                val unique = uniqueKey[i]
                if (!TextUtils.isEmpty(unique)) {
                    result = 31 * result + unique.hashCode()
                }
            }
            return if (result == 0) {
                super.hashCode()
            } else {
                result
            }
        } else {
            return super.hashCode()
        }

    }

    abstract fun uniqueKey(): Array<String>?

    override fun equals(o: Any?): Boolean {
        val uniqueKey = uniqueKey()
        if (uniqueKey != null && uniqueKey.isNotEmpty()) {
            val user = o as BaseOneBean?
            val modelKey = user!!.uniqueKey()
            for (i in uniqueKey.indices) {
                val unique = uniqueKey[i]
                if (TextUtils.isEmpty(unique) || unique != modelKey!![i]) {
                    return super.equals(o)
                }
            }
            return true
        } else {
            return super.equals(o)
        }
    }
}