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

    // license expire at 2019-07-20
    private String license = "uOkvS/xbv9Ta37phkqrCCfDlHz26dKA10ztb0jaJg7v3oCoOaZbYp9mZakMuaSPTrGjd1PVNcqMeJw7O27eCPTrMsvJpriX6XSJ5YRBWnCCS3GVLpmVM7EHVogR4enzRnrbG2h8rYuy/5BPJJK+472HZa39wuRtcM/tFaFoulVTUD5cpaZ+kP+2RJ6Je2laK6gj30X+UG4wp27XgT9zlaGibWccO2vbT17hz6dLOUqXgpjmRrHLnARvS0XVuQ/zXUYcojDcv/aeylpLuamDR8tS5RL1qgA1cDquYBKx+ndfvB3/gBDDFFi1rOkfsudrFVzEuGTZHX63gpmoaHfpUwNZoubkLLbanMttD0oRCtd0Y6Uvw5EEByyMd6nXXahCBHJn/0iqP+K4EL4T8jXhNx1hldX4EWhjl7KxQkrTJWLI=";


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
         * Simple
         * dynamic_text
         * kenbentuya
         * */

        String tplFolder = basePath + "/workspace/template/kenbentuya/";
        String outputPath = basePath + "/workspace/output/kenbentuya.mp4";

        String[] paths = {
                basePath + "/workspace/assets/1.jpeg",
                basePath + "/workspace/assets/2.jpeg",
                "/home/slayer/Desktop/workspace/music1.mp4",
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
                basePath + "/workspace/assets/6.jpeg"
        };


        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);
        task.setDynamicAdaptVideo(true);
        task.setRetainAudioOfVideo(true);

        try {
            boolean ret = task.render();

            // 获取渲染状态
            String info = task.getTaskRenderedInfo();

            // 获取渲染错误码
            int errorCode = task.getErrorCode();

            System.out.println(info + " : " + errorCode);
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
     * 渲染进度获取
     *
     * */

    @Test
    public void testProgress() {
        File f = new File("");
        String basePath = f.getAbsolutePath();
        basePath = "/home/slayer/Desktop";

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

        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);



        // 获取进度进程
        Thread progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    float progress = task.getRenderProgress();
                    System.out.println("-- " + progress );
                    if (progress == 1.0) {
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        progressThread.start();


        // 渲染开始
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
     * 测试 音乐, 水印
     *
     *
     * */
    @Test
    public void testOtherFeatures() {
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

        // 设置视频码率, 可以用于调整视频文件大小
        // 码率越高, 视频画面质量越高, 默认 0.25
        task.setBitrateControl(0.1f);

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