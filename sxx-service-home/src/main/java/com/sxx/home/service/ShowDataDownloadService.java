package com.sxx.home.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.domain.data.response.DataEntityResult;
import com.sxx.framework.domain.data.response.DataResult;
import com.sxx.framework.domain.page.PageResult;
import com.sxx.framework.domain.response.DownloadResult;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.home.mapper.ShowDataDownloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈资料下载模块业务层〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Service
@Transactional(rollbackOn = Exception.class)
@SuppressWarnings("Duplicates")
public class ShowDataDownloadService {
    @Autowired
    private ShowDataDownloadMapper dataDownloadMapper;

    /**
     * 分页模糊查询数据
     *
     * @param dataClassName    当前分类名称
     * @param dataCategoryName 当前分类行业名称
     * @param name             查询名称
     * @param page             当前页数
     * @param size             当前页记录数
     * @return 结果
     */
    public DataEntityResult findDataList(String dataClassName, String dataCategoryName, String name, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<DataEntity> queryData = dataDownloadMapper.findDataList(dataClassName, dataCategoryName, name, page, size);
        List<DataEntity> entityList = queryData.getResult();
        PageResult pageResult = new PageResult();
        pageResult.setTotal(queryData.getTotal());
        return new DataEntityResult(CommonCode.SUCCESS, entityList,pageResult);
    }

    /**
     * 资料下载
     *
     * @param dataId 下载的数据id
     * @return 结果
     */
    public DownloadResult downloadData(Integer dataId) {
        // 根据id查询对应文件key
        DataEntity dataEntity = dataDownloadMapper.findByDataId(dataId);
        String key = dataEntity.getDataKey();
        // 文件下载,返回文件的presign
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(AwsS3Bucket.BAI_PI_SHU_BUCKET, key);
        // 设置过期时间1小时
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        urlRequest.setExpiration(expiration);
        URL url = s3.generatePresignedUrl(urlRequest);
        // 下载次数增加
        addDownloadCount(dataId);
        return new DownloadResult(CommonCode.SUCCESS,url.toString());
    }

    /**
     * 下载成功后增加下载次数
     * @param dataId 资料id
     */
    private void addDownloadCount(Integer dataId) {
        DataEntity dataEntity = dataDownloadMapper.findByDataId(dataId);
        dataEntity.setDownloadCount(dataEntity.getDownloadCount() + 1);
        dataDownloadMapper.updateData(dataEntity);
    }


    /**
     * 根据资料下载数据id查询资料详情
     *
     * @param dataId 资料id
     * @return 结果
     */
    public DataResult findDataInfoByDataId(Integer dataId) {
        DataEntity dataEntity = dataDownloadMapper.findByDataId(dataId);
        return new DataResult(CommonCode.SUCCESS,dataEntity);
    }
}
