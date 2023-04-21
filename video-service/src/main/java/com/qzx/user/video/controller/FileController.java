package com.qzx.user.video.controller;

import com.qzx.user.utils.ResponseResult;
import com.qzx.user.video.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;

    /**
     * 文件上传
     */
    @PostMapping("/uploadFile")
    public ResponseResult<?> uploadFile(@RequestParam("/files")MultipartFile[] files){
        return fileService.uploadFile(files);
    }

}
