package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;

import java.util.Random;

public class VeProcessRenderTask {

    /**
     * license
     * */
    private String license;

    /**
     * 模板文件夹绝对路径
     * */
    private String tplFolder;

    /**
     * 视频输出绝对路径, mp4 结尾
     * */
    private String outputPath;

    /**
     * 替换素材, 绝对路径数组
     * */
    private String[] assetPaths;

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
    private TemplateType templateType = TemplateType.NORMAL_TEMPLATE;

    /**
     * 是否已经初始化
     * */
    private boolean initialized = false;

    /**
     * 音乐文件绝对路径
     * */
    private String musicPath;

    /**
     * 是否开启音乐循环
     * */
    private boolean musicLoop = false;

    /**
     * 视频比特率控制参数
     *
     * 范围:　0.0 - 1.0
     **/
    private float bitrateControl = 0.25f;

    /**
     * 动态模板附加素材数据　json 字符串
     * */
    private String subImgJson;

    /**
     * 渲染任务状态
     *
     * start   开始
     * update　更新
     * end　　 渲染完成
     * fail　　渲染失败
     * cancel　渲染被取消
     * crash　 渲染崩溃
     * */
    private String status = "";

    public VeProcessRenderTask(String license, String tplFolder, String outputPath) {
        this.license = license;
        this.tplFolder = tplFolder;
        this.outputPath = outputPath;

        initTask();
        if (initialized) {
            engine.registerRenderProcessLicense(renderId, license);
        }
    }

    public VeProcessRenderTask(String license, String tplFolder, String outputFile, String[] assetPaths) {
        this(license, tplFolder, outputFile);
        this.assetPaths = assetPaths;
    }

    private boolean initTask() {
        if (engine == null) {
            engine = new VideoEngine();
            Random r = new Random();
            renderId = engine.createRenderProcess(tplFolder, outputPath, r.nextInt(Integer.MAX_VALUE));
            if (renderId.isEmpty()) {
                return false;
            }
            this.initialized = true;
        }
        return true;
    }


    public String getStatus() {
        return status;
    }

    public boolean isMusicLoop() {
        return musicLoop;
    }

    public void setMusicLoop(boolean musicLoop) {
        this.musicLoop = musicLoop;
    }

    public void setTemplateType(TemplateType type) {
        this.templateType = type;
    }

    public boolean isLicenseValid() {
        return engine.isRenderProcessLicenseValid(renderId);
    }

    public String getLicenseProfile() {
        if (!initialized){
            return "";
        }

        return engine.getRenderProcessLicenseProfile(renderId);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean setAssetPaths(String[] paths) {
        this.assetPaths = paths;
        return true;
    }

    public boolean setMusicPath(String musicPath, boolean loopMusic) {
        this.musicPath = musicPath;
        this.musicLoop = loopMusic;
        return true;
    }

    public boolean setBitrateControl(float control) {
        this.bitrateControl = control;
        return true;
    }

    /**
     * 为动态模板设置附加素材
     *
     * @note 非动态模板设置无效
     *
     * */
    public boolean setDynamicSubFiles(String json) {
        this.subImgJson = json;
        return true;
    }

    public boolean render() throws InvalidLicenseException, RenderException, NotSupportedTemplateException {
        if (!initialized) {
            errorMsg = "task not initialized";
            return false;
        }
        if (this.assetPaths != null && this.assetPaths.length > 0) {
            boolean set = engine.setRenderProcessReplaceableFiles(renderId, this.assetPaths);
            if (!set) {
                errorMsg = "set task asset paths failed";
                return false;
            }
        }

        if (this.musicPath != null && this.musicPath.length() > 0) {
            boolean set = engine.setRenderProcessMusicFile(renderId, this.musicPath, this.musicLoop);
            if (!set) {
                errorMsg = "set task music paths failed";
                return false;
            }
        }

        if (subImgJson != null && subImgJson.length() > 0) {
            boolean set = engine.setRenderProcessDynamicSubFiles(renderId, subImgJson);
            if (!set) {
                errorMsg = "set task text paths failed";
                return false;
            }
        }


        boolean set = engine.setRenderProcessBitrateControl(renderId, this.bitrateControl);

        set = engine.setRenderProcessMusicLoop(renderId, this.musicLoop);

        boolean success = engine.startRenderProcess(renderId);

        if (!success)  {
            errorMsg = "start render process failed";
            return false;
        }


        status = engine.getRenderProcessStatus(renderId);
        if (status.equals("end")) {
            errorMsg = "task exit with status : " + status;
            return true;
        }

        errorMsg = "task exit with status : " + status;
        return false;
    }


    public void destroy() {
       engine.destroyRenderProcess(renderId);
    }

}
