package com.ms.jpdf.web.rest.download;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.apache.commons.io.FileUtils.ONE_MB;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by zcc on 17-3-10.
 */
public class ExistingFile {
    private final FilePointer filePointer;

    public ExistingFile(FilePointer filePointer) {
        this.filePointer=filePointer;
    }

    public ResponseEntity<Resource> toResponse(Optional<String> requestEtagOpT, Optional<LocalDateTime> ifModifiedSinceOpt) {
        final InputStream inputStream = filePointer.open();
        final RateLimiter throttler = RateLimiter.create(ONE_MB*10);
        final ThrottlingInputStream throttlingInputStream = new ThrottlingInputStream(inputStream, throttler);
        InputStreamResource resource=new InputStreamResource(throttlingInputStream);
        return ResponseEntity
                .status(OK)
                .contentLength(filePointer.getSize())
                .lastModified(filePointer.getLastModified()).body(resource);
    }
}
