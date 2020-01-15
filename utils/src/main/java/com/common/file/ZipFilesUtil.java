package com.common.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/9
 */
public class ZipFilesUtil {

    public static void compress(File f, String baseDir, ZipOutputStream zos,boolean isDel) {
        if (!f.exists()) {
            System.out.println("待压缩的文件目录或文件" + f.getName() + "不存在");
            return;
        }


        File[] fs = f.listFiles();
        BufferedInputStream bis = null;
        //ZipOutputStream zos = null;
        byte[] bufs = new byte[1024 * 10];
        FileInputStream fis = null;


        try {
            //zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < fs.length; i++) {
                String fName = fs[i].getName();
                System.out.println("压缩：" + baseDir + fName);
                if (fs[i].isFile()) {
                    ZipEntry zipEntry = new ZipEntry(baseDir + fName);//
                    zos.putNextEntry(zipEntry);
                    //读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(fs[i]);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                    //如果需要删除源文件，则需要执行下面2句
                    if(isDel){
                        fis.close();
                        fs[i].delete();
                    }

                } else if (fs[i].isDirectory()) {
                    compress(fs[i], baseDir + fName + "/", zos,isDel);
                }
            }//end for
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bis)
                    bis.close();
                //if(null!=zos)
                //zos.close();
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static String getZipFile(String sourceFilePath, boolean isDel) {


        File sourceDir = new File(sourceFilePath);
        File zipFile = new File(sourceFilePath + ".zip");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            String baseDir = "";
            compress(sourceDir, baseDir, zos,isDel);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (zos != null)
                try {
                    zos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        //是否删除原文件
        if (isDel) {
            FileUtils.delFile(sourceDir);
        }
        //返回压缩文件路径
        return zipFile.getPath();
    }

    public static void main(String[] args) {
        String sourceFilePath = "D:\\JAVA\\file\\generatorFile\\157855980734413";
//        String zipFile = getZipFile(sourceFilePath, true);
        boolean b = FileUtils.delFile(sourceFilePath);

//        System.out.println(zipFile);
    }

}

