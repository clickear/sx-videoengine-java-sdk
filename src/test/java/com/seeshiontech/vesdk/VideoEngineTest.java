package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;


public class VideoEngineTest {

    // license expire at 2019-05-20
    private String license = "uOkvS/xbv9Ta37phkqrCCfDlHz26dKA10ztb0jaJg7v3oCoOaZbYp9mZakMuaSPTrGjd1PVNcqMeJw7O27eCPTrMsvJpriX6XSJ5YRBWnCCS3GVLpmVM7EHVogR4enzRw85LzYaSsniGRSqW5ZnWf2HZa39wuRtcM/tFaFoulVTUD5cpaZ+kP+2RJ6Je2laK6gj30X+UG4wp27XgT9zlaGibWccO2vbT17hz6dLOUqXgpjmRrHLnARvS0XVuQ/zXUYcojDcv/aeylpLuamDR8tS5RL1qgA1cDquYBKx+ndcfr/I4sBqgH+1b6HHsPMULVzEuGTZHX63gpmoaHfpUwNZoubkLLbanMttD0oRCtd0Y6Uvw5EEByyMd6nXXahCBsyQG1Uk6xS6cyXZVSfril1hldX4EWhjl7KxQkrTJWLI=";

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