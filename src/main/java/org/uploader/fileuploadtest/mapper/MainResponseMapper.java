package org.uploader.fileuploadtest.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.uploader.fileuploadtest.dto.response.main.MainResponse;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class MainResponseMapper {

    public MainResponse success(
            int status,
            String message,
            Object details
    ){
        return MainResponse
                .builder()
                .success(true)
                .status(status)
                .message(message)
                .details(details)
                .errors(Collections.emptyMap())
                .build();
    }

    public MainResponse failed(
            int status,
            String message,
            Object errors
    ){
        return MainResponse
                .builder()
                .success(false)
                .status(status)
                .message(message)
                .details(Collections.emptyMap())
                .errors(errors)
                .build();
    }
}
