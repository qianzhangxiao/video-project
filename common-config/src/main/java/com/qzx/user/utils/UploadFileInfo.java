package com.qzx.user.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileInfo implements Serializable {
    /**
     * 原始文件名称
     */
    private String originalFileName;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 分片大小
     */
    private Long chunkSize;

    /**
     * 文片
     */
    private Integer chunk;

    /**
     * 文件md5
     */
    private String guid;

    /**
     * 临时路径
     */
    private String tmpPath;

    /**
     * 实际路径
     */
    private String realPathName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 音视频时长
     */
    private Long duration;

}
