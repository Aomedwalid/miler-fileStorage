package org.uploader.fileuploadtest.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.uploader.fileuploadtest.dto.response.upload.UploadCompletedResponse;
import org.uploader.fileuploadtest.entities.UploadSession;
import org.uploader.fileuploadtest.exception_handling.costumeErrors.*;
import org.uploader.fileuploadtest.mapper.uploadProccess.CompletedResponse;
import org.uploader.fileuploadtest.repos.UploadSessionRepo;
import org.uploader.fileuploadtest.services.UploadCompletionService;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class uploadCompletionServiceImpl implements UploadCompletionService {
    private final UploadSessionRepo uploadSessionRepo;
    private final CompletedResponse completedResponse;

    private final RedisTemplate<String , String> redisTemplate;

    @Value("${app.redis.progress-key-prefix}")
    private String PROGRESS_KEY_PREFIX;

    @Value("${app.upload.base-path}")
    private String baseTempPath;
    @Value("${app.upload.final-path}")
    private String baseFinalPath;

    @Override
    public UploadCompletedResponse uploadCompleted(String uploadId){
        String progressKey = PROGRESS_KEY_PREFIX + uploadId;

        Path tempFilePath = Paths.get(baseTempPath , uploadId);

        UploadSession currentSession = getCurrentSession(uploadId);

        uploadCompletionCheck(progressKey , uploadId);

        Path fileDirectory = pathsValidityCheck();

        Path finalFilePath = fileDirectory.resolve(uploadId + "_" + currentSession.getFileName());

        List<Path> chunks = getChunksList(tempFilePath);

        mergingChunks(finalFilePath , chunks);

        Long fileSize = getFileSize(finalFilePath);

        MimeValidation(finalFilePath);

        return completedResponse.createResponse(
                currentSession.getFileName(),
                fileSize.toString(),
                currentSession.getContentType(),
                currentSession.getStatus()
        );
    }


    //--------------------completionMethods--------------------------------

    private void uploadCompletionCheck(String progressKey , String uploadId){

        Path tempDir = Paths.get(baseTempPath , uploadId);

        Map<Object , Object> progress = redisTemplate.opsForHash().entries(progressKey);

        Map<String , String > keys = progress.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                e -> e.getKey().toString(),
                                e-> e.getValue().toString()
                        )
                );

        if( !keys.get("totalChunks").equals(keys.get("receivedChunks"))){
            throw new IncompletedUploadSession("code 6");
        }

        if(!Files.exists(tempDir)){
            throw new InactivatedUploadSession("We could not find your Directory ");
        }
    }

    private Path pathsValidityCheck() {
        Path finalPath = Paths.get(baseFinalPath);

        createFinalPathDirectory(finalPath);

        return finalPath;

    }

    private void mergingChunks(Path finalPath , List<Path> chunkFiles) {

        try(FileChannel outChannel = FileChannel.open(
                finalPath,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
        )){
            for (Path chunkPath : chunkFiles){
                try(FileChannel inChannel = FileChannel.open(chunkPath , StandardOpenOption.READ)){
                    long size = inChannel.size();
                    long position = 0L;

                    while (position < size){
                        position+= inChannel.transferTo(position , size-position , outChannel);
                    }

                }
            }


        } catch (IOException e) {
            throw new AssemblingException("fail to open the channel");
        }


    }

    private Long getFileSize(Path filePath)  {
        try{
            return Files.size(filePath);
        }
        catch (IOException e){
            throw new DirectoryException("cannot return fileSize");
        }
    }


    private void MimeValidation(Path finalFilePath) {

    }


    //-------------------reusable methods for completion----------------------------------



    private void createFinalPathDirectory(Path finalPath){
        if (!Files.exists(finalPath)){
            try {
                Files.createDirectories(finalPath);
                log.info("final files folder created");
            }
            catch (IOException e){
                throw new DirectoryException("cannot create temp directory for your session");
            }
        }
    }

    private List<Path> getChunksList(Path fileDirectory) {

        try (Stream<Path> stream = Files.list(fileDirectory)){
            return stream
                    .filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(p -> Integer.parseInt(p.getFileName().toString())))
                    .collect(Collectors.toList());
        }
        catch (IOException e){
            throw new DirectorySortingException("wrong sorting");
        }

    }

    //-------------------------reusable methods--------------------------

    private UploadSession getCurrentSession(String uploadId){

        return uploadSessionRepo.findByUploadId(uploadId)
                .orElseThrow(() -> new InvalidUploadSession("code : 1") );

    }
}

