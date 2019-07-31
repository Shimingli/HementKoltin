package com.lsm.net.rxjava

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * <p>
 *
 *   转换到主线程
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 14:51
 */
class IoMainScheduler<T> : BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())