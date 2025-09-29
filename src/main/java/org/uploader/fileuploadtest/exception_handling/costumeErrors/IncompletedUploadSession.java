package org.uploader.fileuploadtest.exception_handling.costumeErrors;

public class IncompletedUploadSession extends RuntimeException {
    public IncompletedUploadSession(String message) {
        super(message);
    }
}
