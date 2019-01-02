package com.sxx.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.DynamicType;
import com.sxx.framework.domain.dynamic.ext.DynamicExt;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicListResult2;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.framework.domain.dynamic.response.DynamicTypeResponse;
import com.sxx.framework.domain.page.PageResult;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.mapper.DynamicMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * 展示思学行动态信息
     *
     * @param page   当前页数
     * @param size   当前页记录数
     * @return 结果集
     */
    public DynamicListResult showNewsInfoList(String name,Integer page, Integer size) {
        if (page == null){
            page = 1;
        }
        if (size == null){
            size = 5;
        }
        // 获得新闻咨询列表
        PageHelper.startPage(page,6);
        List<DynamicExt> newsList = findDynamicList(1L,name);
        if (newsList == null || newsList.size() <= 0){
            newsList = findDynamicList(1L,null);
        }
        // 获得行业动态列表
        PageHelper.startPage(page,10);
        Page<Dynamic> pageList = dynamicMapper.findDynamicList(2L);
        List<Dynamic> industryList = pageList.getResult();
        // 获得学员动态列表
        PageHelper.startPage(page,5);
        List<DynamicExt> studentList = findDynamicList(3L,null);
        return new DynamicListResult(CommonCode.SUCCESS, newsList,industryList,studentList);
    }

    /**
     * 添加思学行动态信息
     *
     * @param dynamic 思学行动态信息
     * @return 结果集
     */
    public ResponseResult addDynamic(Dynamic dynamic) {
        if (dynamic == null || dynamic.getTypeId() == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        if (dynamic.getCreateTime() == null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String createTime = simpleDateFormat.format(new Date());
            dynamic.setCreateTime(createTime);
        }
        dynamicMapper.save(dynamic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除思学行动态信息
     *
     * @param ids 信息id
     * @return 结果集
     */
    public ResponseResult delDynamic(Long[] ids) {
        if (ids == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        for (Long id : ids) {
            dynamicMapper.delete(id);
        }
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

    /**
     * 根据分类id获得列表
     * @param typeId 分类id
     * @return 列表
     */
    public List<DynamicExt> findDynamicList(Long typeId,String name){
        if (typeId == 2L){
            return null;
        }
        Page<Dynamic> pageList;
        if (typeId == 1L){
            pageList = dynamicMapper.findNewsList(typeId,name);
        }else {
            pageList = dynamicMapper.findDynamicList(typeId);
        }
        List<Dynamic> dynamicList = pageList.getResult();
        List<DynamicExt> dynamicExtList = new ArrayList<>();
        for (Dynamic dynamic : dynamicList) {
            DynamicExt dynamicExt = new DynamicExt();
            // 获得内容
            String content = dynamic.getContent();
            // 根据正则表达式去掉html标签
            if (content != null){
                content=content.replaceAll("<[.[^<]]*>", "");
                if (content.length() > 100){
                    content = content.substring(0,100);
                }
            }
            // 添加内容
            dynamicExt.setContentKey(content);
            BeanUtils.copyProperties(dynamic,dynamicExt);
            dynamicExtList.add(dynamicExt);
        }
        return dynamicExtList;
    }

    /**
     *
     * @param typeId 分类id
     * @param page   当前页数
     * @param size   当前页记录数
     * @return 结果集
     */
    public DynamicListResult2 showNewsListByTypeId(String typeId, Integer page, Integer size) {
        PageResult pageResult = new PageResult();
        if (typeId == null){
            return new DynamicListResult2(CommonCode.FAIL,null,pageResult);
        }
        if (page == null){
            page = 1;
        }
        if (size == null){
            size = 5;
        }
        PageHelper.startPage(page,size);
        Page<Dynamic> dynamicPage = dynamicMapper.findDynamicListByTypeId(typeId);
        pageResult.setTotal(dynamicPage.getTotal());
        List<Dynamic> dynamicList = dynamicPage.getResult();
        List<Dynamic> newList = new ArrayList<>();
        for (Dynamic dynamic : dynamicList) {
            String content = dynamic.getContent();
            dynamic.setHtml(content);
            if (content!=null){
                content=content.replaceAll("<[.[^<]]*>", "");
                dynamic.setContent(content);
            }
            newList.add(dynamic);
        }
        return new DynamicListResult2(CommonCode.SUCCESS,newList,pageResult);

    }

}
