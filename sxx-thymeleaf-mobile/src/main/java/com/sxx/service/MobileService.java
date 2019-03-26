package com.sxx.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.mapper.MobileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈官网移动端业务层〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@Service
public class MobileService {
    @Autowired
    private MobileMapper mobileMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取移动端思学行动态列表
     *
     * @param model 返回封装结果
     * @return 页面
     */
    public void getMobileNewsList(Model model) {
        // 获得新闻咨询列表
        PageHelper.startPage(1, 10);
        List<Dynamic> newsList = mobileMapper.findDynamicList(1L);
        // 获得行业动态列表
        PageHelper.startPage(1, 10);
        Page<Dynamic> pageList = mobileMapper.findDynamicList(2L);
        List<Dynamic> industryList = pageList.getResult();
        // 获得学员动态列表
        PageHelper.startPage(1, 10);
        List<Dynamic> studentList = mobileMapper.findDynamicList(3L);
        model.addAttribute("newsList", newsList);
        model.addAttribute("industryList", industryList);
        model.addAttribute("studentList", studentList);
    }

    /**
     * 展示思学行动态页面详情
     *
     * @param model    返回封装结果
     * @param id       当前数据id
     * @param newsStat 当前数据在集合中索引
     * @return 页面
     */
    public void showDynamicListInfo(Model model, Long id, Integer newsStat) {
        Dynamic dynamic = mobileMapper.query(id);
        List<Dynamic> parseArray = mobileMapper.findDynamicList(Long.parseLong(dynamic.getTypeId()));
        int preId = newsStat - 1;
        int nextId = newsStat + 1;
        //把集合存入redis
        String jsonString = JSON.toJSONString(parseArray);
        stringRedisTemplate.boundValueOps("dynamicList").set(jsonString);
        model.addAttribute("dynamic", dynamic);
        model.addAttribute("preId", preId);
        model.addAttribute("nextId", nextId);
        if (preId + 1 == 0) {
            model.addAttribute("preMessage", "没有更多数据了");
        } else {
            model.addAttribute("preMessage", "上一篇");
        }
        if (nextId == parseArray.size()) {
            model.addAttribute("nextMessage", "没有更多数据了");
        } else {
            model.addAttribute("nextMessage", "下一篇");

        }
    }

    /**
     * 上一篇
     *
     * @param model 返回对象
     * @param preId 上一篇在集合中的索引
     * @return 页面
     */
    public void showDynamicPreInfo(Model model, Integer preId) {
        String dynamicList = stringRedisTemplate.boundValueOps("dynamicList").get();
        List<Dynamic> parseArray = JSON.parseArray(dynamicList, Dynamic.class);

        Dynamic dynamic = parseArray.get(preId);
        model.addAttribute("dynamic", dynamic);
        model.addAttribute("nextMessage", "下一篇");
        if (preId - 1 < 0) {
            model.addAttribute("preMessage", "没有更多数据了");
        } else {
            model.addAttribute("preMessage", "上一篇");
        }
        model.addAttribute("preId", preId - 1);
        model.addAttribute("nextId", preId + 1);
    }

    /**
     * 下一篇
     *
     * @param model  返回对象
     * @param nextId 下一篇在集合中的索引
     * @return 页面
     */
    public void showDynamicNextInfo(Model model, Integer nextId) {
        String dynamicList = stringRedisTemplate.boundValueOps("dynamicList").get();
        List<Dynamic> parseArray = JSON.parseArray(dynamicList, Dynamic.class);
        Dynamic dynamic = parseArray.get(nextId);
        model.addAttribute("dynamic", dynamic);
        model.addAttribute("preMessage", "上一篇");
        if (nextId + 1 == parseArray.size()) {
            model.addAttribute("nextMessage", "没有更多数据了");
        } else {
            model.addAttribute("nextMessage", "下一篇");
        }
        model.addAttribute("nextId", nextId + 1);
        model.addAttribute("preId", nextId - 1);
    }
}
