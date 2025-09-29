package org.uploader.fileuploadtest.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadSessionRequest {

    @NotBlank(message = "File name cannot be empty")
    @Size(max = 255, message = "File name must not exceed 255 characters")
    private String fileName;

    @Min(value = 1, message = "Total chunks must be at least 1")
    @Max(value = 100, message = "Total chunks must not exceed 100")
    private int totalChunks;

    @NotBlank(message = "Content type cannot be empty")
    private String contentType;

}
