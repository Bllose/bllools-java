package com.bllools.util;

import java.io.File;
import java.io.IOException;

/**
 * 遍历文件夹，处理文件
 */
public interface TraversingFolders {

    /**
     * 遍历文件夹，处理文件
     *
     * @param path 文件绝对路径
     * @throws IOException e
     */
    default void traversingFolders(String path) throws IOException {
        File dir = new File(path);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 获取文件绝对路径
                    traversingFolders(file.getAbsolutePath());
                } else {
                    dealWithFile(file);
                }
            }
        }
    }

    /**
     * 处理文件
     *
     * @param file 文件
     */
    void dealWithFile(File file) throws IOException;
}
