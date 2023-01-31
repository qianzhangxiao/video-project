package com.qzx.user.utils;


import cn.hutool.core.img.Img;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * @description: 图片工具类
 * @author: qc
 * @time: 2020/8/25 21:09
 */
public class ImageUtil {

    /**
     * 图片流转为byte数组
     * @param bis
     * @return
     */
    private static byte[] imageToByteArray(BufferedInputStream bis){
        byte[] b=null;
        try {
            b=new byte[bis.available()];
            bis.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtil.closeIo(bis);
        }
        return b;
    }

    /**
     * 图片字节数组转为base64
     * @param b
     * @return
     */
    private static String getBase64ImgFromClassPath(byte[] b){
        StringBuilder base64String=new StringBuilder("data:image/png;base64,");
        Base64.Encoder encoder = Base64.getEncoder();
        base64String.append(encoder.encodeToString(b));
        return base64String.toString();
    }

    /**
     * 获取base64图片
     */
    public static String getBase64ImgFromClassPath(String path,String fileName){
        BufferedInputStream bis=IOUtil.getBufferInputFromClassPath(path,fileName);
        return getBase64ImgFromClassPath(imageToByteArray(bis));
    }
    /**
     * 获取base64图片
     */
    public static String getBase64ImgFromDisk(String path,String fileName){
        BufferedInputStream bis=IOUtil.getBufferInput(path,fileName);
        return getBase64ImgFromClassPath(imageToByteArray(bis));
    }
    /**
     * 获取base64图片
     */
    public static String getBase64ImgFromClassPath(String file){
        BufferedInputStream bis=IOUtil.getBufferInputFromClassPath(file);
        return getBase64ImgFromClassPath(imageToByteArray(bis));
    }
    /**
     * 获取base64图片
     */
    public static String getBase64ImgFromDisk(String file){
        BufferedInputStream bis=IOUtil.getBufferInput(file);
        return getBase64ImgFromClassPath(imageToByteArray(bis));
    }
    /**
     * 获取base64图片
     */
    public static String getBase64ImgFromInputStream(InputStream input){
        BufferedInputStream bis= new BufferedInputStream(input);
        return getBase64ImgFromClassPath(imageToByteArray(bis));
    }

    /**
     * 图片流
     */
    public static void imageFromClassPath(String path, String fileName,OutputStream os){
        InputStream inputStream = null;
        ImageOutputStream imageOutputStream = null;
        try {
            imageOutputStream = getImageOutputStream(os);
            inputStream = IOUtil.getInputStreamFromClassPath(path,fileName);
            BufferedImage io = getBufferedImage(inputStream);
            imgToOutPutStream(io,imageOutputStream);
            imageOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIo(imageOutputStream);
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void streamImageFromClassPath(String path, String fileName,OutputStream os){
        InputStream inputStream = null;
        try {
            inputStream = IOUtil.getInputStreamFromClassPath(path,fileName);
            imgToOutPutStream(inputStream,os);
        } finally {
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void imageFromDisk(String path, String fileName,OutputStream os){
        InputStream inputStream = null;
        ImageOutputStream imageOutputStream = null;
        try {
            imageOutputStream = getImageOutputStream(os);
            inputStream = IOUtil.getInputStream(path,fileName);
            BufferedImage io = getBufferedImage(inputStream);
            imgToOutPutStream(io,imageOutputStream);
            imageOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(imageOutputStream);
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void streamImageFromDisk(String path, String fileName,OutputStream os){
        InputStream inputStream = null;
        try {
            inputStream = IOUtil.getInputStream(path,fileName);
            imgToOutPutStream(inputStream,os);
        } finally {
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void imageFromClassPath(String file,OutputStream os){
        InputStream inputStream = null;
        ImageOutputStream imageOutputStream = null;
        try {
            imageOutputStream = getImageOutputStream(os);
            inputStream = IOUtil.getInputStreamFromClassPath(file);
            BufferedImage io = getBufferedImage(inputStream);
            imgToOutPutStream(io,imageOutputStream);
            imageOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(imageOutputStream);
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void streamImageFromClassPath(String file,OutputStream os){
        InputStream inputStream = null;
        try {
            inputStream = IOUtil.getInputStreamFromClassPath(file);
            imgToOutPutStream(inputStream,os);
        } finally {
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void imageFromDisk(String file,OutputStream os){
        InputStream inputStream = null;
        ImageOutputStream imageOutputStream = null;
        try {
            imageOutputStream = getImageOutputStream(os);
            inputStream = IOUtil.getInputStream(file);
            BufferedImage io = getBufferedImage(inputStream);
            imgToOutPutStream(io,imageOutputStream);
            imageOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(imageOutputStream);
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void imageFromInputStream(InputStream inputStream,OutputStream os){
        ImageOutputStream imageOutputStream = null;
        try {
            imageOutputStream = getImageOutputStream(os);
            BufferedImage io = getBufferedImage(inputStream);
            imgToOutPutStream(io,imageOutputStream);
            imageOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(imageOutputStream);
            closeIo(os);
            closeIo(inputStream);
        }
    }
    /**
     * 图片流
     */
    public static void streamImageFromDisk(String file,OutputStream os){
        InputStream inputStream = null;
        try {
            inputStream = IOUtil.getInputStream(file);
            imgToOutPutStream(inputStream,os);
        } finally {
            closeIo(os);
            closeIo(inputStream);
        }
    }

    /**
     * 图片流（可能颜色失真--修改后失真问题待测试）
     * */
    public static void imgToOutPutStream(BufferedImage img,ImageOutputStream os) throws IOException {
        ImageIO.write(img,"jpg",os);
    }

    /**
     * 图片流（利用hutool实现图片回显，解决图片失真问题）
     * */
    public static void imgToOutPutStream(InputStream inputStream,OutputStream outputStream){
        Img.from(inputStream).write(outputStream);
    }

    public static ImageOutputStream getImageOutputStream(OutputStream outputStream) throws IOException {
        return ImageIO.createImageOutputStream(outputStream);
    }

    public static BufferedImage getBufferedImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    private static void closeIo(Closeable x){
        IOUtil.closeIo(x);
    }
}
