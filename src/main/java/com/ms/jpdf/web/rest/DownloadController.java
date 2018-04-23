package com.ms.jpdf.web.rest;


import com.ms.jpdf.web.rest.download.ExistingFile;
import com.ms.jpdf.web.rest.download.FileStorage;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.IF_MODIFIED_SINCE;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;


/**
 * Created by zcc on 17-3-10.
 */
@RestController
@RequestMapping("/api/download")
public class DownloadController {
    private final FileStorage storage;

    public DownloadController(FileStorage storage) {
        this.storage = storage;
    }

    @RequestMapping(value = "/{filename}", method = {GET, HEAD}, produces = "application/octet-stream")
    public ResponseEntity<Resource> downloadByName(
        HttpMethod method,
        @PathVariable String filename,
        @RequestHeader(IF_NONE_MATCH) Optional<String> requestEtagOpT,
        @RequestHeader(IF_MODIFIED_SINCE) Optional<LocalDateTime> ifModifiedSinceOpt
    ) {
        System.out.println("开始");
        ResponseEntity<Resource> response = findExistingFileByName(method, filename)
            .map(file -> file.toResponse(requestEtagOpT, ifModifiedSinceOpt))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        System.out.println(response);
        return response;
    }

    private Optional<ExistingFile> findExistingFileByName(HttpMethod method, @PathVariable String fileName) {
        return storage.findFileByName(fileName).map(filePointer -> new ExistingFile(filePointer));
    }
}
