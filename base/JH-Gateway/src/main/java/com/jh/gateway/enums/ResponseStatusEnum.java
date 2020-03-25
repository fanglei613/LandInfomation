package com.jh.gateway.enums;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.jh.vo.ResultMessage;

/*
*字典 返回 标志
* @version <1> 2018-05-07 xzg : Created.
*/
public enum ResponseStatusEnum {
    RequestOk(true,200,"请求成功"),
    NoToken(false,6000,"参数中没有Token"),
    RedisNoToken(false,6001,"用户没有登录"),
    PhoneIsBlank(false,6002,"手机号为空"),
    ServiceKeyFail(false,6003,"用户的key不正确"),
    NotDataProduct(false,6004,"暂无符合所选条件的数据访问权限");

    private boolean flag;
    private int code ;
    private String message;

    private ResponseStatusEnum(boolean flag ,int code, String message){
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public boolean getFlag(){
        return this.flag;
    }
    public void setFlag(boolean flag){
        this.flag = flag;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /*
    * "{\"flag\":false,\"result\":\"暂无符合所选条件的数据访问权限\"}"
    * @version <1> 2018-05-08 10:04:35 Hayden : Created.
    */
    public String getMessage() {
        ResultMessage result = new ResultMessage();
        result.setFlag(this.flag);
        result.setCode(Integer.toString(this.code));
        result.setMsg(this.message);
        String msg = "{}";
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            msg = objectMapper.writeValueAsString(result);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return msg;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
