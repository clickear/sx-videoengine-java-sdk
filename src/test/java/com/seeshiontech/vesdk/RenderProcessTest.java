package com.seeshiontech.vesdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;


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

    public class DyamicSubFile {
        @JSONField(name="img_path")
        public String imgPath;
        @JSONField(name="d_key_prefix")
        public String dKeyPrefix;
        @JSONField(name="d_img_paths")
        public String[] dImgPaths;

        public DyamicSubFile(String imgPath, String dKeyPrefix, String[] dImgPaths) {
            this.imgPath = imgPath;
            this.dKeyPrefix = dKeyPrefix;
            this.dImgPaths = dImgPaths;
        }
    }

    /**
     * 测试动态模板设置附加的文字头像 昵称
     *
     * 附加素材动态模板测试
     *
     * dynamic_text
     *
     *
     * */

    @Test
    public void testDynamic() {
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

        // 给素材绑定关联的子素材
        ArrayList<DyamicSubFile> subFiles = new ArrayList<>();
        String[] subImgs = {
                basePath + "/workspace/assets/235_41_text1.png"
        };
        subFiles.add(new DyamicSubFile(basePath + "/workspace/assets/1.jpeg", "dtext", subImgs));
        String[] subImgs2 = {
                basePath + "/workspace/assets/235_41_text2.png",
                basePath + "/workspace/assets/235_41_text3.png",

        };
        String[] subImgs3 = {
                basePath + "/workspace/assets/235_41_text4.png"

        };
        String[] subImgs4 = {
                basePath + "/workspace/assets/235_41_text8.png" // 昵称
        };
        String[] subImgs5 = {
                basePath + "/workspace/assets/235_41_text9.png" // 头像
        };

        subFiles.add(new DyamicSubFile(basePath + "/workspace/assets/1.jpeg", "dtext", subImgs));
        subFiles.add(new DyamicSubFile(basePath + "/workspace/assets/2.jpeg", "dtext", subImgs2));
        subFiles.add(new DyamicSubFile(basePath + "/workspace/assets/3.jpeg", "dtext", subImgs3));
        subFiles.add(new DyamicSubFile(basePath + "/workspace/assets/4.jpeg", "dtext", subImgs4));
        subFiles.add(new DyamicSubFile(basePath + "/workspace/assets/4.jpeg", "dsubimg", subImgs5));


        String subFilesJson = JSON.toJSONString(subFiles);
        System.out.println(subFilesJson);


        String musicPath = basePath + "/workspace/music.mp3";
        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);
        task.setDynamicSubFiles(subFilesJson);
        task.setMusicPath(musicPath, true);
        task.setMusicLoop(false);
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


    /**
     * 进程模式渲染测试
     *
     * */
    @Test
    public void testRenderProcess() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        VideoEngine engine = new VideoEngine();

        /**
         * 常规模板测试
         *
         * Calorie
         * Flash
         * Screen
         *
         * */
/*        String tplFolder = basePath + "/workspace/template/Flash";
        String outputPath = basePath + "/workspace/output/flash.mp4";

        String[] paths = {
                "",
                basePath + "/workspace/544x960.test0.png"
        };*/


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
         * Album
         * ChineseStyle
         * Simple
         *
         * */

        String tplFolder = basePath + "/workspace/template/Album";
        String outputPath = basePath + "/workspace/output/album.mp4";

        String[] paths = {
                basePath + "/workspace/544x960.test0.png",
                basePath + "/workspace/544x960.test0.png",
                basePath + "/workspace/544x960.test0.png"
        };


        String musicPath = basePath + "/workspace/music.mp3";
        VeProcessRenderTask task = new VeProcessRenderTask(license, tplFolder, outputPath);
        task.setAssetPaths(paths);
        task.setMusicPath(musicPath, true);
        task.setMusicLoop(false);
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