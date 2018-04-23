package com.ms.jpdf.web.rest.download;

import java.util.Optional;

/**
 * Created by zcc on 17-3-10.
 */
public interface FileStorage {
    Optional<FilePointer> findFileByName(String fileName);
}
