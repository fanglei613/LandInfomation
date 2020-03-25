package com.jh.land.service.impl;

import com.github.pagehelper.PageInfo;
import com.jh.land.entity.LandEntity;
import com.jh.land.mapping.ILandMapper;
import com.jh.land.model.LandParam;
import com.jh.land.model.LandVO;
import com.jh.land.service.ILandService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LandServiceImpl implements ILandService {

    @Autowired
    private ILandMapper landMapper;

    /**
     * 获取乡镇地块统计信息
     */
    @Override
    public ResultMessage queryLandStatistics(LandParam landParam) {
        if (landParam.getEndDate() != null) {
            //获取结束时间的RankAreaVO年份  自动拼接12月31日
            LocalDate endDate = LocalDate.of(landParam.getEndDate().getYear(), 12, 31);
            landParam.setEndDate(endDate);
        } else {
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
            landParam.setEndDate(endDate);
        }
        if (landParam.getStartDate() != null) {
            //获取开始时间的年份  自动拼接1月1日
            LocalDate startDate = LocalDate.of(landParam.getStartDate().getYear(), 1, 1);
            landParam.setStartDate(startDate);
        } else {
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            landParam.setStartDate(startDate);
        }
        List<LandVO> list = landMapper.queryLandStatistics(landParam);
        return ResultMessage.success(list);
    }

    /**
     * 分页查询地块
     */
    @Override
    public PageInfo<LandEntity> queryLandList(LandParam landParam) {
        Integer page = landParam.getPage();
        Integer rows = landParam.getRows();
        // 如果总数不存在则查询当前乡镇的地块总数(这里如果设置分页插件会极大影响性能，因此总数从统计表中取)
        Long total = landParam.getTotal();
        if (null == total || total < 1) {
            total = this.landMapper.queryLandListSum(landParam);
        }
        // 获取开始索引
        Integer offset = (page - 1) * rows;
        landParam.setOffset(offset);
        // 根据分页条件查询并返回结果
        List<LandEntity> list = this.landMapper.queryLandList(landParam);
        PageInfo<LandEntity> result = new PageInfo<>(list);
        result.setTotal(total);
        return result;
    }

    /**
     * 根据经纬度查询地块
     */
    @Override
    public ResultMessage queryLandInfoByLonLat(LandParam landParam) {
        // 转换为点WKT
        String wkt = "SRID=4326;POINT(" + landParam.getLon() + " " + landParam.getLat() + ")";
        landParam.setWkt(wkt);
        // 查询并返回结果
        LandEntity data = this.landMapper.queryLandInfoByLonLat(landParam);
        return ResultMessage.success(data);
    }

    /**
     * 根据ID获取地块
     */
    @Override
    public ResultMessage queryLandInfoById(LandParam landParam) {
        LandEntity data = this.landMapper.queryLandInfoById(landParam);
        return ResultMessage.success(data);
    }
}
