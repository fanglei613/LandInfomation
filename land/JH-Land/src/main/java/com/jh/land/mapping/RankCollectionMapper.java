package com.jh.land.mapping;


import com.jh.land.entity.RankCollection;

import java.util.List;

public interface RankCollectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rank_collection
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);



    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rank_collection
     *
     * @mbggenerated
     */
    int insertSelective(RankCollection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rank_collection
     *
     * @mbggenerated
     */
    List<RankCollection> selectByExample(RankCollection example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rank_collection
     *
     * @mbggenerated
     */
    RankCollection selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rank_collection
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RankCollection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rank_collection
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RankCollection record);
}