//package com.jh.system.filter;
//
//import com.jh.common.dataReturn.CommonDefineEnum;
//import com.jh.vo.ResultMessage;
//import com.jh.common.utils.CookieUtils;
//import com.jh.common.utils.RedisUtil;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import com.jh.system.entity.PermAccount;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @description: 用于验证是否登陆
// *  1.拦截所有没有在config.properties文件中的，no_auth_login_url属性进行配置的url
// *  2.验证用户信息是否存在于session中，不存在则直接返回消息给前端
// * @version <1> 2018-01-12 cxj： Created.
// */
//public class LoginInterceptor implements HandlerInterceptor {
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        /*HttpSession session = httpServletRequest.getSession();
//        Object object = session.getAttribute("permAccount");
//        if(object == null){
//            ResultMessage resultMessage = ResultMessage.fail(CommonDefineEnum.NO_LOGIN_FAIL.getValue(),CommonDefineEnum.NO_LOGIN_FAIL.getMesasge());
//            httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
//            httpServletResponse.getWriter().print(JSONObject.fromObject(resultMessage).toString());
//            return false;
//        }else{
//            PermAccount permAccount = (PermAccount) object;
//            session.setAttribute("permAccount",permAccount);
//        }*/
//        String accessToken = httpServletRequest.getHeader("accessToken");
//        if(StringUtils.isEmpty(accessToken)){
//            accessToken = httpServletRequest.getParameter("accessToken");
//        }
//
//        //String accessToken = CookieUtils.getCookieValue(httpServletRequest, "accessToken");
//        if(null != accessToken && RedisUtil.testKeyExists(accessToken)){
//            /*Map<String, Object> userData = RedisUtil.getJsonToMap(accessToken);
//            RedisUtil.setJsonStr(accessToken, userData, 30*60);*/
//            String str = RedisUtil.get(accessToken);
//            RedisUtil.set(accessToken, str, 30*60);
//            CookieUtils.setCookie(httpServletRequest ,httpServletResponse, "accessToken", accessToken);
//        }else{
//            ResultMessage resultMessage = ResultMessage.fail(CommonDefineEnum.NO_LOGIN_FAIL.getValue(),CommonDefineEnum.NO_LOGIN_FAIL.getMesasge());
//            httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
//            httpServletResponse.getWriter().print(JSONObject.fromObject(resultMessage).toString());
//            return false;
//        }
//
//        return true;
//    }
//
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//}
