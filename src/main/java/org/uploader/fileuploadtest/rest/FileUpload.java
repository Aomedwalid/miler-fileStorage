package org.uploader.fileuploadtest.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.uploader.fileuploadtest.dto.request.UploadSessionRequest;
import org.uploader.fileuploadtest.dto.response.upload.ChunkResponse;
import org.uploader.fileuploadtest.dto.response.upload.UploadCompletedResponse;
import org.uploader.fileuploadtest.dto.response.upload.UploadSessionResponse;
import org.uploader.fileuploadtest.dto.response.upload.UploadStatusResponse;
import org.uploader.fileuploadtest.dto.response.main.MainResponse;
import org.uploader.fileuploadtest.mapper.MainResponseMapper;
import org.uploader.fileuploadtest.services.UploadCompletionService;
import org.uploader.fileuploadtest.services.impl.UploadServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class FileUpload {
    private final UploadServiceImpl uploadService;
    private final MainResponseMapper mainResponseMapper;
    private final UploadCompletionService uploadCompletionService;

    @PostMapping("/start")
    public ResponseEntity<MainResponse> startUploadSession(@RequestBody @Valid UploadSessionRequest uploadSessionRequest){
        UploadSessionResponse uploadSessionResponse = uploadService.createUploadSession(uploadSessionRequest);

        MainResponse response = mainResponseMapper.success(
                HttpStatus.CREATED.value(),
                "upload Session Started successfully",
                uploadSessionResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{uploadId}/chunk/{index}")
    public ResponseEntity<MainResponse> uploadChunk(
            @PathVariable("uploadId") String uploadId,
            @PathVariable("index") int index,
            @RequestParam("file" )MultipartFile chunk
            ){

        ChunkResponse chunkResponse = uploadService.uploadChunk(chunk , index , uploadId);

        MainResponse response = mainResponseMapper.success(
                HttpStatus.OK.value(),
                "chunk [ " + index + " ] uploaded successfully",
                chunkResponse
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uploadId}/status")
    public ResponseEntity<MainResponse> uploadSessionStatus(
            @PathVariable String uploadId
    ){

        UploadStatusResponse uploadStatusResponse = uploadService.getUploadSessionStatus(uploadId);

        MainResponse response = mainResponseMapper.success(
                HttpStatus.OK.value(),
                "status fetched successfully",
                uploadStatusResponse
        );

        return ResponseEntity.ok(response);

    }

    @PostMapping("/{uploadId}/complete")
    public ResponseEntity<MainResponse> completeSession(
            @PathVariable String uploadId
    ) throws IOException {

        UploadCompletedResponse uploadCompletedResponse = uploadCompletionService.uploadCompleted(uploadId);

        MainResponse response = mainResponseMapper.success(
                HttpStatus.OK.value(),
                "status fetched successfully",
                uploadCompletedResponse
        );

        return ResponseEntity.ok(response);

    }
}
