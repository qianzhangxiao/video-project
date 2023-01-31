package com.qzx.user.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @description: 文件上传工具类
 * @author: qc
 * @time: 2020/9/7 19:21
 */
@Slf4j
public class UploadUtils {


    /**
     * 校验分片是否存在，分片大小是否正常
     */
    public static boolean checkChunk(UploadFileInfo fileInfo) {
        String filePath = fileInfo.getTmpPath()+File.separator+fileInfo.getGuid();
        File file = new File(filePath,String.valueOf(fileInfo.getChunk()));
        return file.exists() && file.length() == fileInfo.getChunkSize();
    }

    /**
     * 切片文件上传到临时路径
     */
    public static void uploadTmpFile(MultipartFile file, UploadFileInfo fileInfo) throws IOException {
        String filePath = fileInfo.getTmpPath() + File.separator + fileInfo.getGuid();
        File tmpFile = new File(filePath);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        File dirFile = fileInfo.getChunk() == null ? new File(filePath, "0") : new File(filePath, String.valueOf(fileInfo.getChunk()));
        try (
                RandomAccessFile accessFile = new RandomAccessFile(dirFile, "rw");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
        ) {
            accessFile.seek(accessFile.length());
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = bufferedInputStream.read(buf)) != -1) {
                accessFile.write(buf, 0, length);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("文件未找到");
        } catch (IOException e) {
            throw new IOException("文件处理异常");
        }
    }

    /**
     * 合并切片文件
     */
    public static UploadFileInfo combineFile(UploadFileInfo fileInfo) throws IOException {
        Long start = System.currentTimeMillis();
        String filePath = fileInfo.getTmpPath() + File.separator + fileInfo.getGuid();
        File tmpFile = new File(filePath);
        File realPath = new File(fileInfo.getRealPath());
        //真实上传路径
        if (!realPath.exists()) {
            realPath.mkdirs();
        }
        File realFile = new File(realPath, fileInfo.getFileName());
        // 文件追加写入
        FileChannel fcin;
        FileChannel fcout;
        try (
                FileOutputStream outputStream = new FileOutputStream(realFile, true)
        ) {
            log.info("合并文件——开始 [ 文件名称：" + fileInfo.getFileName() + " ，MD5值：" + fileInfo.getGuid() + " ]");
            outputStream.getChannel();
            fcout = outputStream.getChannel();
            if (tmpFile.exists()) {
                final File[] files = tmpFile.listFiles();
                //按名称排序
                final List<File> fileList = Arrays.asList(files).stream().sorted((o1, o2) -> Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName())).collect(Collectors.toList());
                //每次读取10MB大小，字节读取
                //设置缓冲区为20MB，缓冲区大小需要比分片大小要大
                ByteBuffer buffer = ByteBuffer.allocate(20 * 1024 * 1024);
                for (int i = 0; i < fileList.size(); i++) {
                    FileInputStream inputStream = new FileInputStream(fileList.get(i));
                    fcin = inputStream.getChannel();
                    if (fcin.read(buffer) != -1) {
                        // 切换为读模式
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            fcout.write(buffer);
                        }
                    }
                    buffer.clear();
                    inputStream.close();
                    fileList.get(i).delete(); //删除临时文件
                }
            }
            //删除临时目录
            if (tmpFile.isDirectory() && tmpFile.exists()) {
                System.gc(); // 回收资源
                tmpFile.delete();
            }
            log.info("合并文件——结束 [ 文件名称：" + fileInfo.getFileName() + " ，MD5值：" + fileInfo.getGuid() + " ]" + "耗时：" + (System.currentTimeMillis() - start) + "ms");
            fileInfo.setFileSize(realFile.length());
            return fileInfo;
        } catch (IOException e) {
            throw new IOException("文件合并异常");
        }
    }

    /**
     * 单文件上传
     *
     * @param realPath：文件路径，需要
     * @param multipartFile
     * @return
     */
    public static Map<String, Object> uploadFile(String realPath, MultipartFile multipartFile) {
        Map<String, Object> map = new HashMap<>();
        try {
            String filePath = mkDir(realPath);
//            String filePath=mkDirOnClass(realPath);
            // 获取文件路径及名称
            String fileNamePath = filePath.substring(filePath.indexOf(realPath));
            // 获取文件原始名称
            String oldFileName = multipartFile.getOriginalFilename();
            // 获取文件类型
            String fileType = multipartFile.getContentType();
            // 获取文件大小
            Long fileSize = multipartFile.getSize();
            // 获取文件后缀
            String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
            // 真实文件名
            String realFileName = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date()) + "_" + UUID.randomUUID().toString().replace("-", "") + suffix;
            multipartFile.transferTo(new File(filePath, realFileName));
            map.put("oldFileName", oldFileName);
            map.put("fileSize", fileSize);
            map.put("suffix", suffix);
            map.put("fileType", fileType);
            map.put("filePath", filePath);
            map.put("filePathName", fileNamePath + "/" + realFileName);
            map.put("realFileName", realFileName);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", "文件上传异常");
        }
        return map;
    }

    /**
     * 多文件上传
     *
     * @param request
     * @param pathName：文件map，key:前台file名称，value，文件存储路径
     * @return
     */
    public static Map<String, Map<String, Object>> uploadFiles(HttpServletRequest request, Map<String, Object> pathName) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        Map<String, Map<String, Object>> map = new HashMap<>();
        if (!commonsMultipartResolver.isMultipart(request)) {
            throw new RuntimeException("请求不包含文件，请仔细核对");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multiRequest.getFileNames();
        while (iter.hasNext()) {
            MultipartFile file = multiRequest.getFile(iter.next());
            pathName.forEach((k, v) -> {
                //file.getName()  获取前台file的name值，map的key需要和name值对应
                if (k.equals(file.getName())) {
//                    System.out.println(k+","+file.getName());
                    Map<String, Object> uploadFile = uploadFile(v.toString(), file);
                    map.put(k, uploadFile);
                }
            });
        }
        return map;
    }

    // 服务器项目上传至真实路径
    private static String mkDir(String realPath) {
        StringBuilder sb = new StringBuilder(realPath);
        String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (!realPath.endsWith("/")) {
            sb.append("/");
        }
        sb.append(datePath);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    // 本地项目使用ClassPathResource获取target目录
    private static String mkDirOnClass(String realPath) throws IOException {
        if (ObjectUtils.isEmpty(realPath)) {
            return "文件路径不能为空";
        }
        String[] array = handleString(realPath);
        String parentPath = array[0];

        String path = new ClassPathResource(parentPath).getURL().getPath();
        String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        realPath=ResourceUtils.getURL(path+"/"+datePath).getPath();
        String realFilepath;
        if (array.length == 2) {
            realFilepath = path + array[1] + "/" + datePath;
        } else {
            realFilepath = path + "/" + datePath;
        }
        File file = new File(realFilepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return realFilepath;
    }

    private static String[] handleString(String str) {
        String[] strArr = null;
        if (str.startsWith("/")) {
            str = str.substring(str.indexOf("/") + 1);
        }
        String[] split = str.split("/");
        String splitFirst = split[0];
        if (split.length == 1) {
            strArr = new String[1];
            strArr[0] = splitFirst;
            return strArr;
        }
        strArr = new String[2];
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < split.length; i++) {
            sb.append("/");
            sb.append(split[i]);
        }
        strArr[0] = splitFirst;
        strArr[1] = sb.toString();
        return strArr;
    }
}
