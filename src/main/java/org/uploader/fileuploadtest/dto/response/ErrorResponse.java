package org.uploader.fileuploadtest.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Builder
@Data
public class ErrorResponse {
    @Builder.Default
    private String timestamp = Instant.now().toString();

    private String path;

    private Map<String, String> details;
}
