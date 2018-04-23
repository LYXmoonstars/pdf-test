package com.ms.jpdf.web.rest.download;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by zcc on 17-3-10.
 */
@Component
public class FileSystemStorage implements FileStorage {
    @Override
    public Optional<FilePointer> findFileByName(String fileName) {
        byte[] decoded = Base64.decode(fileName);
        String name = new String(decoded);
        File file= Paths.get("../attachments/"+name).toFile();
        final FileSystemPointer pointer = new FileSystemPointer(file);
        return Optional.of(pointer);
    }
}
