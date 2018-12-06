package com.sxx.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.DynamicType;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.framework.domain.dynamic.response.DynamicTypeResponse;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.mapper.DynamicMapper;
import org.apache.commons.lang3.StringUtils;
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
public class DynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;

    /**
     * 展示思学行动态列表
     *
     * @return 思学行动态列表响应结果
     */
    public DynamicTypeResponse showDynamicTypeList() {
        List<DynamicType> dynamicType = dynamicMapper.findDynamicType();
        return new DynamicTypeResponse(CommonCode.SUCCESS, dynamicType);
    }

    /**
     * 根据分类id展示思学行动态信息
     *
     * @param typeId 动态分类id
     * @param page   当前页数
     * @param size   当前页记录数
     * @return 结果集
     */
    public DynamicListResult showNewsInfoList(Long typeId, Integer page, Integer size) {
        if (typeId == null) {
            return new DynamicListResult(CommonCode.FAIL, null);
        }
        if (page == null){
            page = 1;
        }
        if (size == null){
            size = 5;
        }
        PageHelper.startPage(page,size);
        Page<Dynamic> pageList = dynamicMapper.findNewsInfoListByTypeId(typeId);
        List<Dynamic> dynamicList = pageList.getResult();
        return new DynamicListResult(CommonCode.SUCCESS, dynamicList);
    }

    /**
     * 添加思学行动态信息
     *
     * @param dynamic 思学行动态信息
     * @return 结果集
     */
    public ResponseResult addDynamic(Dynamic dynamic) {
        if (dynamic == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        dynamicMapper.save(dynamic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除思学行动态信息
     *
     * @param id 信息id
     * @return 结果集
     */
    public ResponseResult delDynamic(Long id) {
        if (id == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        dynamicMapper.delete(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id查看编辑思学行动态信息
     *
     * @param id 信息id
     * @return 思学行动态信息
     */
    public DynamicResult queryDynamic(Long id) {
        if (id == null) {
            return new DynamicResult(CommonCode.FAIL, null);
        }
        Dynamic dynamic = dynamicMapper.query(id);
        return new DynamicResult(CommonCode.SUCCESS, dynamic);
    }

    /**
     * 更新修改思学行动态信息
     *
     * @param dynamic 动态信息
     * @return 结果集
     */
    public ResponseResult updateDynamic(Dynamic dynamic) {
        if (dynamic.getId() == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        dynamicMapper.update(dynamic);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
