package com.common.file;

import java.io.File;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/9
 */
public class FileUtils {

    /**
     * 删除文件夹 以及子文件
     *
     * @param filePath 要删除的文件的绝对路径
     * @return
     */
    public static boolean delFile(String filePath) {
        File directory = new File(filePath);

        return delFile(directory);
    }

    /**
     * 删除文件夹 以及子文件
     *
     * @param directory 要删除的文件
     * @return
     */
    public static boolean delFile(File directory) {

        System.out.println("删除文件");
        if (!directory.isDirectory()) {
            directory.delete();
            return true;
        } else {
            File[] files = directory.listFiles();

            // 空文件夹
            if (files.length == 0) {
                directory.delete();
                return true;
            }

            // 删除子文件夹和子文件
            for (File file : files) {
                if (file.isDirectory()) {
                    delFile(file);
                } else {
                    file.delete();
                }
            }
            // 删除文件夹本身
            directory.delete();
            return true;
        }

    }
}
