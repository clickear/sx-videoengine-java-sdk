package com.seeshiontech.vesdk;

import com.alibaba.fastjson.JSON;
import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DynamicTest {

    // license expire at 2019-06-20
    private String license = "uOkvS/xbv9Ta37phkqrCCfDlHz26dKA10ztb0jaJg7v3oCoOaZbYp9mZakMuaSPTrGjd1PVNcqMeJw7O27eCPTrMsvJpriX6XSJ5YRBWnCCS3GVLpmVM7EHVogR4enzRPH8iGBYbkcfbiOzqsK3F3WHZa39wuRtcM/tFaFoulVTUD5cpaZ+kP+2RJ6Je2laK6gj30X+UG4wp27XgT9zlaGibWccO2vbT17hz6dLOUqXgpjmRrHLnARvS0XVuQ/zXUYcojDcv/aeylpLuamDR8tS5RL1qgA1cDquYBKx+nddUseQsUoNot9BfE/BDL+fHVzEuGTZHX63gpmoaHfpUwNZoubkLLbanMttD0oRCtd0Y6Uvw5EEByyMd6nXXahCBbxZa14J/2bzXs2QsxFRLgFhldX4EWhjl7KxQkrTJWLI=";


    /**
     * 测试动态模板文字
     *
     * */

    @Test
    public void testDynamicText() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        VideoEngine engine = new VideoEngine();

        String tplFolder = basePath + "/workspace/template/kenbentuya/";
        String outputPath = basePath + "/workspace/output/kenbentuya.mp4";

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
        String[] title = {"自定义相册标题"};
        String[] text1 = {"自定义图片描述"};

        subTexts.add(new DynamicSubTexts("", "title", title));
        subTexts.add(new DynamicSubTexts( basePath + "/workspace/assets/1.jpeg", "dtext", text1));

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
     * 测试动态模板设置附加的文字头像 昵称
     *
     * 附加素材动态模板:
     * dynamic_text
     * */

    @Test
    public void testDynamicTempalte() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        VideoEngine engine = new VideoEngine();

        String tplFolder = basePath + "/workspace/template/dynamic_text2";
        String outputPath = basePath + "/workspace/output/dynamic_text2.mp4";

        String[] paths = {
                basePath + "/workspace/assets/1.jpeg",
                basePath + "/workspace/assets/2.jpeg",
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
        };

        // 给素材绑定关联的子素材
        ArrayList<DynamicSubFiles> subFiles = new ArrayList<>();


        // 按照 ui_key = avatar 精确替换头像
        String[] heads = {
                basePath + "/workspace/headimg.png"
        };
        subFiles.add(new DynamicSubFiles("", "avatar", heads));

        String subFilesJson = JSON.toJSONString(subFiles);
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