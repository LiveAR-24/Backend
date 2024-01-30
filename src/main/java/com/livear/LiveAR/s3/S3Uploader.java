package com.livear.LiveAR.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3 파일 한장 업로드
     */
    public String uploadFile(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String url) {
        try{
            String convertUrl = URLDecoder.decode(url.split("/")[3], StandardCharsets.UTF_8.name());
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, convertUrl));
        }catch (UnsupportedEncodingException e){
            log.info(e.getMessage());
        }
    }

    /**
     * 파일 이름 생성
     */
    private String createFileName(String fileName)  {
        return UUID.randomUUID() + "_" + fileName;
    }

}
