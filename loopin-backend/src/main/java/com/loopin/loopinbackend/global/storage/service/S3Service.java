package com.loopin.loopinbackend.global.storage.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloudfront.domain}")
    private String cloudFrontDomain;

    /**
     * 파일명을 UUID 채번 후 PresignedUrl을 생성후 반환
     * @param originalFileName
     * @param path
     * @return
     */
    public Map<String, String> generatePresignedUrl(String originalFileName, String path) {
        String fileExtension = "";
        if (originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // UUID를 이용한 파일명 변환
        String newFileName = UUID.randomUUID() + fileExtension;
        String contentType = getContentType(fileExtension);

        // PresignedUrl 만료 시간 설정
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5);

        // PUT 요청 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, path + newFileName) // 경로(파일이름)
                        .withMethod(HttpMethod.PUT)
                        .withContentType(contentType)
                        .withExpiration(expiration);

        // PresignedUrl 생성
        URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        Map<String, String> urls = new HashMap<>();

        urls.put("presignedUrl", presignedUrl.toString());
        urls.put("fileUrl", cloudFrontDomain + "/" + path + newFileName);

        return urls;
    }

    private String getContentType(String fileExtension) {
        return switch (fileExtension.toLowerCase()) {
            case ".jpg", ".jpeg" -> "image/jpeg";
            case ".png" -> "image/png";
            case ".gif" -> "image/gif";
            case ".svg" -> "image/svg+xml";
            default -> "application/octet-stream"; // 기본값
        };
    }
}
