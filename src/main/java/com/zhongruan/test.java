package com.zhongruan;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * @Author: Serena
 * @Date: 2020/7/30 8:23
 * @description:
 */
public class test {
    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        config.setProjectPath("C:\\Users\\1\\Desktop\\code\\3\\news"); // 项目根目录
        config.setProjectName("bookshop"); // 项目名称
        config.setApiVersion("V1.0");       // 声明该API的版本
        config.setDocsPath("C:\\Users\\1\\Desktop\\book-shop\\Docs"); // 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
        Docs.buildHtmlDocs(config); // 执行生成文档
    }

}
