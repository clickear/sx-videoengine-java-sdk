package com.seeshiontech.vesdk;

import com.alibaba.fastjson.JSON;
import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * 动态模板高级素材替换
 *
 * @author dongyado
 * */

public class DynamicSubAssetTest {

    // license expire at 2019-08-20
    private String license = "uOkvS/xbv9Ta37phkqrCCfDlHz26dKA10ztb0jaJg7v3oCoOaZbYp9mZakMuaSPTrGjd1PVNcqMeJw7O27eCPTrMsvJpriX6XSJ5YRBWnCCS3GVLpmVM7EHVogR4enzR/uzGfrP5ptm43dUV4Tw+ZGHZa39wuRtcM/tFaFoulVTUD5cpaZ+kP+2RJ6Je2laK6gj30X+UG4wp27XgT9zlaGibWccO2vbT17hz6dLOUqXgpjmRrHLnARvS0XVuQ/zXUYcojDcv/aeylpLuamDR8tS5RL1qgA1cDquYBKx+ndcoEGbrnr5pHSs8JkGv0p35VzEuGTZHX63gpmoaHfpUwNZoubkLLbanMttD0oRCtd0Y6Uvw5EEByyMd6nXXahCBq0uhEEKtq2ZckKcaG/1/LVhldX4EWhjl7KxQkrTJWLI=";

    /**
     * 动态模板高级文字素材替换
     * */

    @Test
    public void testDynamicSubText() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        String tplFolder = basePath + "/workspace/template/kenbentuya/";
        String outputPath = basePath + "/workspace/output/kenbentuya_text.mp4";

        String[] paths = {
                basePath + "/workspace/assets/1.jpeg",
                basePath + "/workspace/assets/2.jpeg",
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
        };

        // 给素材绑定关联的文字
        ArrayList<DynamicSubTexts> subTexts = new ArrayList<>();


        // 按照 ui_key = avatar 精确替换头像
        String[] title = {"标题"};
        String[] text1 = {"描述1"};
        String[] text2 = {"描述2"};
        String[] text3 = {"描述3"};
        String[] text4 = {"描述4"};
        String[] text5 = {"描述5"};

        subTexts.add(new DynamicSubTexts("", "title", title));
        subTexts.add(new DynamicSubTexts( basePath + "/workspace/assets/1.jpeg", "dtext", text1));
        subTexts.add(new DynamicSubTexts( basePath + "/workspace/assets/2.jpeg", "dtext", text2));
        subTexts.add(new DynamicSubTexts( basePath + "/workspace/assets/3.jpeg", "dtext", text3));
        subTexts.add(new DynamicSubTexts( basePath + "/workspace/assets/4.jpeg", "dtext", text4));
        subTexts.add(new DynamicSubTexts( basePath + "/workspace/assets/5.jpeg", "dtext", text5));

        String subTextJson = JSON.toJSONString(subTexts);

        System.out.println(subTextJson);

        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);

        // 设置替换素材
        task.setAssetPaths(paths);

        // 设置文字
        task.setDynamicSubTexts(subTextJson);

        // 设置文字绘制工具目录, 必须设置, 以 / 结尾
        task.setTextPainterDir("/home/slayer/workspace/ve-textpainter/textpainter/");

        // 设置素材保存目录, 必须设置, 以 / 结尾
        task.setAssetDir(basePath + "/workspace/assets/");


        try {
            boolean ret = task.render();
        } catch (InvalidLicenseException e) {
            e.printStackTrace();
        } catch (RenderException e) {
            e.printStackTrace();
        } catch (NotSupportedTemplateException e) {
            e.printStackTrace();
        } finally {
            task.destroy();
        }
    }


    /**
     * 动态模板高级图片素材替换
     *
     * */
    @Test
    public void testDynamicSubImg() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        VideoEngine engine = new VideoEngine();

        String tplFolder = basePath + "/workspace/template/kenbentuya/";
        String outputPath = basePath + "/workspace/output/kenbentuya_img.mp4";

        String[] paths = {
                basePath + "/workspace/assets/1.jpeg",
                basePath + "/workspace/assets/2.jpeg",
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
        };

        // 给素材绑定关联的子素材
        ArrayList<DynamicSubFiles> subFiles = new ArrayList<>();
        String[] title = {
                basePath + "/workspace/assets/235_41_text8.png"
        };
        String[] subImgs = {
                basePath + "/workspace/assets/235_41_text1.png"
        };
        String[] subImgs2 = {
                basePath + "/workspace/assets/235_41_text2.png",
                basePath + "/workspace/assets/235_41_text3.png",

        };
        String[] subImgs3 = {
                basePath + "/workspace/assets/235_41_text4.png"

        };
        String[] subImgs4 = {
                basePath + "/workspace/assets/235_41_text5.png"
        };
        String[] subImgs5 = {
                basePath + "/workspace/assets/235_41_text6.png"
        };

        subFiles.add(new DynamicSubFiles("", "title", title));
        subFiles.add(new DynamicSubFiles(basePath + "/workspace/assets/1.jpeg", "dtext", subImgs));
        subFiles.add(new DynamicSubFiles(basePath + "/workspace/assets/2.jpeg", "dtext", subImgs2));
        subFiles.add(new DynamicSubFiles(basePath + "/workspace/assets/3.jpeg", "dtext", subImgs3));
        subFiles.add(new DynamicSubFiles(basePath + "/workspace/assets/4.jpeg", "dtext", subImgs4));
        subFiles.add(new DynamicSubFiles(basePath + "/workspace/assets/5.jpeg", "dtext", subImgs5));


        String subFilesJson = JSON.toJSONString(subFiles);

        System.out.println(JSON.toJSONString(paths));
        System.out.println(subFilesJson);


        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);
        task.setDynamicSubFiles(subFilesJson);

        try {
            boolean ret = task.render();
        } catch (InvalidLicenseException e) {
            e.printStackTrace();
        } catch (RenderException e) {
            e.printStackTrace();
        } catch (NotSupportedTemplateException e) {
            e.printStackTrace();
        } finally {
            task.destroy();
        }
    }

}