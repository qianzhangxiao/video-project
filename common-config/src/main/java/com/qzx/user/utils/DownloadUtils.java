package com.qzx.user.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description: 文件下载工具类
 * @author: qzx
 * @time: 2020/9/4 14:03
 */

public class DownloadUtils {

    /**
     * 文件下载，可以导出txt、json等文件
     * @param fileName 文件名称
     * @param bytes 导出的字节数组
     * @param response 响应流
     * @throws IOException 异常
     * 案例：
     *      DownloadUtils.downloadFile("test.txt","hello world \r\n 新的一天，新的希望！".getBytes(StandardCharsets.UTF_8),response);
     */
    public static void downloadFile(String fileName,byte[] bytes,HttpServletResponse response) throws IOException {
        BufferedOutputStream bos = null;
        try {
            setResponse(response,fileName);
            bos = IoUtil.toBuffered(response.getOutputStream());
            bos.write(bytes);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("文件下载异常");
        } finally {
            IoUtil.close(bos);
        }
    }

    /**
     *
     * @param fileName 下载的文件名称（浏览器展示的名称）
     * @param inputStream：文件输入流
     * @param response
     */
    public static void downloadFile(String fileName,InputStream inputStream, HttpServletResponse response) throws IOException {
        OutputStream os;
        BufferedOutputStream bos =null;
        try{
            setResponse(response,fileName);
            os=response.getOutputStream();
            bos=new BufferedOutputStream(os);
            //创建缓冲区
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("文件下载异常");
        } finally {
            IoUtil.close(bos);
            IoUtil.close(inputStream);
        }
    }

    /**
     *
     * @param fileName 下载的文件名称（浏览器展示的名称）
     * @param inputStream：文件输入流
     * @param response
     */
    public static void downloadFileByTool(String fileName,InputStream inputStream, HttpServletResponse response) throws IOException {
        BufferedOutputStream bos =null;
        try{
            setResponse(response,fileName);
            bos = IoUtil.toBuffered(response.getOutputStream());
            IoUtil.copy(inputStream,bos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("文件下载异常");
        } finally {
            IoUtil.close(bos);
            IoUtil.close(inputStream);
        }
    }

    /**
     * 下载时响应流设置
     * @param response 响应流
     * @param fileName 下载的文件名
     * @throws UnsupportedEncodingException
     */
    public static void setResponse(HttpServletResponse response,String fileName) throws UnsupportedEncodingException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.addHeader("Content-type", "application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
    }

    /**
     *
     *压缩文件并下载
     * fileName:压缩文件名称,后缀为.zip
     * DownloadFileInfo:
     *  fileName,文件名称
     *  InputStream，文件流
     */
    public static void zipDownload(HttpServletResponse response,String fileName, List<DownloadFileInfo> files) throws IOException {
        setResponse(response,fileName);
        zipDownload(response.getOutputStream(),files);
    }
    /**
     *
     *压缩文件并下载到指定目录
     * DownloadFileInfo:
     *  fileName,文件名称
     *  InputStream，文件流
     */
    public static void zipDownload(OutputStream outputStream, List<DownloadFileInfo> files) throws IOException {
        try{
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            for (DownloadFileInfo file : files) {
                zipOutputStream.putNextEntry(new ZipEntry(file.getFileName()));
                IoUtil.copy(file.getInputStream(),zipOutputStream);
                zipOutputStream.flush();
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
            throw new IOException("压缩文件下载异常");
        }
    }
    /**
     * 压缩指定目录
     */
    public static void zipDownload(Path sourceFolderPath, Path zipPath) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
        zipCompress(sourceFolderPath,zos);
    }
    /**
     * 压缩指定目录
     */
    public static void zipDownload(Path sourceFolderPath, File zipFile) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(IoUtil.toBuffered(FileUtil.getOutputStream(zipFile)));
        zipCompress(sourceFolderPath,zos);
    }
    /**
     * 压缩指定目录
     */
    public static void zipDownload(Path sourceFolderPath, OutputStream outputStream) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(IoUtil.toBuffered(outputStream));
        zipCompress(sourceFolderPath,zos);
    }
    /**
     * 压缩指定目录
     */
    public static void zipDownload(Path sourceFolderPath, HttpServletResponse response,String fileName) throws IOException {
        setResponse(response,fileName);
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
        zipCompress(sourceFolderPath,zos);
    }

    /**
     * 压缩指定目录
     */
    private static void zipCompress(Path sourceFolderPath,ZipOutputStream zos) throws IOException {
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                IoUtil.copy(IoUtil.toBuffered(FileUtil.getInputStream(file.toFile())),zos);
                zos.flush();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.closeEntry();
        zos.close();
    }


}
