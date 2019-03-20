package com.seeshion;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;
import org.junit.Test;
import org.junit.experimental.theories.Theories;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class VideoEngineTest {


    @Test
    public void testException() {
        VideoEngine engine = new VideoEngine();
        engine.testException();
    }


    @Test
    public void testLicense() {
        String license = "lTGjb5rCMjl3n2KsH15n9vPDHDu7/O5wmRH5c0gX5cO7nQxidsFa3gpN9tr3u138UXcjjePGG3fQ3RbZQIpmMJEqgkiahLUkiIsSjqiT9LZNR5r9tFrUwoOym8OD9ozttMMfF+Tng8aKjSxGl0siTg==";
        VideoEngine engine = new VideoEngine();
        engine.registerLicense(license);
        boolean valid = engine.isLicenseValid();
        return;
    }

    @Test
    public void testRenderProcess() {
        String license = "lTGjb5rCMjl3n2KsH15n9vPDHDu7/O5wmRH5c0gX5cO7nQxidsFa3gpN9tr3u138UXcjjePGG3fQ3RbZQIpmMJEqgkiahLUkiIsSjqiT9LZNR5r9tFrUwoOym8OD9ozttMMfF+Tng8aKjSxGl0siTg==";
        VideoEngine engine = new VideoEngine();
        engine.registerLicense(license);
        String tplFolder = "/home/slayer/Desktop/export10/Calorie";
        String outputPath = "/home/slayer/Desktop/newest.mp4";

        String[] paths = {
                "/home/slayer/Desktop/export/asset2.jpg"
        };


//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
                Random r = new Random();
                String renderId = engine.pCreateRender(tplFolder, outputPath, r.nextInt(Integer.MAX_VALUE));
                int num = engine.pChangeAssetPaths(renderId, paths);
                int ret = engine.pStartRender(renderId, false);
                String str = engine.pGetRenderStatus(renderId);
                System.out.println("status --- : " + str);
//            }
//        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                String outputPath = "/home/slayer/Desktop/newest1.mp4";
                String renderId = engine.pCreateRender(tplFolder, outputPath, r.nextInt(Integer.MAX_VALUE));
                int num = engine.pChangeAssetPaths(renderId, paths);
                int ret = engine.pStartRender(renderId);
                String str = engine.pGetRenderStatus(renderId);
                System.out.println("status --- : " + str);
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                String outputPath = "/home/slayer/Desktop/newest2.mp4";
                String renderId = engine.pCreateRender(tplFolder, outputPath, r.nextInt(Integer.MAX_VALUE));
                int num = engine.pChangeAssetPaths(renderId, paths);
                int ret = engine.pStartRender(renderId, false);
                String str = engine.pGetRenderStatus(renderId);
                System.out.println("status --- : " + str);
            }
        });


        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                String outputPath = "/home/slayer/Desktop/newest3.mp4";
                String renderId = engine.pCreateRender(tplFolder, outputPath, r.nextInt(Integer.MAX_VALUE));
                int num = engine.pChangeAssetPaths(renderId, paths);
                int ret = engine.pStartRender(renderId);
                String str = engine.pGetRenderStatus(renderId);
                System.out.println("status --- : " + str);
            }
        });

//        t.start();
//        t1.start();
//        t2.start();
//        t3.start();

        System.out.println("main thread start ---");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread end ---");

//        engine.pDestroyRender(renderId);
    }


    @Test
    public void testRender() {
        String tplFolder = "/home/slayer/Desktop/template/Calorie";
        String outputPath = "/home/slayer/Desktop/newest.mp4";
//        String license = "lTGjb5rCMjl3n2KsH15n9vPDHDu7/O5wmRH5c0gX5cO7nQxidsFa3gpN9tr3u138UXcjjePGG3fQ3RbZQIpmMJEqgkiahLUkiIsSjqiT9LZNR5r9tFrUwoOym8OD9ozttMMfF+Tng8aKjSxGl0siTg==";
//        String license = "0gUIWb7AuYS0/ULStx+thJ0uMRkKFbTnomn0rntQG+QXhUpTco04Wh7qV4RyjIAV9mLEYWcU5YanAdJ9iTihjzLm6kOlPc1go7s5oDXO7jXTUJG3F567/rjFwcE40tNavQgeew0f6NC0durbX/vwvAlgai9YcNOlupSe0ey7jjO49AVgDilG/szB9wDZtboTCBL+ecQoXanVVxjbqnXPTJFf7boHeUPHNCiI6VEb2FhI0dWSXIwfzEMiZPA5X8B1DLW2zYeRvyxlm2DjRVnqRdwbEBVOUd7ifDOL5AWSZl8+3V4yfPkETifrccnmtImn";

        String license="zPfg3zBjSVGqn/8GdXrN5dAtMFHUvDQEMBCFWAajVjtxkSA4mi8fQu9EtNHxE+G5zVqFOlZzTbJWTwhVFbtmWq8eti9sR2Pe8KO3TubpiWpZSXPGFBu6+suQYYlt/RFhNFc5am/3roENVzrWckuZIBg8pwbFjdzXt6bBJBPg93WVbVu8QETjQc9z2XBw+02LXB29PLg1xsclDi8nhQOB6oI8Iu2Z+Oh8HdWcS+j0BYKECHjvmUCBaFaNJBty0UfVoX0pGtN9Uou0vrT3dyWKpZHs0IZD+WebN8pfFtGWWIDwGkrX0YjPNDx/cWLo6bNIdbTqonxx6rQJ5ymaMOTchipRS15htnrUrDf5P3Bi+w5ht6zD5pTw8sI9q1bTZC3E5tf9vZBnALluHuXc9NOKbA==";
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

    @Test
    public void createRender() {
    }

    @Test
    public void changeAssetPaths() {
    }

    @Test
    public void startRender() {
    }

    @Test
    public void getRenderProgress() {
    }

    @Test
    public void getRenderValidateError() {
    }

    @Test
    public void getRenderValidateErrorIndex() {
    }
}