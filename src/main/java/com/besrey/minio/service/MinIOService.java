package com.besrey.minio.service;


import io.minio.MinioClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MinIOService {

    @Resource
    private MinioClient minioClient;


    public void testMinIOClient() {
        System.out.println(minioClient);
    }
}
