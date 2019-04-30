package com.sxx.framework.domain.compus_talk.response;

import com.sxx.framework.domain.compus_talk.dto.CompusTalkDTO;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲结果类〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
@Data
public class CompusTalkResult extends ResponseResult {
    private CompusTalkDTO compusTalkDTO;

    public CompusTalkResult(ResultCode resultCode, CompusTalkDTO compusTalkDTO) {
        super(resultCode);
        this.compusTalkDTO = compusTalkDTO;
    }
}
