package com.sxx.home.service;

import com.sxx.framework.domain.compus_talk.CompusTalk;
import com.sxx.framework.domain.compus_talk.dto.CompusTalkDTO;
import com.sxx.framework.domain.compus_talk.dto.CompusTalkListDTO;
import com.sxx.framework.domain.compus_talk.response.CompusTalkListResult;
import com.sxx.framework.domain.compus_talk.response.CompusTalkResult;
import com.sxx.framework.exception.ExceptionCast;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.home.mapper.CompusTalkMapper;
import com.sxx.home.mapper.CompusTalkRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.Pipe;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲管理业务层〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
@Service
public class CompusTalkService {
    @Autowired
    private CompusTalkRepository compusTalkRepository;
    @Autowired
    private CompusTalkMapper compusTalkMapper;

    /**
     * 查询校园宣讲列表
     *
     * @return 结果
     */
    public CompusTalkListResult findCompusTalkList() {
        List<CompusTalkListDTO> compusTalkListDTOList = compusTalkMapper.findCompusTalkList();
        return new CompusTalkListResult(CommonCode.SUCCESS,compusTalkListDTOList);
    }

    /**
     * 根据id查询校园宣讲内容
     *
     * @param id id
     * @return 结果
     */
    public CompusTalkResult findCompusTalkInfo(String id) {
        if (StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        Optional<CompusTalk> optional = compusTalkRepository.findById(id);
        if (!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        CompusTalk compusTalk = optional.get();
        CompusTalkDTO compusTalkDTO = new CompusTalkDTO();
        BeanUtils.copyProperties(compusTalk,compusTalkDTO);
        return new CompusTalkResult(CommonCode.SUCCESS,compusTalkDTO);
    }
}
