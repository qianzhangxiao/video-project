package com.qzx.user.utils;

import cn.hutool.core.io.IoUtil;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
     *map:
     * key:fileName,文件名称
     * key:inputStream，文件流
     */

    public static void zipDownload(HttpServletResponse response,String fileName, List<DownloadFileInfo> files) throws IOException {
        try{
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            //下载显示的文件名，解决中文名称乱码问题
            String downloadFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName);
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
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


}
