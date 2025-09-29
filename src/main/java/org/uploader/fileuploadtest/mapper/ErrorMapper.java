package org.uploader.fileuploadtest.mapper;

import org.springframework.stereotype.Component;
import org.uploader.fileuploadtest.dto.response.ErrorResponse;

import java.util.Map;

@Component
public class ErrorMapper {
    public ErrorResponse createNormalError(
            String path,
            Map<String , String> details
    ){
        return ErrorResponse.builder()
                .path(path)
                .details(details)
                .build();
    }
}
