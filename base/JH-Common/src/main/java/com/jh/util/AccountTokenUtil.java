package com.jh.util;

import com.google.gson.JsonObject;
import com.jh.constant.ConstantUtil;
import com.jh.constant.SysConstant;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccountTokenUtil {


    private static String ROLE_ADMIN = "Role_Admin";//超级管理员角色编码
    private static String Login_Account_Token = SysConstant.Key_Login_Token;

    /**
     * 获取当前登录人Token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request){
        String token = request.getHeader(Login_Account_Token) == null ? request.getParameter(Login_Account_Token) : request.getHeader(Login_Account_Token) ;
        return token;
    }

    /**
     * 判断是否是超级管理员
     * @param accountToken
     * @return
     */
    public static boolean isSuperAdmin(String accountToken) {
        boolean  bool = false;
//        List<Map<String,Object>> roleList = getUserRolesFromRedis(accountToken);

//        for (Map<String,Object> role : roleList){
//            String roleCode = role.get("roleCode") == null ? null : role.get("roleCode").toString();
//            if(roleCode != null && ROLE_ADMIN.equalsIgnoreCase(roleCode)){
//                bool = true;
//                break;
//            }
//        }



        Map<String,Object> accountMap = getUserDataFromRedis(accountToken);
        if(accountMap != null && accountMap.size() > 0){
            String roleCode = accountMap.get("roleCode") == null ? null : accountMap.get("roleCode").toString();
            if(roleCode != null && ROLE_ADMIN.equalsIgnoreCase(roleCode)){
                bool = true;
            }
        }


        return bool;
    }


    /**
     * 判断是否需要经历productFilter过滤器
     * @param accountToken
     * @return
     * @version<1> 2018-07-18 lcw :Created.
     */
    public static boolean isUseProductFilter(String accountToken){
        boolean  bool = getUseProductFilterFlag(accountToken);
        return bool;


    }

    /**
     * 从redis中取得用户基本信息
     * @param accountToken
     * @return
     */
    public static Map<String, Object> getUserInfoFromRedis(String accountToken) {
        Map<String,Object> userData = getUserDataFromRedis(accountToken);
        Map<String,Object> userInfo = null;
        if (userData != null && userData.containsKey("userInfo")){

            userInfo = (Map<String, Object>) userData.get("userInfo");
        }
        return userInfo;
    }

    /**
     * 从redis 中取得用户角色
     * @param accountToken
     * @return
     */
    public static List<Map<String, Object>> getUserRolesFromRedis(String accountToken) {
        Map<String,Object> userData = getUserDataFromRedis(accountToken);
        List<Map<String, Object>> roleList = null;
        if (userData != null && userData.containsKey("roleList")){
            roleList = (List<Map<String, Object>>)userData.get("roleList");
        }
        return roleList;
    }



    /**
     * 从redis 中取得是否使用productFilter的状态
     * @param accountToken
     * @return
     */
    private static boolean getUseProductFilterFlag(String accountToken) {
        Map<String,Object> userData = getUserDataFromRedis(accountToken);
        boolean flag = false;
        if (userData != null && userData.containsKey(ConstantUtil.USEPRODUCTFILTER_KEY)){
            flag = (boolean)userData.get(ConstantUtil.USEPRODUCTFILTER_KEY);
        }
        return flag;
    }




    /**
     * 从redis 中取得用户数据权限
     * @param accountToken
     * @return
     */
    public static List<Map<String, Object>> getUserProductsFromRedis(String accountToken) {
        Map<String,Object> userData = getUserDataFromRedis(accountToken);
        List<Map<String, Object>> productList = null;
        if (userData != null && userData.containsKey("productList")){
            productList = (List<Map<String, Object>>)userData.get("productList");
        }
        return productList;
    }

    /**
     * 从redis中取得用户 基本信息、角色、数据权限
     * @param accountToken
     * @return
     */
    public static Map<String, Object> getUserDataFromRedis(String accountToken) {
        Map<String,Object> userData = null;
        if (!StringUtils.isBlank(accountToken)){
            userData = RedisUtil.getJsonToMap(accountToken);
        }
        return userData;
    }

    /**
     * 查询用户是否具有此数据权限
     * @param token                       用户标识
     * @param paramRegionId              区域
     * @param paramDsCode                 数据集
     * @param paramStartDate              开始时间
     * @param paramEndDate                 结束时间
     * @param paramCropId                  作物
     * @param paramAccuracyId               分辨率
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static boolean checkProduct(String token,Long paramRegionId,String paramDsCode,Date paramStartDate,Date paramEndDate,Integer paramCropId,Integer paramAccuracyId ) {


        List<Map<String,Object>> productList = AccountTokenUtil.getUserProductsFromRedis(token);
        if (productList == null || productList.isEmpty()){
            //用户没有任何数据权限  或 查询条件空
            return false;
        }


        if (paramRegionId == null || org.apache.commons.lang3.StringUtils.isBlank(paramDsCode)){
            //参数，区域 和 数据集 不能为空
            return true;
        }

        boolean bool = false;
        if (productList != null && !productList.isEmpty()){
            for(Map<String,Object> product : productList){

                //1、校验区域
                long productRegionId = Long.parseLong(product.get("regionId").toString());//区域
                if (paramRegionId.longValue() != productRegionId){
                    continue;
                }

                //2、校验数据集
                String productDsCode = product.get("datasetCode").toString();//数据集
                if (!paramDsCode.equals(productDsCode)){
                    continue;
                }

                //3、校验数据源精度
                Integer productAccuracyId = Integer.valueOf(product.get("accuracyId").toString());
                if (paramAccuracyId.intValue() != productAccuracyId.intValue()){
                    continue;
                }


                //4、校验作物 (降水、地温除外)
                String productCropId = product.get("cropId").toString();//区域
                if (!"t".equals(paramDsCode) && !"trmm".equals(paramDsCode) && !"temperature".equals(paramDsCode)){
                    if (paramCropId.intValue() != Integer.parseInt(productCropId)){
                        continue;
                    }
                }

                //5、校验时间
                Date productStartDate = DateUtil.strToDate(product.get("startDate").toString());
                Date productEndDate = DateUtil.strToDate(product.get("endDate").toString());
                //if(!(paramStartDate.after(productStartDate) && paramEndDate.before(productEndDate))){
                if(!(!paramEndDate.after(productEndDate)) || !(!paramStartDate.before(productStartDate))){
                    continue;
                }

                //所有校验通过
                bool = true;
                break;
            }
        }
        return bool;
    }



}
