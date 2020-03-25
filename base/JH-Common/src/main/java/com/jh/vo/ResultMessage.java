/**
* VO对象，主要用于方法返回。
* flag : 操作是否成功标志
* code : 返回代码
* msg  : 返回提示
* data : 返回的数据
* @@version <1> 2018-04-24 10:10:37 Hayden : Created.
**/

package com.jh.vo;

public class ResultMessage{
	private  boolean flag ; //标记
	private  String code;  //状态码
	private  String msg;  //消息
	private  Object	data; //内容

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultMessage(){}

	public ResultMessage(boolean flag,String code,String msg,Object data){
		this.flag = flag;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	* 当操作成功时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage success(){
		ResultMessage result = new ResultMessage(true,null,null,null);
		return result;
	}

	/**
	* 当操作成功时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage success(String msg){
		ResultMessage result = new ResultMessage(true,null,msg,null);
		return result;
	}

	/**
	* 当操作成功时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage success(Object data){
		ResultMessage result = new ResultMessage(true,null,null,data);
		return result;
	}

	/**
	* 当操作成功时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage success(String msg,Object data){
		ResultMessage result = new ResultMessage(true,null,msg,data);
		return result;
	}

	/**
	* 当操作成功时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage success(String code,String msg,Object data){
		ResultMessage result = new ResultMessage(true,code,msg,data);
		return result;
	}

	/**
	 * * 当操作失败时，调用此方法
	 * @version <1> 2018-07-12 10:16:07 Hayden : Created.
	 * @return
     */
	public static ResultMessage fail(){
		return fail(null);
	}

	/**
	* 当操作失败时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage fail(String msg){
		ResultMessage result = new ResultMessage(false,null,msg,null);
		return result;
	}

	/**
	* 当操作失败时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage fail(String code,String msg){
		ResultMessage result = new ResultMessage(false,code,msg,null);
		return result;
	}

	/**
	* 当操作失败时，调用此方法
	* @version <1> 2018-04-27 10:16:07 Hayden : Created.
	*/
	public static ResultMessage fail(String code,String msg,Object data){
		ResultMessage result = new ResultMessage(false,code,msg,data);
		return result;
	}
}