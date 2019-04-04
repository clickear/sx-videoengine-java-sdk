package com.seeshion;


import java.io.File;
import java.util.Random;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;

public class Test {
    public static void main(String[] args) throws Exception {
        for (int idx = 0; idx < 6; ++idx) {
            final int threadIdx = idx;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("start " + threadIdx);
                    testRenderProcess();
                    System.out.println("end " + threadIdx);
                }
            }).start();
        }
    }

    private static String license = "zPfg3zBjSVGqn/8GdXrN5dAtMFHUvDQEMBCFWAajVjtxkSA4mi8fQu9EtNHxE+G5zVqFOlZzTbJWTwhVFbtmWq8eti9sR2Pe8KO3TubpiWpZSXPGFBu6+suQYYlt/RFhNFc5am/3roENVzrWckuZIBg8pwbFjdzXt6bBJBPg93WVbVu8QETjQc9z2XBw+02LXB29PLg1xsclDi8nhQOB6oI8Iu2Z+Oh8HdWcS+j0BYKECHjvmUCBaFaNJBty0UfVoX0pGtN9Uou0vrT3dyWKpZHs0IZD+WebN8pfFtGWWIDwGkrX0YjPNDx/cWLo6bNIdbTqonxx6rQJ5ymaMOTchipRS15htnrUrDf5P3Bi+w5ht6zD5pTw8sI9q1bTZC3E5tf9vZBnALluHuXc9NOKbA==";

    static void testRenderProcess() {
        String basePath = new File("").getAbsolutePath();

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
        String outputPath = basePath + "/workspace/output/screen_" + System.currentTimeMillis() + ".mp4";

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
}
