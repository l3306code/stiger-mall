package com.besrey.minio;

import com.besrey.minio.service.MinIOService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBoot01MinioApplicationTests {


    @Resource
    private MinIOService minIOService;


    @Resource
    private MinioClient minioClient;

    @Test
    void contextLoads() {
        minIOService.testMinIOClient();
    }


    @Test
    void test01() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean res = minioClient.bucketExists(BucketExistsArgs.builder().bucket("myfile").build());
        System.out.println(res);
    }

    @Test
    void test02() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
            String bucket = "jl-file";

    // 1. 创建 bucket（如果不存在）
            if (!minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucket).build()
            )) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucket).build()
                );
            }

    // 2. 设置公开读 policy
            String policy = "{\n" +
                    "  \"Version\": \"2012-10-17\",\n" +
                    "  \"Statement\": [\n" +
                    "    {\n" +
                    "      \"Effect\": \"Allow\",\n" +
                    "      \"Principal\": \"*\",\n" +
                    "      \"Action\": [\"s3:GetObject\"],\n" +
                    "      \"Resource\": [\"arn:aws:s3:::jl-file/*\"]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucket)
                            .config(policy)
                            .build()
            );
    }


    @Test
    void test03() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<Bucket> bucketList = minioClient.listBuckets();

        bucketList.forEach(bucket -> System.out.println(bucket.name()));
    }


    @Test
    void test04() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket("jl-file").build());
    }


    @Test
    void test05() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Pictures\\壁纸\\backgroud.jpg");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "image/jpeg");

        minioClient.putObject(
                PutObjectArgs.builder().
                        bucket("jl-file").
                        object("5.jpg").
                        stream(Files.newInputStream(file.toPath()), file.length(), -1).
                        headers(headers).
                        build()
        );
    }


    @Test
    void test06() throws Exception {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("jl-file")
                        .object("2.jpg")
                        .filename("C:\\Users\\Administrator\\Pictures\\壁纸\\backgroud.jpg")
                        .build()
        );
    }


    @Test
    void test07() throws Exception {
        StatObjectResponse statObjectResponse = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket("jl-file")
                        .object("1.jpg")
                        .build()
        );

        System.out.println(statObjectResponse.toString());
    }


    @Test
    void test08() throws Exception {
        String objectUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket("jl-file")
                        .object("5.jpg")
                        .method(Method.GET)
                        .build()
        );


        System.out.println(objectUrl);

    }


    @Test
    void test09() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse objectUrl = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("jl-file")
                        .object("7.jpg")
                        .build()
        );

        System.out.println(objectUrl.toString());
    }


    @Test
    void test10(){
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket("jl-file")
                        .build()
        );

        results.forEach(
                itemResult -> {
                    try {
                        Item item = itemResult.get();
                        System.out.println(item.objectName());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

    }


}
