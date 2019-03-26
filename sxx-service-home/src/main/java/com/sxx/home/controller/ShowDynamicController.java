package com.sxx.home.controller;

import com.sxx.api.home.ShowDynamicControllerApi;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicListResult2;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.home.service.ShowDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态展示控制层〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@RestController
@RequestMapping("/home/dynamic")
public class ShowDynamicController implements ShowDynamicControllerApi {
    @Autowired
    private ShowDynamicService showDynamicService;

    /**
     * 展示思学行动态信息
     *
     * @param page 当前页数
     * @param size 当前页记录数
     * @return 结果集
     */
    @Override
    @GetMapping("/showNewsInfoList")
    public DynamicListResult showNewsInfoList(String name,
                                              @RequestParam(value = "page", defaultValue = "1")Integer page,
                                              @RequestParam(value = "size", defaultValue = "5")Integer size) {
        return showDynamicService.showNewsInfoList(name, page, size);
    }

    /**
     * 根据id查看编辑思学行动态信息
     *
     * @param id 信息id
     * @return 思学行动态信息
     */
    @Override
    @GetMapping("/queryDynamic")
    public DynamicResult queryDynamic(@RequestParam("id") Long id) {
        return showDynamicService.queryDynamic(id);
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
    @Override
    @GetMapping("/showNewsListByTypeId")
    public DynamicListResult2 showNewsListByTypeId(String name,@RequestParam("typeId") Long typeId,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "size", defaultValue = "5")Integer size) {
        return showDynamicService.showNewsListByTypeId(name, typeId, page, size);
    }


}
