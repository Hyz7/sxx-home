package com.sxx.home.controller;

import com.sxx.api.home.ShowDataDownloadControllerApi;
import com.sxx.framework.domain.data.response.DataEntityResult;
import com.sxx.framework.domain.data.response.DataResult;
import com.sxx.framework.domain.response.DownloadResult;
import com.sxx.home.service.ShowDataDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 〈一句话功能简述〉<br>
 * 〈资料下载模块控制层〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@RestController
@RequestMapping("/home/data")
public class ShowDataDownloadController implements ShowDataDownloadControllerApi {
    @Autowired
    private ShowDataDownloadService showDataDownloadService;

    /**
     * @param dataClassName    当前分类名称
     * @param dataCategoryName 当前分类行业名称
     * @param name             查询名称
     * @param page             当前页数
     * @param size             当前页记录数
     * @return 结果
     */
    @Override
    @GetMapping("/findDataList")
    public DataEntityResult findDataList(String dataClassName, String dataCategoryName, String name,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return showDataDownloadService.findDataList(dataClassName, dataCategoryName, name, page, size);
    }

    /**
     * 资料下载
     *
     * @param dataId 下载的数据id
     * @return 结果
     */
    @Override
    @GetMapping("/downloadData")
    public DownloadResult downloadData(@RequestParam("dataId") Integer dataId) {
        return showDataDownloadService.downloadData(dataId);
    }

    /**
     * 根据资料下载数据id查询资料详情
     *
     * @param dataId 资料id
     * @return 结果
     */
    @Override
    @GetMapping("/findDataInfoByDataId")
    public DataResult findDataInfoByDataId(@RequestParam("dataId") Integer dataId) {
        return showDataDownloadService.findDataInfoByDataId(dataId);
    }
}
