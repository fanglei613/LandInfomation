package com.jh.ttn.service.impl;


import com.jh.ttn.mapping.IDsTrmmForSevenMapper;
import com.jh.ttn.service.IDsTrmmForSevenService;
import com.jh.ttn.vo.TrmmSevenVo;
import com.jh.util.DateUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:降雨数据集服务:
 * 1.根据区域构造7天降雨数据查询服务
 * @version <1> 2018-06-12 cxw: Created.
 */
@Transactional
@Service
public class DsTrmmForSevenServiceImpl implements IDsTrmmForSevenService {

    @Autowired
    private IDsTrmmForSevenMapper sevenTrmmMapper;


    /*
     * 根据区域构造7天降雨数据查询服务
     * @param 数据集查询参数
     *   regionId: 区域ID
     *       date: 日期
     * @return ResultMessage
     * @version <1> 2018-05-02 cxw: Created.
     */
    @Override
    public ResultMessage findTrmmForSeven(Long[] regionId, String date) {
        ResultMessage result = ResultMessage.success(null,null,null);
        if(regionId.length>0&&StringUtils.isNotBlank(date))
        {
            LocalDate data_time = DateUtil.strToLocalDate(date);
            Map<String,Object> map = new HashMap<String,Object>();
            LocalDate now_edate = DateUtil.strToLocalDate(date);
            LocalDate now_sdate = data_time.plusDays(-6);
            LocalDate last_edate = now_edate.plusYears(-1);
            LocalDate last_sdate = now_sdate.plusYears(-1);
            map.put("now_edate",now_edate);
            map.put("now_sdate",now_sdate);
            map.put("lastweek_edate",now_edate.plusDays(-7));
            map.put("lastweek_sdate",now_sdate.plusDays(-7));
            map.put("last_sdate",last_sdate);
            map.put("last_edate",last_edate);
            List<TrmmSevenVo>  trmmList = sevenTrmmMapper.findTrmmForSeven(map);
            result = ResultMessage.success(trmmList);
        }
        return result;
    }



}
