package org.uploader.fileuploadtest.dto.response.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.uploader.fileuploadtest.entities.UploadSession;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadCompletedResponse {

    private String fileName;

    private String fileSize;

    private String contentType;

    private UploadSession.Status status;

}
