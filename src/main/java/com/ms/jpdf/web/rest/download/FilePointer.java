package com.ms.jpdf.web.rest.download;


import java.io.InputStream;

/**
 * Created by zcc on 17-3-10.
 */
public interface FilePointer {
    InputStream open();
    long getSize();
    long getLastModified();
}
