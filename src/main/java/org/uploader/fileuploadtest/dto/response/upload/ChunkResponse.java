package org.uploader.fileuploadtest.dto.response.upload;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChunkResponse {
    private int index;

    private String fileName;

}
