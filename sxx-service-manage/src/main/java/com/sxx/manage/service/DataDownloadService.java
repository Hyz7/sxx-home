package com.sxx.manage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.domain.data.response.DataEntityResult;
import com.sxx.framework.domain.response.DownloadResult;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.mapper.DataDownloadMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class DataDownloadService {
    @Autowired
    private DataDownloadMapper dataDownloadMapper;
    /**
     * 添加数据
     *
     * @param dataEntity 数据
     * @return 结果
     */
    public ResponseResult insertData(MultipartFile file, DataEntity dataEntity) {
        if (dataEntity == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        // 设置分类
        if (dataEntity.getDataClassId() == null) {
            dataEntity.setDataClassId(1);
        }
        // 设置行业类别
        if (dataEntity.getDataCategoryId() == null) {
            dataEntity.setDataCategoryId(1);
        }
        // 设置行业类别
        if (dataEntity.getDownloadCount() == null) {
            dataEntity.setDownloadCount(1000);
        }

        // 设置时间
        if (dataEntity.getCreateTime() == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = simpleDateFormat.format(new Date());
            dataEntity.setCreateTime(createTime);
        }
        if (file != null){
            // 文件上传
            this.fileUpload(file, dataEntity, AwsS3Bucket.BAI_PI_SHU_BUCKET);
        }
        dataDownloadMapper.insertData(dataEntity);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 上传文件并保存信息
     *
     * @param file       文件
     * @param dataEntity 数据实体
     * @param bucket     对象名
     */
    private void fileUpload(MultipartFile file, DataEntity dataEntity, String bucket) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        try {
            // 获得文件全称
            String originalFilename = file.getOriginalFilename();
            // 获得文件名
            String filename = originalFilename.substring(0, originalFilename.indexOf("."));
            // 获得文件后缀
            String substring = originalFilename.substring(originalFilename.indexOf("."));
            // 生成唯一文件名
            String key = filename + "SXX_" + System.currentTimeMillis() + substring;
            // 保存文件
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            PutObjectResult putObjectResult = s3.putObject(bucket, key, file.getInputStream(), objectMetadata);
            // 保存文件key和文件类型
            dataEntity.setDataKey(key);
            dataEntity.setFileType(substring);
            dataEntity.setETag(putObjectResult.getETag());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 5;
        }
        PageHelper.startPage(page, size);
        Page<DataEntity> queryData = dataDownloadMapper.findDataList(dataClassName, dataCategoryName, name, page, size);
        List<DataEntity> entityList = queryData.getResult();
        List<DataEntity> result = new ArrayList<>();

        for (DataEntity dataEntity : entityList) {
            String content = dataEntity.getDataDesc();
            // 根据正则表达式去掉html标签
            if (content != null) {
                content = content.replaceAll("<[.[^<]]*>", "");
                if (content.length() > 100) {
                    content = content.substring(0, 100);
                }
            }
            dataEntity.setDataDesc(content);
            result.add(dataEntity);
        }
        return new DataEntityResult(CommonCode.SUCCESS, result);
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
        URL url = s3.generatePresignedUrl(urlRequest);
        return new DownloadResult(CommonCode.SUCCESS,url.toString());
    }

    /**
     * 根据id删除数据
     *
     * @param ids id
     * @return 结果
     */
    public ResponseResult deleteData(Integer[] ids) {
        if (ids == null) {
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        for (Integer id : ids) {
            // 删除资料数据
            DataEntity dataEntity = dataDownloadMapper.findByDataId(id);
            String dataKey = dataEntity.getDataKey();
            if (dataKey != null){
                this.fileDelete(AwsS3Bucket.BAI_PI_SHU_BUCKET, dataKey);
            }
            // 删除数据库数据
            dataDownloadMapper.deleteData(id);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更新资料下载数据
     *
     * @param file       更新文件
     * @param dataEntity 新数据
     * @return 结果
     */
    public ResponseResult updateData(MultipartFile file, DataEntity dataEntity) {
        if (dataEntity == null){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        if (file != null){
            // 判断文件是否更新
            DataEntity entity = dataDownloadMapper.findByDataId(dataEntity.getDataId());
            String filename = file.getOriginalFilename();
            String dataKey = entity.getDataKey();
            if (dataKey != null){
                if (!filename.equals(dataKey)) {
                    // 更新文件
                    // 删除
                    boolean isDelete = this.fileDelete(AwsS3Bucket.BAI_PI_SHU_BUCKET, dataKey);
                    if (!isDelete) {
                        return new ResponseResult(CommonCode.FAIL);
                    }
                    // 上传
                    this.fileUpload(file, dataEntity,AwsS3Bucket.BAI_PI_SHU_BUCKET);
                }
            }else {
                this.fileUpload(file, dataEntity,AwsS3Bucket.BAI_PI_SHU_BUCKET);
            }

        }
        dataDownloadMapper.updateData(dataEntity);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除文件
     *
     * @param bucket 桶名
     * @param key    文件key
     * @return 是否成功
     */
    private boolean fileDelete(String bucket, String key) {
        try {
            final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
            s3.deleteObject(bucket,key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
