package com.jh.system.mapping;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.entity.Dict;
import com.jh.system.entity.RelateCropRegion;
import com.jh.system.entity.RelateDataSetResolution;
import com.jh.system.model.DictDataReturn;
import com.jh.system.model.DictParam;
import com.jh.system.model.DictTreeReturn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: 字典数据访问接口
 * @version <1> 2018-01-17 cxj： Created.
 */
@Mapper
public interface IDictMapper extends IBaseMapper<DictParam, Dict,Integer>{
    /**
     * @description: 查询子字典数据
     * @param dict 字典对象
     * @version <1> 2018-01-17 cxj： Created.
     */
    List<Dict> queryDictByParentId(Dict dict);

    /**
     * @description: 通过字典父主键集合，查询多个子字典数据
     * @param list 字典父主键集合
     * @version <1> 2018-01-18 cxj： Created.
     */
    List<Dict> queryDictByParentIdList(List<Integer> list);

    /**
     * 查询指定父节点下的所有子字典数据
     *
     * @param dictParentId 父字典的主键id
     * @return
     * @version <1> 2018/1/30 djh： Created.
     */
    List<DictParam> querySubDictListByParentId(Integer dictParentId);

    /**
     *查询数据字典所有数据
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    public List<DictTreeReturn> findDictList();

    /**
     *查询所有数据字典所有数据
     * @version <1> 2017-12-15 cxw: Created.
     */
    public List<Dict> findAllDictList();

    /**
     *  根据父ID查询子节点
     * @return DictTreeReturn
     * @version  <1> 2017-12-27 cxw : Created.:
     */
    List<DictTreeReturn> findDictListByPid(Long parentId);

    /**
     *根据id查询数据字典数据
     * @param id 字典主键
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    DictParam findDictById(Integer id);

    /**
     * 查询下载配置中的所有数据分类
     *
     * @version <1> 2018/2/5 djh： Created.
     */
    List<DictParam> queryDataType();

    /**
     * 查询指定字典主键是否存在
     *
     * @param dictId 字典主键
     * @version <1> 2018-02-11 djh： Created.
     */
    Integer checkDictIdIsExists(Integer dictId);

    /**
     * 查询指定字典编码是否存在
     * @param dataCode 数据编码
     * @version <1> 2018-02-11 djh： Created.
     */
    Integer checkDictCodeIsExists(String dataCode);

    /**
     * 根据字典id查询对应的字典数据详情（包含父字典的名称）
     * @param dictId 字典id
     * @return
     * @version <1> 2018-02-26 djh： Created.
     */
    DictParam queryById(Integer dictId);

    /**
     *添加数据字典数据
     * @param dict 数据字典对象
     * @return int 添加成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    int addDict(DictParam dict);

    /**
     *添加数据字典数据
     * @param dict 数据字典对象
     * @return int 添加成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    int editDict(DictParam dict);

    /**
     * 修改字典数据状态
     * @param dict 数据字典对象
     * @return int 添加操作结果
     * @version <1> 2018-05-28 lxy: Created.
     */
    int editDictStatusByParentId(DictParam dict);

    /**
     *  根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息
     * @param dataCode 字典编码
     * @return  dict 字典对象
     * @version  <1> 2018-03-06 cxw : Created.:
     */
    List<DictParam> findDictByCode(String dataCode);

    /**
     * 查询对应区域下的所有农作物信息
     *
     * @param regionId 选中的区域id
     * @return
     * @version <1> 2018-05-10 wl： Created.
     */
    List<DictParam> findCropByRegionId(Integer regionId);

    /**
     * 根据条件查询数据字典数据集
     * @param dict
     * @return List<Dict>
     * @version <1> 2018-06-15 zhangshen： Created.
     */
    List<Dict> findDictListByDName(Dict dict);




    /**
     * 根据数据代码查询数据字典，如有其关联数据则加入关联数据（如区域与作物关联）
     * 在配置区域与作物时显示某一区域对应的所有作物，且该区域已分配的作物复选框勾选
     * <1> 2018-1-26 cxw: Created.
     */
    List<DictDataReturn> findAllCropByRegionId(RelateCropRegion relateCropRegion);







    /**
     * 根据字典中文名查询对应的字典
     * @param nameList
     * @return
     */
    List<Dict> findDictByName(List<String> nameList);

    /**
     * 根据数据集ID查询分辨率数据
     * @param param   (datasetId , dataStatus , delFlag)
     * @return  (dictId , dataName , dataCode , dataValue)
     */
    List<Map<String,Object>> findAccuracyByDsId(Map<String,Object> param);

    /**
     * 根据条件查询
     * @param param
     * @return   (dictId , dataName , dataCode , dataValue)
     */
    List<Map<String,Object>> findDictByParam(Map<String,Object> param);

    /**
     * 根据数据集id和精度id查询某数据集绑定的精度
     * @param relateDataSetResolution
     * @return
     */
    List<DictDataReturn> findAllResolutionByDatasetId(RelateDataSetResolution relateDataSetResolution);

    /**
     * 根据code查询字典信息
     * @param dict
     * @return
     */
    Dict queryDictByCode(Dict dict);

}
