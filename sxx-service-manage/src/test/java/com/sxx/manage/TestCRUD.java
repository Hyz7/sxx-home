package com.sxx.manage;

import com.github.pagehelper.Page;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.manage.mapper.DynamicMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCRUD {

    @Autowired
    private DynamicMapper dynamicMapper;
    @Test
    public void testCRUD(){
        Dynamic dynamic = new Dynamic();
        //dynamic.setId(1L);
        dynamic.setContent("1");
        dynamic.setCreateTime("2018");
        dynamic.setTitle("12323");
        dynamic.setTypeId("1");
        dynamicMapper.update(dynamic);
    }

    @Test
    public void testQueryNewsList(){
        Page<Dynamic> newsList = dynamicMapper.findNewsList(1L, "西博会");
        List<Dynamic> result = newsList.getResult();
        System.out.println(result);
    }
}
