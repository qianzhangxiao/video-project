package com.qzx.user.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 媒体（音视频）工具类
 */
public class MediaUtils {

    private static final List<String> FILE_TYPE;

    static{
        FILE_TYPE = new ArrayList<>();
        String[] videoFileTypeArr = new String[]{
                "mp4" , "3gp" , "avi" , "wmv",
                "mpeg", "mpg" , "mov" , "flv",
                "swf" , "qsv" , "kux" , "rm",
                "ram"
        };
        String[] audioFileTypeArr = new String[]{
                "mp3" , "mp2" , "mp1" , "wav",
                "aif" , "aiff", "au"  , "ra" ,
                "rm"  , "ram" , "mid" , "rmi"
        };
        FILE_TYPE.addAll(Arrays.asList(videoFileTypeArr));
        FILE_TYPE.addAll(Arrays.asList(audioFileTypeArr));
    }


    /**
     * 获取媒体文件时长，单位ms
     */
    public static Long videoDuration(MultipartFile multipartFile){
        if (null!=multipartFile){
            try {
                /**
                 * 临时文件
                 */
                File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                return videoDuration(file,multipartFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }

    public static Long videoDuration(File file, InputStream inputStream){
        if (judgeFileIsMedia(inputStream)){
            BufferedOutputStream outputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                /**
                 * 文件拷贝至临时文件
                 */
                outputStream = FileUtil.getOutputStream(file);
                bufferedInputStream = IoUtil.toBuffered(inputStream);
                IoUtil.copy(bufferedInputStream,outputStream);
                /**
                 * 读取文件为媒体文件
                 */
                MultimediaObject multimediaObject = new MultimediaObject(file);
                /**
                 * 获取媒体对象信息
                 */
                final MultimediaInfo info = multimediaObject.getInfo();
                return info.getDuration();
            } catch (EncoderException e) {
                e.printStackTrace();
            } finally {
                if (null!=outputStream){
                    IoUtil.close(outputStream);
                }
                if (null!=bufferedInputStream){
                    IoUtil.close(bufferedInputStream);
                }
                if (null!=file){
                    file.delete();
                }
            }
        }
        return 0L;
    }

    /**
     * 时间（ms）转为中文
     */
    public static String videoDurationStr(Long duration){
        if (duration==0L){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return simpleDateFormat.format(duration);
    }

    public static String videoDurationStr(MultipartFile file){
        return videoDurationStr(videoDuration(file));
    }
    /**
     * 判断是否是指定媒体文件类型
     */
    public static boolean judgeFileIsMedia(MultipartFile file){
        try {
            return judgeFileIsMedia(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean judgeFileIsMedia(InputStream file){
        final String type = fileType(file);
        if (!ObjectUtils.isEmpty(type)&&FILE_TYPE.contains(type)){
            return true;
        }
        return false;
    }

    public static String fileType(MultipartFile file){
        try {
            return fileType(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fileType(InputStream file){
        return FileTypeUtil.getType(file);
    }

}
