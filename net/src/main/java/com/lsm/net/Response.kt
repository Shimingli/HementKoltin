package com.lsm.net

/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 16:44
 */

class Response<T>: BaseOneBean() {

    var data: T? = null

    var message: String? = null

    var code: Int = 0

    override fun uniqueKey(): Array<String>? {
        return arrayOf("")
    }

}