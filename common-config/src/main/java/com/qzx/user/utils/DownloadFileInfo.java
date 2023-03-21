package com.qzx.user.utils;

import lombok.Data;

import java.io.InputStream;

@Data
public class DownloadFileInfo {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件输入流
     */
    private InputStream inputStream;
}
