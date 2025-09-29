package org.uploader.fileuploadtest.mapper.uploadProccess;

import org.springframework.stereotype.Component;
import org.uploader.fileuploadtest.dto.response.upload.ChunkResponse;

@Component
public class ChunksMapper {
    public ChunkResponse createChunkResponse(
            int index ,
            String fileName
    ){
        return ChunkResponse
                .builder()
                .index(index)
                .fileName(fileName)
                .build();
    }
}
