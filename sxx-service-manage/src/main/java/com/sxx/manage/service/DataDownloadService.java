package com.sxx.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.domain.data.response.DataEntityResult;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.mapper.DataDownloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.ByteBuffer;
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
        S3Client s3 = S3Client.builder().build();
        try {
            byte[] bytes = file.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, 0, bytes.length);
            // 获得文件全称
            String originalFilename = file.getOriginalFilename();
            // 获得文件名
            String filename = originalFilename.substring(0, originalFilename.indexOf("."));
            // 获得文件后缀
            String substring = originalFilename.substring(originalFilename.indexOf("."));
            // 生成唯一文件名
            String key = filename + "SXX_" + System.currentTimeMillis() + substring;
            // 保存文件
            PutObjectResponse putObjectResponse = s3.putObject(PutObjectRequest.builder().key(key).bucket(bucket)
                            .build(),
                    RequestBody.fromByteBuffer(byteBuffer));
            // 保存文件key和文件类型
            dataEntity.setDataKey(key);
            dataEntity.setFileType(substring);
            dataEntity.setETag(putObjectResponse.eTag());
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
    public ResponseResult downloadData(Integer dataId) {
        // 根据id查询对应文件key
        DataEntity dataEntity = dataDownloadMapper.findByDataId(dataId);
        String dataTitle = dataEntity.getDataTitle();
        String key = dataEntity.getDataKey();
        String fileType = dataEntity.getFileType();
        // 拼接文件名
        String fileName = dataTitle + System.currentTimeMillis() + fileType;
        S3Client s3 = S3Client.builder().build();
        // 文件下载
        s3.getObject(GetObjectRequest.builder().bucket(AwsS3Bucket.BAI_PI_SHU_BUCKET).key(key).build(),
                ResponseTransformer.toFile(Paths.get("D:/" + fileName)));
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除数据
     *
     * @param ids id
     * @return 结果
     */
    public ResponseResult deleteData(Integer[] ids) {
        if (ids == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        for (Integer id : ids) {
            // 删除资料数据
            DataEntity dataEntity = dataDownloadMapper.findByDataId(id);
            String dataKey = dataEntity.getDataKey();
            this.fileDelete(AwsS3Bucket.BAI_PI_SHU_BUCKET, dataKey);
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
    public ResponseResult updateData(MultipartFile file, @RequestPart("dataEntity") DataEntity dataEntity) {
        // 判断文件是否更新
        DataEntity entity = dataDownloadMapper.findByDataId(dataEntity.getDataId());
        String filename = file.getOriginalFilename();
        String dataKey = entity.getDataKey();
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
        S3Client s3 = S3Client.builder().build();
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
        s3.deleteObject(deleteObjectRequest);
        return true;
    }
}
