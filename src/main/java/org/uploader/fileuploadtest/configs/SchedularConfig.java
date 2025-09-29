package org.uploader.fileuploadtest.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.uploader.fileuploadtest.services.impl.UploadServiceImpl;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class SchedularConfig {
    private final UploadServiceImpl uploadService;

    //4 hours sweeper
    @Scheduled(fixedRate = 1000 * 60 * 60 * 4)
    public void cleanAbandonedSessions(){

        log.info("Running the cleaner");
        uploadService.cleanOrphanedSessions();

    }

}
