package com.qzx.user.video.service;

import com.qzx.user.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    ResponseResult<?> uploadFile(MultipartFile[] files);
}
