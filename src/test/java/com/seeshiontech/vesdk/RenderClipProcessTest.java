package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 剪辑测试
 *
 * @author  dongyado
 * */


public class RenderClipProcessTest {

    // license only for video clip, expire at 2019-08-20
    private String license = "uOkvS/xbv9Ta37phkqrCCfDlHz26dKA10ztb0jaJg7v3oCoOaZbYp9mZakMuaSPTrGjd1PVNcqMeJw7O27eCPTrMsvJpriX6XSJ5YRBWnCCS3GVLpmVM7EHVogR4enzR/uzGfrP5ptm43dUV4Tw+ZGHZa39wuRtcM/tFaFoulVTUD5cpaZ+kP+2RJ6Je2laK6gj30X+UG4wp27XgT9zlaGibWccO2vbT17hz6dLOUqWLnjJ4EfinuxGHfPdyV7CdzSx/CZgpCP5RKnICXNflH2G3rMPmlPDywj2rVtNkLcSB6ugaMxHYQRAm78BlehDb";

    /**
     *  clip 测试
     *
     * */
    @Test
    public void testClip() {

        File f = new File("");
        String basePath = f.getAbsolutePath();


        String outputPath = basePath + "/workspace/output/clip.mp4";
        VeClipProcessRenderTask task = new VeClipProcessRenderTask(license, outputPath);
        task.setRetainAudioOfVideo(true);

        String luaDir = "/home/slayer/Desktop/workspace/lua/clip/";
        String mainLuaFile = luaDir + "Test.lua";


        String scriptData = "{\n" +
                "    \"files\": [\"/home/slayer/Desktop/workspace/edit/source.mp4\"],\n" +
                "    \"assets\": [{\n" +
                "        \"f\": 0,\n" +
                "        \"clips\": [{\n" +
                "            \"t\": [60000, 80000],\n" +
                "            \"mode\": \"fixed\"\n" +
                "        }, {\n" +
                "            \"t\": [30000, 50000],\n" +
                "            \"mode\": \"fixed\"\n" +
                "        }]\n" +
                "    }],\n" +
                "    \"iter\": \"break\"}";


        task.setScriptMainFile(mainLuaFile);
        task.setScriptDir(luaDir);
        task.setScriptData(scriptData);

        task.setWidth(1080);
        task.setHeight(1920);
        task.setFrameRate(30);

        try {
            boolean ret = task.render();

            String info = task.getTaskRenderedInfo();
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

}