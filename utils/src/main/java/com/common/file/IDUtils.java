package com.common.file;

import java.util.Random;
import java.util.UUID;

/**
 * id生成策略
 */
public class IDUtils {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getUUID());
        }
        String s = "f5c94968aab195af31a9c77b7fa5a96f45";
        int i = s.length();
        System.out.println(i);
    }

    /**
     * 图片名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }


    public static long getId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //System.out.print(millis);
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = Long.valueOf(str);
        return id;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        String udStr = "";
        Random random = new Random();
        for (int i = 0; i < 34; i++) {
            int end2 = random.nextInt(uuidStr.length());
            udStr += uuidStr.charAt(end2);
        }

        return udStr;
    }

}
