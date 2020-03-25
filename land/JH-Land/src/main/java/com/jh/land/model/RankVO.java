package com.jh.land.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankVO {

    //区域id
    private Long regionId;

    //区域中文名
    private String chinaName;

    //区域地块面积
    private BigDecimal area;

    //区域地块个数
    private Integer rankNum;

    //年份
    private Integer year;

    //数据时间
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate dataTime;

    //查询开始日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    //查询结束日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    //区域等级
    private Integer level;

    //区域code
    private String regionCode;
}
