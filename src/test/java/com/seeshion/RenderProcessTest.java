package com.seeshion;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;
import org.junit.Test;

import java.io.File;
import java.util.Random;


public class RenderProcessTest {

    private  String license = "zPfg3zBjSVGqn/8GdXrN5dAtMFHUvDQEMBCFWAajVjtxkSA4mi8fQu9EtNHxE+G5zVqFOlZzTbJWTwhVFbtmWq8eti9sR2Pe8KO3TubpiWpZSXPGFBu6+suQYYlt/RFhNFc5am/3roENVzrWckuZIBg8pwbFjdzXt6bBJBPg93WVbVu8QETjQc9z2XBw+02LXB29PLg1xsclDi8nhQOB6oI8Iu2Z+Oh8HdWcS+j0BYKECHjvmUCBaFaNJBty0UfVoX0pGtN9Uou0vrT3dyWKpZHs0IZD+WebN8pfFtGWWIDwGkrX0YjPNDx/cWLo6bNIdbTqonxx6rQJ5ymaMOTchipRS15htnrUrDf5P3Bi+w5ht6zD5pTw8sI9q1bTZC3E5tf9vZBnALluHuXc9NOKbA==";


    /**
     * 进程模式渲染测试
     *
     * */
    @Test
    public void testRenderProcess() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        VideoEngine engine = new VideoEngine();
        // test template flash
//        String tplFolder = basePath + "/workspace/template/Flash";
//        String outputPath = basePath + "/workspace/output/flash.mp4";
//
//        String[] paths = {
//                "",
//                basePath + "/workspace/544x960.test0.png"
//        };


        // test template calorie
        String tplFolder = basePath + "/workspace/template/Calorie";
        String outputPath = basePath + "/workspace/output/calorie.mp4";

        String[] paths = {
                basePath + "/workspace/544x960.test0.png"
        };


        // test template Screen
//        String tplFolder = basePath + "/workspace/template/Screen";
//        String outputPath = basePath + "/workspace/output/screen.mp4";
//
//        String[] paths = {
//                basePath + "/workspace/544x960.test0.png"
//        };


        Random r = new Random();
        String renderId = null;


        // 创建 render
        renderId = engine.createRenderProcess(tplFolder, outputPath, r.nextInt(Integer.MAX_VALUE));
        engine.registerRenderProcessLicense(renderId, license);

        if (engine.isRenderProcessLicenseValid(renderId)) {
            System.out.println("valid license");
        }

        System.out.println(engine.getRenderProcessLicenseProfile(renderId));

        if (renderId.equals("")) {
            System.out.println("create render failed");
            return;
        }

        System.out.println("create render id: " + renderId);
        // 设置素材
        boolean set = engine.setRenderProcessReplaceableFiles(renderId, paths);
        if (!set) {
            System.out.println("set assets error");
        }

        // 开始渲染, 并获取结果
        boolean ret = false;
        try {
            ret = engine.startRenderProcess(renderId, false);
        } catch (RenderException e) {
            e.printStackTrace();
        } catch (NotSupportedTemplateException e) {
            e.printStackTrace();
        } catch (InvalidLicenseException e) {
            e.printStackTrace();
        }

        // 获取渲染后的状态描述
        String status = engine.getRenderProcessStatus(renderId);
        System.out.println("finish with start ret: " + ret + ", status: " + status);

        engine.destroyRenderProcess(renderId);
    }



}