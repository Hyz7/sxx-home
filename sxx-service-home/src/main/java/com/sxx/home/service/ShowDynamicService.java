package com.sxx.home.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicListResult2;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.framework.domain.page.PageResult;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.home.mapper.ShowDynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态展示业务层〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ShowDynamicService {
    @Autowired
    private ShowDynamicMapper showDynamicMapper;

    /**
     * 展示思学行动态信息
     *
     * @param page 当前页数
     * @param size 当前页记录数
     * @return 结果集
     */
    public DynamicListResult showNewsInfoList(String name, Integer page, Integer size) {
        // 获得新闻咨询列表
        PageHelper.startPage(page, 6);
        List<Dynamic> newsList = findDynamicList(1L, name);
        if (newsList == null || newsList.size() <= 0) {
            newsList = findDynamicList(1L, null);
        }
        // 获得行业动态列表
        PageHelper.startPage(page, 10);
        Page<Dynamic> pageList = showDynamicMapper.findDynamicList(2L);
        List<Dynamic> industryList = pageList.getResult();
        // 获得学员动态列表
        PageHelper.startPage(page, 5);
        List<Dynamic> studentList = findDynamicList(3L, null);
        return new DynamicListResult(CommonCode.SUCCESS, newsList, industryList, studentList);
    }


    /**
     * 根据分类id获得列表
     *
     * @param typeId 分类id
     * @return 列表
     */
    public List<Dynamic> findDynamicList(Long typeId, String name) {
        if (typeId == 2L) {
            return null;
        }
        Page<Dynamic> pageList;
        if (typeId == 1L) {
            pageList = showDynamicMapper.findNewsList1(typeId, name);
        } else {
            pageList = showDynamicMapper.findDynamicList(typeId);
        }
        return pageList.getResult();
    }

    /**
     * 根据分类id分页模糊查询动态信息
     *
     * @param name   模糊查询标题名称
     * @param typeId 分类id
     * @param page   当前页数
     * @param size   当前页记录数
     * @return 结果集
     */
    public DynamicListResult2 showNewsListByTypeId(String name, Long typeId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<Dynamic> dynamicPage = showDynamicMapper.findDynamicListByTypeId(name,typeId);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(dynamicPage.getTotal());
        List<Dynamic> dynamicList = dynamicPage.getResult();
        if (dynamicList == null || dynamicList.size() <= 0){
            dynamicList = showDynamicMapper.findDynamicList(typeId);
        }
        return new DynamicListResult2(CommonCode.SUCCESS, dynamicList, pageResult);
    }

    /**
     * 根据id查看编辑思学行动态信息
     *
     * @param id 信息id
     * @return 思学行动态信息
     */
    public DynamicResult queryDynamic(Long id) {
        Dynamic dynamic = showDynamicMapper.query(id);
        return new DynamicResult(CommonCode.SUCCESS, dynamic);
    }

}
