package org.uploader.fileuploadtest.exception_handling.costumeErrors;

public class InactivatedUploadSession extends RuntimeException {
    public InactivatedUploadSession(String message) {
        super(message);
    }
}
