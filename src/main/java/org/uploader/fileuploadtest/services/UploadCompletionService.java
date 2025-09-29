package org.uploader.fileuploadtest.services;

import org.uploader.fileuploadtest.dto.response.upload.UploadCompletedResponse;

import java.io.IOException;

public interface UploadCompletionService {

    UploadCompletedResponse uploadCompleted(String uploadId) throws IOException;

}