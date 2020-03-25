/**
* 用户认证：通过检验Token决定用户能否继续访问。
*
* 步骤：
*   1. 从请求中获取token
*   2. 从redis中根据token检查相在的账号信息是否存在
*   3. 如果账号信息存在，则可以继续访问，否则不能继续访问。
* 后期采用token调用API接口：
*   从数据库中检查token是否有对应的账号信息 TODO......?
*
* @version <1> 2018-04-24 14:58:04 Hayden : Created.
*/
package com.jh.gateway.filter;

import com.jh.vo.ResultMessage;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;

import com.jh.util.RedisUtil;
import com.jh.constant.SysConstant;
import com.jh.gateway.util.ConstantUtil;
import com.jh.gateway.enums.ResponseStatusEnum;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

//@Configuration
public class LoginFilter extends ZuulFilter{

    @Autowired
    private RestTemplate restTemplate;

	@Override
   public String filterType() {
       return "pre";
   }

   @Override
   public int filterOrder() {
       return 100;
   }

   @Override
   public boolean shouldFilter() {

       RequestContext ctx = RequestContext.getCurrentContext();
       HttpServletRequest request = ctx.getRequest();

       //1.判断请求是否要经过此过滤器
       String uri = request.getRequestURI();
       int index = uri.indexOf(ConstantUtil.Key_Url_NoLog);
       if(index == -1){
           return true;
       }else{
           //不需要登录
           ctx.set(ConstantUtil.Key_Zuul_Flag, false);
           return false;
       }
   }

   @Override
   public Object run() {
       ResponseStatusEnum responseStatus = ResponseStatusEnum.RequestOk;

       RequestContext ctx = RequestContext.getCurrentContext();
       HttpServletRequest request = ctx.getRequest();

       String serviceToken = !StringUtils.isBlank(request.getHeader(SysConstant.Key_Service_Token)) ? request.getHeader(SysConstant.Key_Service_Token).toString() : null;

       //获取serviceKey
       String serviceKey = request.getParameter(SysConstant.Key_Service_Token) == null ? serviceToken : null;
       if (StringUtils.isBlank(serviceKey)){
           //请求参数中没有 serviceKey  ，需要用户登录才能访问
           //2.从请求中获取token
           String token = request.getHeader(SysConstant.Key_Login_Token) == null ? "" : request.getHeader(SysConstant.Key_Login_Token).toString();
           if(StringUtils.isBlank(token)){
               token = request.getParameter(SysConstant.Key_Login_Token);  //从参数中取accessToken
           }

           //2.1 没有找到token,表示没有登录，不能继续访问
           if(StringUtils.isBlank(token)){
               responseStatus = ResponseStatusEnum.NoToken;
           }else{
               //2. 在redis的所有用户列表中[SysConstant.Key_Login_Token]
               //   根据键[token]检查相关联的账号信息是否存在
               // String accountName = RedisUtil.hget(ConstantUtil.Key_Token_Hash,token);
               String accountName = RedisUtil.get(token);
               if(StringUtils.isBlank(accountName)){
                   responseStatus = ResponseStatusEnum.RedisNoToken;
               }
           }
       } else {
           //请求参数中 有 serviceKey , 判断 serviceKey 跟手机号是否正确
           String phoneHeader = !StringUtils.isBlank(request.getHeader(SysConstant.Key_User_Phone)) ? request.getHeader(SysConstant.Key_User_Phone).toString() : null;
           String phone = request.getParameter(SysConstant.Key_User_Phone) == null ? phoneHeader : null;
           if (StringUtils.isBlank(phone)){
               responseStatus = ResponseStatusEnum.PhoneIsBlank;
           } else {
               //判断 serviceKey 是否 匹配
               ResultMessage userMsg = restTemplate.getForObject("http://"+SysConstant.Module_System_name+"/user/testServiceKey?serviceKey={serviceKey}&phone={phone}", ResultMessage.class,serviceKey,phone);
               if (!userMsg.isFlag()){
                   responseStatus = ResponseStatusEnum.ServiceKeyFail;
               }
           }
       }


       //3. 设置路由是否继续  body: "{\"flag\":false,\"result\":\"暂无符合所选条件的数据访问权限\"}"
       ctx.setSendZuulResponse(responseStatus.getFlag());
       ctx.set(ConstantUtil.Key_Zuul_Flag, responseStatus.getFlag());
       if (ResponseStatusEnum.RequestOk != responseStatus){

           ctx.setSendZuulResponse(false);
           ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
           ctx.setResponseStatusCode(responseStatus.getCode());
           ctx.setResponseBody(responseStatus.getMessage());
       }

       return null;
   }

}