package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;

import java.util.Iterator;
import java.util.List;
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
     * 背景音乐淡出时间, 单位: 秒
     *
     * 在视频结束倒数多少时间开始淡出
     * */
    private int musicFadeoutDuration = 0;

    /**
     * 设置音量
     *
     * 最终音量为 : 原音量 * volume
     *
     * 比如 volume = 0.5, 则输出音量为原音量的一半
     *
     * */
    private float musicVolume = 1.0f;

    /**
     * 视频比特率控制参数
     *
     * 范围:　0.0 - 1.0
     **/
    private float bitrateControl = 0.25f;

    /**
     * 动态模板附加素材数据　json 字符串
     *
     * @note 非动态模板设置无效
     * */
    private String subImgJson;

    /**
     * 水印
     * */
    private List<Watermark> watermarkList;


    /**
     * lua main 函数所在脚本文件
     * */
    private String scriptMainFile;

    /**
     * lua 脚本目录, 无传空字符串
     * */
    private String scriptDir = "";

    /**
     * lua 脚本数据
     * */
    private String scriptData = "";

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

    /**
     * 设置音乐是否循环
     * */
    public void setMusicLoop(boolean musicLoop) {
        this.musicLoop = musicLoop;
    }

    public void setTemplateType(TemplateType type) {
        this.templateType = type;
    }

    /**
     * 检查 license 是否有效
     * */
    public boolean isLicenseValid() {
        return engine.isRenderProcessLicenseValid(renderId);
    }

    /**
     * 获取 license 信息
     *
     * */
    public String getLicenseProfile() {
        if (!initialized){
            return "";
        }

        return engine.getRenderProcessLicenseProfile(renderId);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置替换素材路径
     *
     * */
    public boolean setAssetPaths(String[] paths) {
        this.assetPaths = paths;
        return true;
    }

    /**
     * 设置音乐文件
     *
     * */
    public boolean setMusicPath(String musicPath, boolean loopMusic) {
        this.musicPath = musicPath;
        this.musicLoop = loopMusic;
        return true;
    }

    /**
     * 设置视频比特率控制参数,默认 0.25
     * */
    public boolean setBitrateControl(float control) {
        this.bitrateControl = control;
        return true;
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

    public int getMusicFadeoutDuration() {
        return musicFadeoutDuration;
    }

    /**
     * 设置淡出时间, 单位秒
     *
     * */
    public void setMusicFadeoutDuration(int musicFadeoutDuration) {
        this.musicFadeoutDuration = musicFadeoutDuration;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    /**
     * 设置音量
     *
     * */
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }


    /**
     * 设置动态模板附加素材
     * */
    public boolean setDynamicSubFiles(String json) {
        this.subImgJson = json;
        return true;
    }

    /**
     * 启动渲染
     * */
    public boolean render() throws InvalidLicenseException, RenderException, NotSupportedTemplateException {
        if (!initialized) {
            errorMsg = "task not initialized";
            return false;
        }
        if (this.assetPaths != null && this.assetPaths.length > 0) {
            boolean set = engine.setRenderProcessReplaceableFiles(renderId, assetPaths);
            if (!set) {
                errorMsg = "set task asset paths failed";
                return false;
            }
        }

        if (this.musicPath != null && this.musicPath.length() > 0) {
            boolean set = engine.setRenderProcessMusicFile(renderId, musicPath, musicLoop);
            if (!set) {
                errorMsg = "set task music paths failed";
                return false;
            }

            engine.setRenderProcessMusicFadeoutDuration(renderId, musicFadeoutDuration);
            engine.setRenderProcessMusicVolume(renderId, musicVolume);
        }

        if (subImgJson != null && subImgJson.length() > 0) {
            boolean set = engine.setRenderProcessDynamicSubFiles(renderId, subImgJson);
            if (!set) {
                errorMsg = "set task text paths failed";
                return false;
            }
        }

        if (watermarkList != null && watermarkList.size() > 0) {
            Iterator<Watermark> it = watermarkList.iterator();
            while(it.hasNext()) {
                Watermark mark = it.next();
                String[] paths = mark.getPaths().toArray(new String[mark.getPaths().size()]);
                engine.addRenderProcessWatermark(renderId, paths, mark.getPosX(), mark.getPoxY(), mark.getTimeStart(), mark.getTimeEnd(), mark.getScaleX(), mark.getScaleY());
            }
        }

        if (scriptMainFile != null && scriptMainFile.length() > 0) {
            engine.setRenderProcessScript(renderId, scriptMainFile, scriptDir, scriptData);
        }


        boolean set = engine.setRenderProcessBitrateControl(renderId, bitrateControl);

        engine.setRenderProcessMusicLoop(renderId, this.musicLoop);

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


    /**
     * 获取渲染后的信息, 在 render() 后调用
     *
     * */
    public String getTaskRenderedInfo() {
        return engine != null ? engine.getRenderProcessRenderedInfo(renderId) : "";
    }


    /**
     * 销毁渲染资源,必须调用
     * */
    public void destroy() {
       engine.destroyRenderProcess(renderId);
    }


    /**
     * 设置脚本目录
     * */
    public void setScriptDir(String scriptDir) {
        this.scriptDir = scriptDir;
    }

    /**
     * 设置脚本参数
     * */
    public void setScriptData(String scriptData) {
        this.scriptData = scriptData;
    }

    /**
     * 设置脚本主文件路径
     * */
    public void setScriptMainFile(String scriptMainFile) {
        this.scriptMainFile = scriptMainFile;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
