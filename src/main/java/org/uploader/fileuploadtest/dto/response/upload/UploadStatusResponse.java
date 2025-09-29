package org.uploader.fileuploadtest.dto.response.upload;

import lombok.*;
import org.uploader.fileuploadtest.entities.UploadSession;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadStatusResponse implements Serializable {

    private String fileName;

    private UploadSession.Status status;

    private Instant CreatedAt;

    private Long totalChunks;

    private Long receivedChunks;

    private Set<Long> chunkIndices;

}
