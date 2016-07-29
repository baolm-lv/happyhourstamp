/**
 * 
 */
package com.lvmama.vst.hhs.common.exception;

/**
 * 错误码定义
 * 
 * @author baolm
 *
 */
public class StampErrorCodes {

    /**
     * 成功
     */
    public static final String SUCCESS = "1000";

    /**
     * 系统异常
     */
    public static final String E_SYS_9999 = "9999";

    /**
     * 参数错误
     */
    public static final String E_REQ_1001 = "1001";

    /**
     * 重复请求
     */
    public static final String E_REQ_1002 = "1002";

    /**
     * ENTITY NOT FOUND
     */
    public static final String E_ENTITY_2001 = "2001";

    /**
     * 修改券状态异常
     */
    public static final String E_STAMP_3001 = "3001";

    /**
     * 设置券有效异常
     */
    public static final String E_STAMP_3002 = "3002";

    /**
     * 存在退款中的退款单（退款）
     */
    public static final String E_REFUND_5001 = "5001";

    /**
     * 可退款数量不足
     */
    public static final String E_REFUND_5002 = "5002";

    /**
     * 退款已完成，不能废弃
     */
    public static final String E_REFUND_5003 = "5003";

    /**
     * 券状态不对，解绑废弃失败
     */
    public static final String E_REFUND_5004 = "5004";
}
