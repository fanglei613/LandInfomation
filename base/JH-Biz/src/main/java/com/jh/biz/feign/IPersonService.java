package com.jh.biz.feign;

import com.jh.vo.ResultMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 用户管理
 * @version <1> 2018-08-13 lijie: Created.
 */
@FeignClient(name="jh-sys")
public interface IPersonService {

    /**
     * 新增用户
     * <1> 2019-03-13 lijie: Created.
     */
    @PostMapping( value = "/person/addUser")
    ResultMessage addUser(@RequestBody Map<String,Object> voMap);

    /**
     * 根据手机号查询accountId
     * <1> 2019-03-13 lijie: Created.
     */
    @PostMapping( value = "/person/getAccountIdByPhone")
    ResultMessage getAccountIdByPhone(@RequestParam(name="accountName")String accountName);

    /**
     * 用户是否存在某角色
     * <1> 2019-03-13 lijie: Created.
     */
    @PostMapping( value = "/role/isExistRole")
    ResultMessage isExistRole(@RequestParam(name="accountId")Integer accountId,@RequestParam(name="roleCode")String roleCode);

   /**
    * 修改用户
    * @param
    * @return
    * @version <1> 2019/3/26 mason:Created.
    */
    @PostMapping( value = "/person/editUser")
    ResultMessage editUser(@RequestBody Map<String,Object> voMap);

    /**
     * 验证用户是否有数据权限
     * <1> 2019-03-13 lijie: Created.
     */
    @PostMapping( value = "/dataProduct/queryHasProductForFeign")
    ResultMessage queryHasProductForFeign(@RequestParam(name="accountId")Integer accountId,
                                          @RequestParam(name="DatasetId") Integer DatasetId,
                                          @RequestParam(name="regionId") Long regionId,
                                          @RequestParam(name="cropId") Integer cropId,
                                          @RequestParam(name="resolution") Integer resolution,
                                          @RequestParam(name="startDate") String startDate,
                                          @RequestParam(name="endDate") String endDate,
                                          @RequestParam(name="accountToken") String accountToken);

}
