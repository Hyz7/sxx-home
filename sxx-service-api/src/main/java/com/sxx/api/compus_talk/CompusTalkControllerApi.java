/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲管理接口〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
package com.sxx.api.compus_talk;

import com.sxx.framework.domain.compus_talk.response.CompusTalkListResult;
import com.sxx.framework.domain.compus_talk.response.CompusTalkResult;
import io.swagger.annotations.Api;

@Api(value = "管理门户网站校园宣讲接口", description = "提供对校园宣讲内容的查询操作")
public interface CompusTalkControllerApi {

    /**
     * 查询校园宣讲列表
     *
     * @return 结果
     */
    CompusTalkListResult findCompusTalkList();

    /**
     * 根据id查询校园宣讲内容
     *
     * @param id id
     * @return 结果
     */
    CompusTalkResult findCompusTalkInfo(String id);
}
