package org.uploader.fileuploadtest.mapper.uploadProccess;

import org.springframework.stereotype.Component;
import org.uploader.fileuploadtest.dto.response.upload.UploadCompletedResponse;
import org.uploader.fileuploadtest.entities.UploadSession;

@Component
public class CompletedResponse {

    public UploadCompletedResponse createResponse(
            String fileName,
            String fileSize,
            String contentType,
            UploadSession.Status status
    ){
        return UploadCompletedResponse
                .builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .contentType(contentType)
                .status(status)
                .build();
    }
}
