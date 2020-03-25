package com.jh.gateway.filter;

import com.jh.constant.SysConstant;
import com.jh.gateway.enums.ResponseStatusEnum;
import com.jh.gateway.util.ConstantUtil;
import com.jh.util.AccountTokenUtil;
import com.jh.util.DateUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据权限校验
 * Created by XZG on 2018-05-18.
 */
public class ProductFilter extends ZuulFilter{


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 200;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request  = ctx.getRequest();
        boolean previousFilterSuccess = (boolean)ctx.get(ConstantUtil.Key_Zuul_Flag);
        String uri = request.getRequestURI();
        int index = uri.indexOf(ConstantUtil.Key_Url_NoProduct);// -1 经过过滤器  ，过滤不需要进行数据校验
        int reoprtIndex = uri.indexOf(ConstantUtil.Key_Url_NoReoprt);
        int cropIndex = uri.indexOf(ConstantUtil.Key_Url_NoCrop);

        String serviceKey = request.getParameter(SysConstant.Key_Service_Token);
        if (!previousFilterSuccess || index != -1 || StringUtils.isNotBlank(serviceKey)
                || reoprtIndex != -1 || cropIndex != -1){
            //登录失败  或 拥有忽略权限校验标识，不进行权限的校验
            return false;
        }
        return false;
    }

    @Override
    public Object run() {
//        System.out.println("productFilter run()................");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String token = AccountTokenUtil.getToken(request);
        boolean isSuperAdmin = AccountTokenUtil.isSuperAdmin(token);
        boolean productFilterFlag = AccountTokenUtil.isUseProductFilter(token);
        boolean filterSuccess = false;//默认设置为权限校验执行失败
        if (!isSuperAdmin && productFilterFlag){
            try{
                //获取查询参数
                String paramRegionId = request.getParameter("regionId");
                String paramDsId = request.getParameter("dsId");
                String paramDsCode = request.getParameter("dsCode");
                String paramCropId = request.getParameter("cropId");
                String paramStateDate = request.getParameter("startDate");
                String paramEndDate = request.getParameter("endDate");
                String paramAccuracyId = request.getParameter("accuracyId");

                //区域
                Long regionId = null;
                if (StringUtils.isNotBlank(paramRegionId)){
                    regionId = Long.parseLong(paramRegionId);
                }

                //数据集ID
                Integer dsId = null;
                if (StringUtils.isNotBlank(paramDsId)){
                    dsId = Integer.parseInt(paramDsId.trim());
                }

                //数据集
                String dsCode = null;
                if (StringUtils.isNotBlank(paramDsCode)){
                    dsCode = paramDsCode.trim();
                }

                //作物
                Integer cropId = null;
                if (StringUtils.isNotBlank(paramCropId)){
                    cropId = Integer.parseInt(paramCropId.trim());
                }

                //开始时间
                Date startDate = null;
                if (StringUtils.isNotBlank(paramStateDate)){
                    startDate = DateUtil.strToDate(paramStateDate);
                }

                //结束时间
                Date endDate = null;
                if (StringUtils.isNotBlank(paramEndDate)){
                    endDate = DateUtil.strToDate(paramEndDate);
                }

                //数据源 精度
                Integer accuracyId = null;
                if (StringUtils.isNotBlank(paramAccuracyId)){
                    accuracyId = Integer.parseInt(paramAccuracyId.trim());
                }

                //判断用户是否拥有数据权限
                filterSuccess = AccountTokenUtil.checkProduct(token,regionId,dsCode,startDate,endDate,cropId,accuracyId);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            filterSuccess = true;
        }

        ctx.setSendZuulResponse(filterSuccess);
        ctx.set(ConstantUtil.Key_Zuul_Flag,filterSuccess);
        if (!filterSuccess){
            //没有数据权限
            ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
            ctx.setResponseStatusCode(ResponseStatusEnum.NotDataProduct.getCode());
            ctx.setResponseBody(ResponseStatusEnum.NotDataProduct.getMessage());
        }
        return null;
    }

    /**
     * 封装传递的参数对象
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public Map<String,Object> getParam(HttpServletRequest request) throws IOException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String paramRegionId = request.getParameter("regionId");
        String paramDsId = request.getParameter("dsId");
        String paramDsCode = request.getParameter("dsCode");
        String paramCropId = request.getParameter("cropId");
        String paramStateDate = request.getParameter("startDate");
        String paramEndDate = request.getParameter("endDate");
        String paramAccuracyId = request.getParameter("accuracyId");

        //区域
        Long regionId = null;
        if (StringUtils.isNotBlank(paramRegionId)){
            regionId = Long.parseLong(paramRegionId);
        }

        //数据集ID
        Integer dsId = null;
        if (StringUtils.isNotBlank(paramDsId)){
            dsId = Integer.parseInt(paramDsId.trim());
        }

        //数据集
        String dsCode = null;
        if (StringUtils.isNotBlank(paramDsCode)){
            dsCode = paramDsCode.trim();
        }

        //作物
        Integer cropId = null;
        if (StringUtils.isNotBlank(paramCropId)){
            cropId = Integer.parseInt(paramCropId.trim());
        }

        //开始时间
        Date startDate = null;
        if (StringUtils.isNotBlank(paramStateDate)){
            startDate = sdf.parse(paramStateDate);
        }

        //结束时间
        Date endDate = null;
        if (StringUtils.isNotBlank(paramEndDate)){
            endDate = sdf.parse(paramEndDate);
        }

        //数据源 精度
        Integer accuracyId = null;
        if (StringUtils.isNotBlank(paramAccuracyId)){
            accuracyId = Integer.parseInt(paramAccuracyId.trim());
        }

        Map<String,Object> productParam = new HashMap<String,Object>();
        productParam.put("regionId",regionId);
        productParam.put("dsId",dsId);
        productParam.put("dsCode",dsCode);
        productParam.put("cropId",cropId);
        productParam.put("accuracyId",accuracyId);
        if(startDate != null && endDate != null) {
            productParam.put("startDate",startDate);
            productParam.put("endDate",endDate);
        }

        return productParam;
    }


}
