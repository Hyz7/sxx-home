package com.sxx.manage.service;

import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.DynamicType;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.framework.domain.dynamic.response.DynamicTypeResponse;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.mapper.DynamicMapper;
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
        return new DynamicTypeResponse(CommonCode.SUCCESS,dynamicType);
    }
    /**
     * 展示交易信息:公司和服务商列表
     *
     * @param typeId 分类id
     * @return 新闻资讯列表结果
     */
    public DynamicListResult showNewsInfoList(Long typeId) {
        List<Dynamic> newsInfoListByTypeId = dynamicMapper.findNewsInfoListByTypeId(typeId);
        return new DynamicListResult(CommonCode.SUCCESS,newsInfoListByTypeId);
    }
    /**
     * 添加思学行动态信息
     * @param dynamic 思学行动态信息
     * @return 结果集
     */
    public ResponseResult addDynamic(Dynamic dynamic) {
        try {
            dynamicMapper.save(dynamic);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
            return new ResponseResult(CommonCode.SUCCESS);
    }
    /**
     * 删除思学行动态信息
     * @param id 信息id
     * @return 结果集
     */
    public ResponseResult delDynamic(Long id) {
        try {
            dynamicMapper.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id查看编辑思学行动态信息
     * @param id 信息id
     * @return 思学行动态信息
     */
    public DynamicResult queryDynamic(Long id) {
        if (id == null){
            return new DynamicResult(CommonCode.FAIL,null);
        }
        Dynamic dynamic = dynamicMapper.query(id);
        return new DynamicResult(CommonCode.SUCCESS,dynamic);
    }
    /**
     * 更新修改思学行动态信息
     * @param dynamic 动态信息
     * @return 结果集
     */
    public ResponseResult updateDynamic(Dynamic dynamic) {
        try {
            dynamicMapper.update(dynamic);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
