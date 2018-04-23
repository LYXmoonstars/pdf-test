package com.ms.jpdf.web.rest.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * Created by zcc on 17-3-10.
 */
public class FileSystemPointer implements FilePointer {
    private File file;

    public FileSystemPointer(File file) {
        this.file = file;
    }

    @Override
    public InputStream open() {
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public long getLastModified() {
        return file.lastModified();
    }


}
