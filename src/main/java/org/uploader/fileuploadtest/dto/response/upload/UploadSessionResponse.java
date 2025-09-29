package org.uploader.fileuploadtest.dto.response.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.uploader.fileuploadtest.entities.UploadSession;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadSessionResponse {
    private String uploadId;

    private int totalChunks;

    private UploadSession.Status status;
}
