package com.jh.system;

import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;
import com.jh.system.service.IRegionService;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 区域测试类
 * Created by lj on 2018/8/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IRegionServiceTest {

    @Autowired
    private IRegionService regionService;

    @Test
    public void encodeRegionCode() throws Exception {
        //regionService.encodeRegionCode(3);
        //regionService.encodeRegionCode(4);
        //regionService.encodeRegionName(3);
        //regionService.encodeRegionName(4);
    }

}