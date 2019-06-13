package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;

import java.util.Iterator;
import java.util.List;

public class VeImageProcessRenderTask {

    /**
     * license
     * */
    private String license;

    /**
     * 图片绝对路径
     * */
    private String[] imagePaths;

    /**
     * 图片输出目录绝对路径
     * */
    private String outputPath;

    /**
     * 滤镜路径
     * */
    private String[] filterPaths;

    /**
     * vengine 核心类
     * */
    private VideoEngine engine;

    /**
     * 创建render时返回的 render id
     * */
    private String renderId;

    /**
     * 错误信息
     * */
    private String errorMsg = "";

    /**
     * 模板类型
     *
     * 普通模板
     * 动态模板
     *
     * */
    private TemplateType templateType = TemplateType.IMAGE_FILTER;

    /**
     * 是否已经初始化
     * */
    private boolean initialized = false;

    /**
     * 水印
     * */
    private List<Watermark> watermarkList;


    public VeImageProcessRenderTask(String license, String[] imagePaths, String[] filterPaths, String outputPath) {
        this.license = license;
        this.outputPath = outputPath;
        this.imagePaths = imagePaths;
        this.filterPaths = filterPaths;

        initTask();
        if (initialized) {
            engine.registerRenderImageProcessLicense(renderId, license);
        }
    }


    private boolean initTask() {
        if (engine == null) {
            engine = new VideoEngine();
            renderId = engine.createRenderImageProcess(imagePaths, outputPath);
            if (renderId.isEmpty()) {
                return false;
            }
            this.initialized = true;
        }
        return true;
    }


    /**
     * 检查 license 是否有效
     * */
    public boolean isLicenseValid() {
        return engine.isRenderImageProcessLicenseValid(renderId);
    }

    /**
     * 获取 license 信息
     *
     * */
    public String getLicenseProfile() {
        if (!initialized){
            return "";
        }

        return engine.getRenderImageProcessLicenseProfile(renderId);
    }

    public String getErrorMsg() {
        return errorMsg;
    }


    public List<Watermark> getWatermarkList() {
        return watermarkList;
    }

    /**
     * 设置水印
     * */
    public void setWatermarkList(List<Watermark> watermarkList) {
        this.watermarkList = watermarkList;
    }

    /**
     * 启动渲染
     * */
    public boolean render() throws InvalidLicenseException, RenderException, NotSupportedTemplateException {
        if (!initialized) {
            errorMsg = "task not initialized";
            return false;
        }

        if (watermarkList != null && watermarkList.size() > 0) {
            Iterator<Watermark> it = watermarkList.iterator();
            while(it.hasNext()) {
                Watermark mark = it.next();
                String[] paths = mark.getPaths().toArray(new String[mark.getPaths().size()]);
                engine.addRenderImageProcessWatermark(renderId, paths, mark.getPosX(), mark.getPoxY(), mark.getTimeStart(), mark.getTimeEnd(), mark.getScaleX(), mark.getScaleY());
            }
        }

        if (filterPaths != null && filterPaths.length > 0) {
            for(int i = 0, ilen = filterPaths.length; i < ilen; i++) {
                engine.addRenderImageProcessFilter(renderId, filterPaths[i]);
            }
        }

        boolean success = engine.startRenderImageProcess(renderId);

        if (!success)  {
            errorMsg = "start render process failed";
            return false;
        }

        return true;
    }


    /**
     * 销毁渲染资源,必须调用
     * */
    public void destroy() {
       engine.destroyRenderImageProcess(renderId);
    }

}
