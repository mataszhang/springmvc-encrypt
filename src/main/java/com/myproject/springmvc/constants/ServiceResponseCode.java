package com.myproject.springmvc.constants;


public enum ServiceResponseCode implements ErrorCodeEnum {
    /** 系统错误 */
    SYSTEM_ERROR("-3", "系统错误"),
    /** 处理警告，主要针对允许成功失败同时存在的处理（如批处理）*/
    SENDMSG_ERROR("-3","发短信失败,可能超时"),
    WARNING("-2", "警告"),
    /** 处理失败 */
    FAILURE("-1", "失败"),
    /** 处理成功 */
    SUCCESS("0", "成功"),
    /** 参数错误 */
    PARAM_ERROR("1", "参数错误"),
    /** 方法不存在 */
    NO_SUCH_METHOD("2", "方法不存在"),
    /** 用户未登录 */
    USER_NOT_LOGIN("3", "用户未登录"),
    /** 记录不存在 */
    RECORD_NOT_EXISTS("4", "记录不存在"),
    /** 记录不可用 */
    RECORD_UNAVAILABLE("5", "记录不可用"),
    /** 重复记录 */
    DUPLICATE_RECORD("6", "重复记录"),
    /** 记录被引用 */
    REFERENCED_RECORD("7", "记录被引用"),
    VERIFY_CODE_ERROR("8", "验证码错误"),
    /** 记录被引用 */
    BUSINESS_ILLEGAL_OP("9", "非法的操作"),
    /** 重复提交 */
    REPEAT_SUBMIT("10", "重复提交"),
    /** 无此操作权限 */
    NO_PERMISSION("11", "无此操作权限"),
    /** IP已变更 */
    IP_CHANGED("12", "IP已变更"),
    /** session失效 */
    SESSION_EXPIRED("13", "session失效"),

    MAN_REST_STATUS("14", "快递员不在工作状态"),

    TASKORDER_EXISTS("15", "任务单不存在"),

    BANKACOUNT_EXISTS("16", "快递员的银行账户不存在"),

    ACCOUNTAMOUNT_NULL("17", "账户余额为空"), 
    
    AMOUNT_NOT_ENOUGH("18", "账户余额不足"),
    DELIVERY_UPDATE_FAILURE("19","运单已妥投或已取消，无法更新"),
    AMOUT_FREEZE_FAILURE("20","余额冻结失败"),
    GRAB_ORDER_REPEATED("21", "抢单失败，您已抢过该单，不能重复抢单"),
    BEYOND_SNATCH_COUNT("22","超出当天抢单次数"),
    CREDITAMOUNT_NOT_ENOUGH("50", "信用账户余额不足"),
    UNFREEZEAMOUNT_ERROR("51", "信用账户解冻金额异常"),
    BOUNDCOST_ERROR("52", "信用账户绑定保证金异常"),
    RECHARGE_ERROR("53", "信用账户绑定充值异常"),
    GRABFREEZE_ERROR("54", "信用账户抢单冻结保证金异常"),
    ADJUSTTEMPAMOUNT_ERROR("55", "信用账户调整临时额度异常"),
    CLEARTTEMPAMOUNT_ERROR("56", "信用账户临时额度清除异常"),
    
    TAKE_AMOUNT_NOT_ENOUGH("57", "解冻金额大于冻结金额"),
    CHANGE_MAN_TYPE_ERROR("58", "修改快递员类型异常"),
    INVOKEBANK_ERROR("59", "调网银查询接口错误"),
    
    ONLINETRAINING_TRAINED("62","已培训"),
    USER_NOT_EXIST("63","用户不存在"),
    ;
    private String code;
    private String msg;

    private ServiceResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public static String getDescByCode(String code) {
        for (ServiceResponseCode e : ServiceResponseCode.values()) {
            if (e.getCode().equals(code)) {
                return e.getMsg();
            }
        }
        return null;
    }
}
