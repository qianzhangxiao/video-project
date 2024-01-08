package com.qzx.user.video.service.impl;

import com.qzx.user.entity.VoFileEntity;
import com.qzx.user.exception.BusinessException;
import com.qzx.user.service.IVoFileService;
import com.qzx.user.utils.ResponseResult;
import com.qzx.user.utils.UploadFileInfo;
import com.qzx.user.utils.UploadUtils;
import com.qzx.user.video.config.SelfConfigProperties;
import com.qzx.user.video.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IFileServiceImpl implements IFileService {

    private final IVoFileService voFileService;

    private final SelfConfigProperties selfConfigProperties;

    @Override
    public ResponseResult<?> uploadFile(MultipartFile[] files) {
        try {
            final List<UploadFileInfo> uploadFileInfos = UploadUtils.uploadFiles(selfConfigProperties.getFilePath(), files, true);
            final List<VoFileEntity> fileList = uploadFileInfos.stream().map(res -> {
                VoFileEntity file = new VoFileEntity();
                file.setFileName(res.getFileName());
                file.setFileSize(res.getFileSize());
                file.setFilePath(selfConfigProperties.getFilePath());
                file.setFilePathName(res.getRealPathName());
                file.setFileStatus((byte) 1);
                file.setFileType(res.getFileType());
                file.setFileOriginalName(res.getOriginalFileName());
                file.setUploadTime(new Date());
                file.setFileSuffix(res.getSuffix());
                file.setFileGuid(res.getGuid());
                file.setFileDuration(res.getDuration());
                return file;
            }).collect(Collectors.toList());
            return voFileService.saveBatch(fileList)?ResponseResult.success(fileList):ResponseResult.error(500,"文件上传异常");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件上传失败");
        }
    }
}
