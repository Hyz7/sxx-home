package com.sxx.framework.domain.compus_talk.response;

import com.sxx.framework.domain.compus_talk.dto.CompusTalkListDTO;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲列表结果类〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
@Data
public class CompusTalkListResult extends ResponseResult {
    private List<CompusTalkListDTO> compusTalkListDTOS;

    public CompusTalkListResult(ResultCode resultCode, List<CompusTalkListDTO> compusTalkListDTOS) {
        super(resultCode);
        this.compusTalkListDTOS = compusTalkListDTOS;
    }
}
