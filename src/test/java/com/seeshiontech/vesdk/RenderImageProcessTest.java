package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class RenderImageProcessTest {

    // license expire at 2019-06-20
    private String license ="uOkvS/xbv9Ta37phkqrCCfDlHz26dKA10ztb0jaJg7v3oCoOaZbYp9mZakMuaSPTrGjd1PVNcqMeJw7O27eCPTrMsvJpriX6XSJ5YRBWnCCS3GVLpmVM7EHVogR4enzRPH8iGBYbkcfbiOzqsK3F3WHZa39wuRtcM/tFaFoulVTUD5cpaZ+kP+2RJ6Je2laK6gj30X+UG4wp27XgT9zlaGibWccO2vbT17hz6dLOUqXgpjmRrHLnARvS0XVuQ/zXUYcojDcv/aeylpLuamDR8tS5RL1qgA1cDquYBKx+nddUseQsUoNot9BfE/BDL+fHVzEuGTZHX63gpmoaHfpUwNZoubkLLbanMttD0oRCtd0Y6Uvw5EEByyMd6nXXahCBbxZa14J/2bzXs2QsxFRLgLJj6LUFBTJp8ce7cTQ0A0e+jhWwAZNAOFQkJ0paJHP7rLAuiiiR50AqIwzegmQg/NTla8EwK/vO6jGQpfO0YDpjWBQFcAbBo96VjwlXN1mZFKFM9g83Z8jD3XtUayfq4w==";

    /**
     * 测试图片滤镜
     *
     * */

    @Test
    public void testImageFilterRender() {
        File f = new File("");
        String basePath = f.getAbsolutePath();

        String outputPath = basePath + "/workspace/output/";

        // 图片尺寸不能大于 4096 * 4096
        String[] paths = {
                basePath + "/workspace/assets/3.jpeg",
                basePath + "/workspace/assets/4.jpeg",
                basePath + "/workspace/assets/5.jpeg",
        };

        String[] filters = {
                basePath + "/workspace/filter/white",
        };


        VeImageProcessRenderTask task = new VeImageProcessRenderTask(license, paths, filters, outputPath);

        System.out.println(task.getLicenseProfile());


        // 添加水印
        Watermark mark = new Watermark();
        List<String> watermarkPaths = new ArrayList<>();
        watermarkPaths.add(basePath + "/workspace/watermark.png");
        mark.setPaths(watermarkPaths);

        List<Watermark> list = new ArrayList<>();
        list.add(mark);
        task.setWatermarkList(list);

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