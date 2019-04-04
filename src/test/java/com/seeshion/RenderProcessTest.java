package com.seeshion;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;
import org.junit.Test;

import java.io.File;


public class RenderProcessTest {

    @Test
    public  void test()  {
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

        String musicPath = basePath + "/workspace/music.mp3";

        // test template Screen
//        String tplFolder = basePath + "/workspace/template/Screen";
//        String outputPath = basePath + "/workspace/output/screen.mp4";
//
//        String[] paths = {
//                basePath + "/workspace/544x960.test0.png"
//        };

        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);
        task.setMusicFile(musicPath, true);
        System.out.println(task.getLicenseProfile());

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