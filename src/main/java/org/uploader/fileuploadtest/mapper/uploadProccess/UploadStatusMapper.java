package org.uploader.fileuploadtest.mapper.uploadProccess;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;
import org.uploader.fileuploadtest.dto.response.upload.UploadStatusResponse;
import org.uploader.fileuploadtest.entities.UploadSession;

import java.time.Instant;
import java.util.Set;

@Component
public class UploadStatusMapper {

    public UploadStatusResponse createResponse(
            String fileName,

            UploadSession.Status status,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
            Instant createdAt,

            Long totalChunks,

            Long receivedChunks,

            Set<Long> chunkIndices
    ){
        return UploadStatusResponse
                .builder()
                .fileName(fileName)
                .status(status)
                .CreatedAt(createdAt)
                .totalChunks(totalChunks)
                .receivedChunks(receivedChunks)
                .chunkIndices(chunkIndices)
                .build();
    }
}
