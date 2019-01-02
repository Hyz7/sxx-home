package com.sxx.manage;

import com.github.pagehelper.Page;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.manage.mapper.DynamicMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCRUD {

    @Autowired
    private DynamicMapper dynamicMapper;
    @Test
    public void testCRUD(){
        Dynamic dynamic = new Dynamic();
        //dynamic.setId(1L);
        dynamic.setContent("1");
        dynamic.setCreateTime("2018");
        dynamic.setTitle("12323");
        dynamic.setTypeId("1");
        dynamicMapper.update(dynamic);
    }

    @Test
    public void testQueryNewsList(){
        Page<Dynamic> newsList = dynamicMapper.findNewsList(1L, "西博会");
        List<Dynamic> result = newsList.getResult();
        System.out.println(result);
    }

    @Test
    public void testUseS3(){
        S3Client s3 = S3Client.builder().build();
        // 创建s3名称
        String bucket = "bucket" + System.currentTimeMillis();
        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .build())
                .build();
        CreateBucketResponse bucket1 = s3.createBucket(createBucketRequest);
        System.out.println(bucket1);
        // 获得桶名称
        String substring = bucket1.location().substring(7, bucket1.location().indexOf("."));
        System.out.println(substring);
        s3.close();
    }

    private static ByteBuffer getRandomByteBuffer(int size) throws IOException {
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }
}
