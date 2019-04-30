package com.sxx.home.controller;

import com.sxx.api.compus_talk.CompusTalkControllerApi;
import com.sxx.framework.domain.compus_talk.response.CompusTalkListResult;
import com.sxx.framework.domain.compus_talk.response.CompusTalkResult;
import com.sxx.home.service.CompusTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲控制层〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
@RestController
@RequestMapping("/home")
public class CompusTalkController implements CompusTalkControllerApi {
    @Autowired
    private CompusTalkService compusTalkService;
    /**
     * 查询校园宣讲列表
     *
     * @return 结果
     */
    @Override
    @GetMapping("/compusTalk")
    public CompusTalkListResult findCompusTalkList() {
        return compusTalkService.findCompusTalkList();
    }

    /**
     * 根据id查询校园宣讲内容
     *
     * @param id id
     * @return 结果
     */
    @Override
    @GetMapping("/compusTalk/{id}")
    public CompusTalkResult findCompusTalkInfo(@PathVariable("id") String id) {
        return compusTalkService.findCompusTalkInfo(id);
    }
}
