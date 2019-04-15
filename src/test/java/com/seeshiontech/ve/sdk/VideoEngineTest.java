package com.seeshiontech.ve.sdk;

import com.seeshiontech.ve.sdk.exceptions.InvalidLicenseException;
import com.seeshiontech.ve.sdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.ve.sdk.exceptions.RenderException;
import org.junit.Test;


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