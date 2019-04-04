package com.seeshion;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;
import org.junit.Test;
import org.junit.experimental.theories.Theories;

import java.io.File;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class VideoEngineTest {

    private  String license = "zPfg3zBjSVGqn/8GdXrN5dAtMFHUvDQEMBCFWAajVjtxkSA4mi8fQu9EtNHxE+G5zVqFOlZzTbJWTwhVFbtmWq8eti9sR2Pe8KO3TubpiWpZSXPGFBu6+suQYYlt/RFhNFc5am/3roENVzrWckuZIBg8pwbFjdzXt6bBJBPg93WVbVu8QETjQc9z2XBw+02LXB29PLg1xsclDi8nhQOB6oI8Iu2Z+Oh8HdWcS+j0BYKECHjvmUCBaFaNJBty0UfVoX0pGtN9Uou0vrT3dyWKpZHs0IZD+WebN8pfFtGWWIDwGkrX0YjPNDx/cWLo6bNIdbTqonxx6rQJ5ymaMOTchipRS15htnrUrDf5P3Bi+w5ht6zD5pTw8sI9q1bTZC3E5tf9vZBnALluHuXc9NOKbA==";

    @Test
    public void testException() {
        VideoEngine engine = new VideoEngine();
        engine.testException();
    }


    @Test
    public void testLicense() {
        VideoEngine engine = new VideoEngine();
        engine.registerLicense(license);
        boolean valid = engine.isLicenseValid();
        return;
    }


    /**
     * 进程模式渲染测试
     *
     * */
    @Test
    public void testRenderProcess() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        VideoEngine engine = new VideoEngine();
        engine.registerLicense(license);

        // test template flash
//        String tplFolder = basePath + "/workspace/template/Flash";
//        String outputPath = basePath + "/workspace/output/flash.mp4";
//
//        String[] paths = {
//                "",
//                basePath + "/workspace/544x960.test0.png"
//        };


        // test template calorie
//        String tplFolder = basePath + "/workspace/template/Calorie";
//        String outputPath = basePath + "/workspace/output/calorie.mp4";
//
//        String[] paths = {
//                basePath + "/workspace/544x960.test0.png"
//        };


        // test template Screen
        String tplFolder = basePath + "/workspace/template/Screen";
        String outputPath = basePath + "/workspace/output/screen.mp4";

        String[] paths = {
                basePath + "/workspace/544x960.test0.png"
        };


        Random r = new Random();
        String renderId = null;

        // 创建 render
        try {
            renderId = engine.pCreateRender(tplFolder, outputPath, r.nextInt(Integer.MAX_VALUE));
        } catch (RenderException e) {
            e.printStackTrace();
        } catch (NotSupportedTemplateException e) {
            e.printStackTrace();
        } catch (InvalidLicenseException e) {
            e.printStackTrace();
        }
        if (renderId.equals("")) {
            System.out.println("create render failed");
            return;
        }

        // 设置素材
        boolean set = engine.pChangeAssetPaths(renderId, paths);
        if (!set) {
            System.out.println("set assets error");
        }

        // 开始渲染, 并获取结果
        boolean ret = engine.pStartRender(renderId);

        // 获取渲染后的状态描述
        String status = engine.pGetRenderStatus(renderId);
        System.out.println("finish with start: " + ret + ", status: " + status);

        engine.pDestroyRender(renderId);
    }



    /**
     * 线程模式渲染测试
     *
     * @deprecated
     * */

    @Test
    public void testRender() {
        String tplFolder = "/home/slayer/Desktop/template/Calorie";
        String outputPath = "/home/slayer/Desktop/newest.mp4";
        VideoEngine engine = new VideoEngine();
        engine.registerLicense(license);
        System.out.println(engine.getLicenseProfile());

        String[] paths = {
                "/home/slayer/Desktop/export/asset2.jpg"
        };


        long renderId = 0;
        try {
            renderId = engine.createRender(tplFolder, outputPath);
        } catch (RenderException e) {
            e.printStackTrace();
        } catch (NotSupportedTemplateException e) {
            e.printStackTrace();
        } catch (InvalidLicenseException e) {
            e.printStackTrace();
        }
        engine.changeAssetPaths(renderId, paths);
//        engine.replaceAsset(renderId, "replace_key", "/home/slayer/Desktop/export/asset4.png");
//        engine.replaceAsset(renderId, "test", "/home/slayer/Desktop/export/asset2.jpg");
        //engine.setSnapShotFolder(renderId, snapShotFolder);


        long finalRenderId = renderId;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean ret = engine.startRender(finalRenderId);
            }
        });
        t.start();

        while (t.isAlive()) {
            float progress = engine.getRenderProgress(renderId);
            System.out.println("progress : " + progress);
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
        engine.destroyRender(renderId);
    }
}