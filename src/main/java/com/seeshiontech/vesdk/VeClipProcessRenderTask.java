package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;

import java.util.Random;

/**
 * 视频剪辑 RenderTask
 *
 * */

public class VeClipProcessRenderTask {

    /**
     * license
     * */
    private String license;


    /**
     * 视频输出绝对路径, mp4 结尾
     * */
    private String outputPath;

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
     * 错误码 {@link ErrorCode}
     * */
    private int errorCode = ErrorCode.ERR.getErrCode();

    /**
     * 是否已经初始化
     * */
    private boolean initialized = false;

    /**
     * 视频比特率控制参数
     *
     * 范围:　0.0 - 1.0
     **/
    private float bitrateControl = 0.25f;

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
     * 快照目录
     * */
    private String snapShotPath;

    /**
     * 快照帧索引
     * */
    private int[] snapShotFrames;

    /**
     * 渲染任务状态 {@link RenderStatus}
     * */
    private String status = "";


    /**
     * 是否保留视频中的音频
     * */
    private boolean retainAudioOfVideo = false;



    /**
     * 剪辑视频宽度, px
     * */

    int width;

    /**
     * 剪辑视频高度, px
     * */
    int height;

    /**
     * 剪辑视频导出帧率, fps
     * */
    float frameRate;

    public VeClipProcessRenderTask(String license, String outputPath) {
        this.license = license;
        this.outputPath = outputPath;

        initTask();
        if (initialized) {
            engine.registerRenderProcessLicense(renderId, license);
        }
    }


    private boolean initTask() {
        if (engine == null) {
            engine = new VideoEngine();
            Random r = new Random();
            renderId = engine.createRenderClipProcess(outputPath, r.nextInt(Integer.MAX_VALUE));
            if (renderId.isEmpty()) {
                return false;
            }
            this.initialized = true;
        }
        return true;
    }

    /**
     * 获取渲染错误码
     *
     * @return int, see {@link ErrorCode}
     * */
    public int getErrorCode() {
        if (engine != null) {
           return engine.getRenderProcessError(renderId) ;
        }
        return ErrorCode.ERR.getErrCode();
    }


    /**
     * 获取渲染任务状态
     *
     * @return String, see {@link RenderStatus}
     * */
    public String getStatus() {
        return status;
    }

    /**
     * 检查 license 是否有效
     *
     * @return boolean
     * */
    public boolean isLicenseValid() {
        return engine.isRenderProcessLicenseValid(renderId);
    }


    /**
     * 获取 license 信息
     *
     * @return String
     * */
    public String getLicenseProfile() {
        if (!initialized){
            return "";
        }

        return engine.getRenderProcessLicenseProfile(renderId);
    }


    /**
     * 获取渲染错误信息
     *
     * @return String, see {@link RenderStatus}
     * */
    public String getErrorMsg() {
        return errorMsg;
    }


    /**
     * 设置视频比特率控制参数,默认 0.25
     *
     * @param control
     * */
    public void setBitrateControl(float control) {
        this.bitrateControl = control;
    }


    /**
     * 是否保留视频替换素材中的音频
     *
     * @param retainAudioOfVideo
     * */
    public void setRetainAudioOfVideo(boolean retainAudioOfVideo) {
        this.retainAudioOfVideo = retainAudioOfVideo;
    }

    /**
     * 启动渲染
     *
     * @throws InvalidLicenseException
     * @throws RenderException
     * @throws NotSupportedTemplateException
     * @return boolean
     * */
    public boolean render() throws InvalidLicenseException, RenderException, NotSupportedTemplateException {
        if (!initialized) {
            errorCode = ErrorCode.ERR.getErrCode();
            errorMsg = "clip task not initialized";
            return false;
        }

        if (scriptMainFile != null && scriptMainFile.length() > 0) {
            engine.setRenderProcessScript(renderId, scriptMainFile, scriptDir, scriptData);
        }


        engine.setRenderProcessBitrateControl(renderId, bitrateControl);
        engine.setRenderProcessRetainAudioOfVideo(renderId, retainAudioOfVideo);

        engine.setRenderClipProcessParams(renderId, width, height, frameRate);

        errorCode = engine.nStartRenderProcess(renderId);

        if (errorCode != ErrorCode.OK.getErrCode())  {
            errorMsg = "start clip process failed, error code : " + errorCode;
            return false;
        }


        status = engine.getRenderProcessStatus(renderId);
        if (status.equals(RenderStatus.END.getStatus())) {
            errorMsg = "task exit with status : " + status + ", error code : " + errorCode;
            return true;
        }

        errorMsg = "task exit with status : " + status + ", error code : " + errorCode;
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
     *
     * @param scriptDir
     * */
    public void setScriptDir(String scriptDir) {
        this.scriptDir = scriptDir;
    }

    /**
     * 设置脚本参数
     *
     * @param scriptData
     * */
    public void setScriptData(String scriptData) {
        this.scriptData = scriptData;
    }

    /**
     * 设置脚本主文件路径
     *
     * @param  scriptMainFile
     * */
    public void setScriptMainFile(String scriptMainFile) {
        this.scriptMainFile = scriptMainFile;
    }


    /**
     * 设置快照保存目录
     *
     * @param snapShotPath, 目录
     * */
    public void setSnapShotPath(String snapShotPath) {
        this.snapShotPath = snapShotPath;
    }


    /**
     * 设置快照帧索引
     *
     * [1, 100] 表示第1, 100 帧会被生成 1.png , 100.png 到 snapshot path 目录中
     *
     * @param snapShotFrames, 帧数组
     * */
    public void setSnapShotFrames(int[] snapShotFrames) {
        this.snapShotFrames = snapShotFrames;
    }


    /**
     * 获取渲染进度
     *
     * 无论成功失败,最终都会返回 1.0
     *
     * @return float, 范围 0.0 - 1.0
     * */

    public float getRenderProgress() {
        if (initialized) {
            return engine.getRenderProcessProgress(renderId);
        }

        return 0f;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getFrameRate() {
        return frameRate;
    }
}
