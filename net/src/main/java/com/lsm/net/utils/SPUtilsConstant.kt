package com.base.net.utils

/**
 * <p>
 *
 * </p>
 *
 * @author  shiming
 * @version v1.0
 * @since 2019/5/20 15:40
 */
object SPUtilsConstant{

    const val token="token"
    const val useName="useName"
    const val passWord="passWord"
    const val firstName="firstName"
    //配送站的id
    const val provinceId="provinceId"
    //配送站的名称
    const val provinceName="provinceName"
    //角色的名称
    const val roleName="roleName"
    //如果是配送站的话，并且配送站不少于1个的话，这个值就是2  等于1 就是  1
    const val needShowChangeStationActivity="needShowChangeStationActivity"

    const val baseUrlResource="baseUrlResource"

    const val slectionProductionPersonId="slectionProductionPersonId"


    val PRINT_TYPE_SP = "print_type"
    val CURRENT_PRINT_TYPE = "current_type"//当打印连接方式
    val ACTION_USB_PERMISSION = "com.production.print.usb.action"//Usb打印广播
    val PRINTER_CONNECT_BLUETOOTH = 0x001
    val PRINTER_CONNECT_USB = 0x002
    val USB_CONNECT_SP = "usb_connect"
    val USB_HAS_CONNECTED = "usb_has_connected"
    val BLUET_CONNECT_SP = "blue_connect"
    val BLUET_HAS_CONNECTED = "blue_has_connected"
}

