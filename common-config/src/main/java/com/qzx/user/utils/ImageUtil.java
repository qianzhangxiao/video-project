package com.qzx.user.utils;


import cn.hutool.core.img.Img;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @description:
 * @author: qc
 * @time: 2020/8/25 21:09
 */
public class ImageUtil {

    private static byte[] imageToByteArray(BufferedInputStream bis){
        byte[] b=null;
        try {
            b=new byte[bis.available()];
            bis.read(b);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtil.closeIo();
        }
        return b;
    }

    public static String getBase64ImgFromClassPath(String path,String fileName){
        BufferedInputStream bis=IOUtil.getBufferInputFromClassPath(path,fileName);
        byte[] b=imageToByteArray(bis);
        return getBase64ImgFromClassPath(b);
    }
    public static String getBase64ImgFromDisk(String path,String fileName){
        BufferedInputStream bis=IOUtil.getBufferInput(path,fileName);
        byte[] b=imageToByteArray(bis);
        return getBase64ImgFromClassPath(b);
    }
    public static String getBase64ImgFromClassPath(String file){
        BufferedInputStream bis=IOUtil.getBufferInputFromClassPath(file);
        byte[] b=imageToByteArray(bis);
        return getBase64ImgFromClassPath(b);
    }
    public static String getBase64ImgFromDisk(String file){
        BufferedInputStream bis=IOUtil.getBufferInput(file);
        byte[] b=imageToByteArray(bis);
        return getBase64ImgFromClassPath(b);
    }
    public static String getBase64ImgFromInputStream(InputStream input){
        BufferedInputStream bis= new BufferedInputStream(input);
        byte[] b=imageToByteArray(bis);
        return getBase64ImgFromClassPath(b);
    }

    private static String getBase64ImgFromClassPath(byte[] b){
        StringBuilder base64String=new StringBuilder("data:image/png;base64,");
        Base64.Encoder encoder = Base64.getEncoder();
//            base64String = encoder.encodeToString(bytes);
        base64String.append(encoder.encodeToString(b));
        return base64String.toString();
    }

    public static void getImgByOutputStreamFromClassPath(String path, String fileName,OutputStream os){
        try {
            BufferedImage io=ImageIO.read(IOUtil.getInputStreamFromClassPath(path,fileName));
            ImgToOutPutStream(io,os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(os);
        }

    }
    public static void getImgByOutputStreamFromDisk(String path, String fileName,OutputStream os){
        try {
            BufferedImage io=ImageIO.read(IOUtil.getInputStream(path,fileName));
            ImgToOutPutStream(io,os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(os);
        }

    }
    public static void getImgByOutputStreamFromClassPath(String file,OutputStream os){
        BufferedImage io= null;
        try {
            io = ImageIO.read(IOUtil.getInputStreamFromClassPath(file));
            ImgToOutPutStream(io,os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(os);
        }
    }
    public static void getImgByOutputStreamFromDisk(String file,OutputStream os){
        BufferedImage io= null;
        try {
            io = ImageIO.read(IOUtil.getInputStream(file));
            ImgToOutPutStream(io,os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIo(os);
        }
    }

    /**
     * 图片流（可能颜色失真）
     * */
    private static void ImgToOutPutStream(BufferedImage img,OutputStream os) throws IOException {
            ImageIO.write(img,"jpg",os);
    }

    /**
     * 利用hutool实现图片回显（解决图片失真问题）
     * */
    public static void ImgToOutPutStreamByHutool(InputStream inputStream,OutputStream outputStream){
        Img.from(inputStream).write(outputStream);
    }

    private static void closeIo(OutputStream os){
            IOUtil.closeIo();
            try {
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
