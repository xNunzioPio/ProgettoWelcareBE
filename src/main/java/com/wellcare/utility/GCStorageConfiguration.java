package com.wellcare.utility;

import java.io.*;


import com.google.cloud.storage.*;
import com.wellcare.controller.dto.risorsa.FileStorageDownload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import com.google.auth.oauth2.GoogleCredentials;

@Component
public class GCStorageConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GCStorageConfiguration.class);

    @Value("${gcs.projectId:decent-glazing-374822}")
    private String projectId;

    @Value("${gcs.bucketName:wellcare}")
    private String bucketName;

    public void writeGCSFile(MultipartFile multipartFile, String filePath) throws IOException {

        Storage storage = this.initGCStorage();

        String fileName = multipartFile.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, filePath);
        BlobInfo blobInfo = null;

        byte[] content;

        if (!checkFileExtension(fileName)) {
            blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/*").build();
            content = multipartFile.getBytes();
        }
        else {
            blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/*").build();
            content = multipartFile.getBytes();
        }

        storage.create(blobInfo, content);

    }

    public FileStorageDownload readFGCSFile(String fileFullPath, String filename) throws FileNotFoundException, IOException {

        Storage storage = this.initGCStorage();

        BlobId blobId = BlobId.of(bucketName, fileFullPath);

        log.info("url: " + blobId.getName());

        Blob blob = storage.get(blobId);

        byte[] byteArray = null;

        if (blob.getContentType().contains("image/*")) {
            byteArray = storage.readAllBytes(blobId);
        } else {
            byteArray = blob.getContent();

        }

        FileStorageDownload fsd = new FileStorageDownload();
        fsd.setFilename(filename);
        fsd.setByteArray(byteArray);

        return fsd;

    }

    private Storage initGCStorage() throws FileNotFoundException, IOException {
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(
                        new ClassPathResource("/decent-glazing-374822-93c60dd21c98.json").getInputStream()))
                .setProjectId(projectId).build().getService();
    }

    private Boolean checkFileExtension(String fileName) {

        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            String[] formatAllowed = { ".jpg", ".jpeg", ".png" };
            for (String ext : formatAllowed) {
                if (fileName.endsWith(ext)) {
                    return true;
                }
            }
        }
        return false;
    }
}
