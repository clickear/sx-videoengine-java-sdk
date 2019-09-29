package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;

import java.io.File;

/**
 * gif / 视频裁剪测试
 *
 * @author  dongyado
 * */


public class RenderCutProcessTest {

    /**
     * 测试 gif 裁剪
     *
     * */

    @Test
    public void testGifCut() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        String inputPath = basePath + "/workspace/assets/4.gif";
        String outputPath = basePath + "/workspace/output/cutgif.mp4";

        // 确保 源文件为 gif, type 为 TASK_CUT_GIF
        VeCutProcessRenderTask task = new VeCutProcessRenderTask(inputPath, outputPath, CutTaskType.TASK_CUT_GIF);

        // 从第5 秒开始截取
        task.setStartTime(-5);
        // 截取 10秒钟
        task.setDuration(10);

        // 输出尺寸
        task.setSize(200, 200);

        // 向右下角移动 50, 50
        task.setPosition(50, 50);

        // 缩放 到 0.5
        task.setScale(0.5f);

        // 旋转
        task.setRotation(45);

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

    @Test
    public void testVideoCut() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        String inputPath = basePath + "/workspace/assets/music2.mp4";
        String outputPath = basePath + "/workspace/output/cutvideo.mp4";
        VeCutProcessRenderTask task = new VeCutProcessRenderTask(inputPath, outputPath, CutTaskType.TASK_CUT_VIDEO);

        // 从第 5 秒开始截取
        task.setStartTime(-5);
        // 截取 10 秒
        task.setDuration(10);

        // 设置输出比特率参数
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

}