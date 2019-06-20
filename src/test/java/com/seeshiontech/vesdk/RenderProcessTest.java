package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 渲染测试
 *
 * @author  dongyado
 * */


public class RenderProcessTest {

    // license expire at 2019-06-20
    private String license = "uOkvS/xbv9Ta37phkqrCCfDlHz26dKA10ztb0jaJg7v3oCoOaZbYp9mZakMuaSPTrGjd1PVNcqMeJw7O27eCPTrMsvJpriX6XSJ5YRBWnCCS3GVLpmVM7EHVogR4enzRPH8iGBYbkcfbiOzqsK3F3WHZa39wuRtcM/tFaFoulVTUD5cpaZ+kP+2RJ6Je2laK6gj30X+UG4wp27XgT9zlaGibWccO2vbT17hz6dLOUqXgpjmRrHLnARvS0XVuQ/zXUYcojDcv/aeylpLuamDR8tS5RL1qgA1cDquYBKx+nddUseQsUoNot9BfE/BDL+fHVzEuGTZHX63gpmoaHfpUwNZoubkLLbanMttD0oRCtd0Y6Uvw5EEByyMd6nXXahCBbxZa14J/2bzXs2QsxFRLgFhldX4EWhjl7KxQkrTJWLI=";


    /**
     *  渲染测试
     *
     * */
    @Test
    public void testRender() {

        File f = new File("");
        String basePath = f.getAbsolutePath();

        /**
         * 普通模板测试
         *
         * Calorie
         * Screen
         *
         * */


/*        String tplFolder = basePath + "/workspace/template/Calorie";
        String outputPath = basePath + "/workspace/output/calorie.mp4";

        String[] paths = {
                basePath + "/workspace/544x960.test0.png"
        };*/

/*        String tplFolder = basePath + "/workspace/template/Screen";
        String outputPath = basePath + "/workspace/output/screen.mp4";

        String[] paths = {
                basePath + "/workspace/544x960.test0.png"
        };*/


        /**
         * 动态模板测试
         * ChineseStyle
         * Simple
         * dynamic_text
         * kenbentuya
         * */

        String tplFolder = basePath + "/workspace/template/kenbentuya";
        String outputPath = basePath + "/workspace/output/kenbentuya.mp4";

        String[] paths = {
                basePath + "/workspace/544x960.test0.png",
                basePath + "/workspace/assets/1.jpeg",
                basePath + "/workspace/assets/2.jpeg",
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
                basePath + "/workspace/assets/6.jpeg"
        };


        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);

        try {
            boolean ret = task.render();
            String info = task.getTaskRenderedInfo();
            System.out.println(info);
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
     * 测试 音乐, 水印
     *
     *
     * */
    @Test
    public void testOtherFeatures() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        VideoEngine engine = new VideoEngine();

        String tplFolder = basePath + "/workspace/template/dynamic_text";
        String outputPath = basePath + "/workspace/output/dynamic.mp4";

        String[] paths = {
                basePath + "/workspace/assets/1.jpeg",
                basePath + "/workspace/assets/2.jpeg",
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
        };

        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);


        // 设置音乐
        String musicPath = basePath + "/workspace/music.mp3";
        task.setMusicPath(musicPath, true);
//        task.setMusicLoop(true);
        task.setMusicFadeoutDuration(5);
        task.setMusicVolume(1.0f);


        // 添加水印
        List<Watermark> list = new ArrayList<>();
        Watermark mark = new Watermark();
        List<String> watermarkPaths = new ArrayList<>();
        watermarkPaths.add(basePath + "/workspace/watermark.png");
        mark.setPaths(watermarkPaths);

        Watermark mark2 = new Watermark();
        List<String> watermarkPaths2 = new ArrayList<>();
        watermarkPaths2.add( basePath + "/workspace/watermark.png");
        mark2.setPaths(watermarkPaths2);
        mark2.setPosX(400);

        list.add(mark);
        list.add(mark2);

        task.setWatermarkList(list);

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
     * 测试脚本
     * */

    @Test
    public void testScript() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        String tplFolder = basePath + "/workspace/template/dynamic_text";
        String outputPath = basePath + "/workspace/output/dynamic.mp4";

        String[] paths = {
                basePath + "/workspace/assets/1.jpeg",
                basePath + "/workspace/assets/2.jpeg",
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
        };


        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);

        // 设置脚本
        String mainLuaFile = basePath + "/workspace/main.lua";
        task.setScriptMainFile(mainLuaFile);

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