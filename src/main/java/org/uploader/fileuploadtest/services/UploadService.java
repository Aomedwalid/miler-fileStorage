package org.uploader.fileuploadtest.services;

import org.springframework.web.multipart.MultipartFile;
import org.uploader.fileuploadtest.dto.request.UploadSessionRequest;
import org.uploader.fileuploadtest.dto.response.upload.ChunkResponse;
import org.uploader.fileuploadtest.dto.response.upload.UploadSessionResponse;
import org.uploader.fileuploadtest.dto.response.upload.UploadStatusResponse;

public interface UploadService {
    UploadSessionResponse createUploadSession(UploadSessionRequest createUploadSession);

    ChunkResponse uploadChunk(MultipartFile chunk, int index, String uploadId);

    UploadStatusResponse getUploadSessionStatus(String uploadId);

    void cleanOrphanedSessions();
}
