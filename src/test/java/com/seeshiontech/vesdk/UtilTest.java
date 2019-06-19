package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.utils.TemplateUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * 工具类测试
 * */


public class UtilTest {

    /**
     * 根据模板和素材数量获取视频时长
     * */
    @Test
    public void testDynamicDuration() {
        File f = new File("");
        String basePath = f.getAbsolutePath();
        String configPath = basePath + "/workspace/template/dynamic_text/config.json";

        StringBuilder sb = readFileByLines(configPath);

        System.out.println(sb);

        TemplateUtil util = new TemplateUtil(sb.toString());

        float duration = util.getDurationWithFileNum(5);
        System.out.println(duration);

        return;
    }

    public static StringBuilder readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return sb;
    }
}