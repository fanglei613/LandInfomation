package com.jh.system.service;

import com.jh.system.base.service.IBaseService;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.Dict;
import com.jh.system.model.DictParam;
import com.jh.system.model.DictTreeReturn;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 字典业务逻辑接口
 * @version <1> 2018-01-17 cxj： Created.
 * @version <2> 2018/1/25 djh： update.
 *      新增接口
 */
public interface IDictService  extends IBaseService<DictParam, Dict,Integer> {

    /**
     *查询数据字典所有数据
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    ResultMessage findDictList();

    /**
     *查询数据字典所有数据
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    ResultMessage findAllDictList();

    /**
     *  根据父ID查询子节点
     * @return DictTreeReturn
     * @version  <1> 2017-12-27 cxw : Created.:
     */
    ResultMessage findDictListByPid(DictTreeReturn dtr);

    /**
     *根据id查询数据字典数据
     * @param dict 字典对象
     * @return DictParam
     * @version <1> 2017-12-15 cxw: Created.
     */
    ResultMessage findDictById(DictParam dict);

    /**
     * @description: 查询子字典数据
     * @param dict 字典对象
     * @version <1> 2018-01-17 cxj： Created.
     */
    ResultMessage queryDictByParentId(Dict dict);

    /**
     * @description: 通过字典父主键集合，查询多个子字典数据
     * @param list 字典父主键集合
     * @version <1> 2018-01-18 cxj： Created.
     */
    ResultMessage queryDictByParentIdList(List<Integer> list);

    /**
     *添加数据字典数据
     * @param dict 数据字典对象
     * @return int 添加成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    ResultMessage addDict(DictParam dict, HttpServletRequest request);

    /**
     *修改数据字典数据
     * @param dict 数据字典对象
     * @return int 修改成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    ResultMessage updateDict(DictParam dict, HttpServletRequest request);


    /**
     *删除数据字典数据
     * @param dict 字典
     * @return int 删除成功返回1，失败返回0
     * @version <1> 2017-12-15 cxw: Created.
     */
    ResultMessage delDictById(DictParam dict, HttpServletRequest request);

    /**
     * 查询指定父节点下的所有子字典数据
     * 
     * @param dictParentId 父字典的主键id
     * @return
     * @version <1> 2018/1/30 djh： Created.
     * @version<2>  2018-02-06 lcw : updated.
     *  修改该方法的返回类型为ResultMessage
     */
    ResultMessage querySubDictListByParentId(Integer dictParentId);

    /**
     * 查询下载配置中的所有数据分类
     *
     * @version <1> 2018/2/5 djh： Created.
     */
    ResultMessage queryDataType();

    /**
     * 查询指定字典主键是否存在
     *
     * @param dictId 字典主键
     * @version <1> 2018-02-11 djh： Created.
     */
    ResultMessage checkDictIdIsExists(Integer dictId);

    /**
     * 查询指定字典编码是否存在
     * @param dataCode 数据编码
     * @version <1> 2018-02-11 djh： Created.
     */
    ResultMessage checkDictCodeIsExists(String dataCode);

    /**
     *  根据编码查询出id，然后根据查询出的id匹配父ID查询出所有字典信息
     * @param dict 字典对象
     * @return  dict 字典对象集合
     * @version  <1> 2018-03-06 cxw : Created.:
     */
    ResultMessage findDictByCode(DictParam dict);
    /**
     * @description: 校验字典编码是否存在
     * 若为true，表示存在，不可重复添加
     * 若未false，表示不存在，可进行添加
     * @param dictParam
     * @return
     * @version <1> 2018-04-16 wl： Created.
     */
    ResultMessage checkDictCode(DictParam dictParam);

    /**
     *  根据选中的区域id查询该区域的所有农作物信息
     * @param regionId 区域id
     * @return  dict 字典对象农作物集合
     * @version  <1> 2018-05-10 wl : Created.:
     */
    ResultMessage findCropByRegionId(Integer regionId);













    /**
     * 根据作物中文名查询作物，多个逗号分隔
     * @param dictNameStr
     * @return
     */
    ResultMessage findDictByName(String dictNameStr);

    /**
     * 根据数据集ID查询分辨率数据
     * @param dsId
     * @return
     */
    ResultMessage findAccuracyByDsId(Integer dsId);

    /**
     * 根据父级查询
     * @param parentId
     * @return
     */
    ResultMessage findDictByParamId(Integer parentId);

    /**
     * 根据字典code查询字典详情
     * @param dataCode
     * @return
     */
    ResultMessage queryDictByCode(String dataCode);
}
