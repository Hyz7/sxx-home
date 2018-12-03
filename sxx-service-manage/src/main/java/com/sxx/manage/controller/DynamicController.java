package com.sxx.manage.controller;

import com.sxx.api.dynamic.DynamicControllerApi;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.framework.domain.dynamic.response.DynamicTypeResponse;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态展示控制层〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicController implements DynamicControllerApi {
    @Autowired
    private DynamicService dynamicService;
    /**
     * 展示思学行动态列表
     *
     * @return 思学行动态列表响应结果
     */
    @Override
    @GetMapping("/showDynamicTypeList")
    public DynamicTypeResponse showDynamicTypeList() {
        return dynamicService.showDynamicTypeList();
    }

    /**
     * 展示交易信息:公司和服务商列表
     *
     * @param typeId 分类id
     * @return 新闻资讯列表结果
     */
    @Override
    @GetMapping("/showNewsInfoList")
    public DynamicListResult showNewsInfoList(Long typeId) {
        return dynamicService.showNewsInfoList(typeId);
    }
    /**
     * 添加思学行动态信息
     * @param dynamic 思学行动态信息
     * @return 结果集
     */
    @Override
    @PostMapping("/addDynamic")
    public ResponseResult addDynamic(Dynamic dynamic) {
        return dynamicService.addDynamic(dynamic);
    }
    /**
     * 删除思学行动态信息
     * @param id 信息id
     * @return 结果集
     */
    @Override
    @DeleteMapping("/delDynamic")
    public ResponseResult delDynamic(Long id) {
        return null;
    }
    /**
     * 根据id查看编辑思学行动态信息
     * @param id 信息id
     * @return 思学行动态信息
     */
    @Override
    @GetMapping("/queryDynamic")
    public DynamicResult queryDynamic(Long id) {
        return dynamicService.queryDynamic(id);
    }
    /**
     * 更新修改思学行动态信息
     * @param dynamic 动态信息
     * @return 结果集
     */
    @Override
    @PostMapping("/updateDynamic")
    public ResponseResult updateDynamic(Dynamic dynamic) {
        return dynamicService.updateDynamic(dynamic);
    }
}
