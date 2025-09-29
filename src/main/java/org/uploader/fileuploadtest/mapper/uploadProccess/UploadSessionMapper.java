package org.uploader.fileuploadtest.mapper.uploadProccess;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Component;
import org.uploader.fileuploadtest.dto.response.upload.UploadSessionResponse;
import org.uploader.fileuploadtest.entities.UploadSession;

@Component
public class UploadSessionMapper {
    private final char[] keyComponents = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public UploadSessionResponse createUploadResponse(
            String uploadId,
            int totalChunks,
            UploadSession.Status status
    ){
        return UploadSessionResponse
                .builder()
                .uploadId(uploadId)
                .totalChunks(totalChunks)
                .status(status)
                .build();
    }

    public UploadSession createUploadSession(
            String fileName,
            int totalChunks,
            String contentType
    ){
        return UploadSession
                .builder()
                .uploadId(uploadIdGenerator())
                .fileName(fileName)
                .totalChunks(totalChunks)
                .contentType(contentType)
                .status(UploadSession.Status.IN_PROGRESS)
                .build();
    }

    //-------------------reusable methods----------------------

    private String uploadIdGenerator(){
        int keySize = 12;
        return "upload_" + NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                keyComponents,
                keySize
        );
    }
}
