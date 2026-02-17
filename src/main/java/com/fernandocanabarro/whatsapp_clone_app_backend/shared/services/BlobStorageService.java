package com.fernandocanabarro.whatsapp_clone_app_backend.shared.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.exceptions.BadRequestException;

@Service
public class BlobStorageService {
    private final BlobContainerClient blobContainerClient;

    public BlobStorageService(
        @Value("${azure.storage.connection-string}") String connectionString,
        @Value("${azure.storage.blob-container-name}") String containerName
    ) {
        this.blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();
    }

    public String uploadBlob(String blobName, MultipartFile file) {
        try {
            InputStream dataStream = new ByteArrayInputStream(file.getBytes());
            this.blobContainerClient.getBlobClient(blobName).upload(dataStream, file.getSize(), true);
            return this.blobContainerClient.getBlobClient(blobName).getBlobUrl();
        }
        catch (IOException e) {
            throw new BadRequestException("Failed to upload blob: " + e.getMessage());
        }
    }

    public byte[] downloadBlob(String blobName) {
        return this.blobContainerClient.getBlobClient(blobName).downloadContent().toBytes();
    }

}
