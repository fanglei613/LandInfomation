package com.jh.land.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 * @Description
 * @Author verint
 * @Date 2019-08-29 11:23 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YieldCollect {
    /**
     * 估产面积
     */
    @Builder.Default
    private BigDecimal yield = new BigDecimal(0.00);
    /**
     * 单位
     */
    private BigDecimal yieldUnit = new BigDecimal(0.00);
    /**
     * 总面积
     */
    private BigDecimal total = new BigDecimal(0.00);
    /**
     * 作物Id
     */
    private Integer cropId;
    /**
     * 区域Id
     */
    private BigInteger regionId;
    /**
     * 区域拼音
     */
    private String regionName;
    /**
     * 区域中文名
     */
    private String chinaName;
    /**
     * 时间节点
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataTime;
    /**
     * 年份
     */
    private Integer Year;

}
