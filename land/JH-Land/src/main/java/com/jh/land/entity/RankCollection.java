package com.jh.land.entity;

import java.util.Date;

public class RankCollection {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rank_collection.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rank_collection.rank_id
     *
     * @mbggenerated
     */
    private Long rankId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rank_collection.rank_name
     *
     * @mbggenerated
     */
    private String rankName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rank_collection.collection_time
     *
     * @mbggenerated
     */
    private Date collectionTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rank_collection.id
     *
     * @return the value of rank_collection.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rank_collection.id
     *
     * @param id the value for rank_collection.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rank_collection.rank_id
     *
     * @return the value of rank_collection.rank_id
     *
     * @mbggenerated
     */
    public Long getRankId() {
        return rankId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rank_collection.rank_id
     *
     * @param rankId the value for rank_collection.rank_id
     *
     * @mbggenerated
     */
    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rank_collection.rank_name
     *
     * @return the value of rank_collection.rank_name
     *
     * @mbggenerated
     */
    public String getRankName() {
        return rankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rank_collection.rank_name
     *
     * @param rankName the value for rank_collection.rank_name
     *
     * @mbggenerated
     */
    public void setRankName(String rankName) {
        this.rankName = rankName == null ? null : rankName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rank_collection.collection_time
     *
     * @return the value of rank_collection.collection_time
     *
     * @mbggenerated
     */
    public Date getCollectionTime() {
        return collectionTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rank_collection.collection_time
     *
     * @param collectionTime the value for rank_collection.collection_time
     *
     * @mbggenerated
     */
    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }
}