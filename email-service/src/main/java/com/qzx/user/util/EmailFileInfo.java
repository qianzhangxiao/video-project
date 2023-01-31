package com.qzx.user.util;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;

@Data
public class EmailFileInfo implements Serializable {

    private static final long serialVersionUID = -5046546545143407095L;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 附件
     */
    private MultipartFile file;

}
