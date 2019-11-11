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
     * 替换素材 json
     * */
    private String replaceableJson;

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
     * 模板类型 {@link TemplateType}
     *
     * 普通模板
     * 动态模板
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
     * <p>
     *     非动态模板设置无效
     * </p>
     * */
    private String subImgJson;

    /**
     * 动态模板附加文字数据, json 字符串
     *
     * */
    private String subTextJson;


    /**
     * 引擎素材目录存放路径
     *
     *<p>
     * 文字绘制生成的图片将会保存到该目录, 该目录需要调用方进行清理工作
     *</p>
     *
     * */
    private String assetPath;


    /**
     * 文字绘制工具目录
     *
     * @note 如果要时候用文字绘制功能,必须设置
     *
     * */
    private String textPainterPath;

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
     * 设置是否对动态模板中的视频替换素材自适应
     * */
//    private boolean dynamicAdaptVideo = false;

    /**
     * 开启素材缓存管理器
     * */
    private  boolean enalbeSourceManager = false;

    /**
     * 素材管理器缓存大小,单位 m
     * */
    private int sourceManagerCacheSize = 300;


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

    public boolean isMusicLoop() {
        return musicLoop;
    }

    /**
     * 设置音乐是否循环
     *
     * @param musicLoop
     * */
    public void setMusicLoop(boolean musicLoop) {
        this.musicLoop = musicLoop;
    }


    /**
     * 设置模板类型
     *
     * @param type
     * */
    public void setTemplateType(TemplateType type) {
        this.templateType = type;
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
     * 设置替换素材路径
     *
     * @param paths
     * */
    public void setAssetPaths(String[] paths) {
        this.assetPaths = paths;
    }

    /**
     * 设置替换素材 json
     *
     * 参考 http://www.seeshiontech.com/docs/page_103.html
     *
     * @param json
     * */
    public void setReplaceableJson(String json) {
        this.replaceableJson = json;
    }

    /**
     * 设置音乐文件
     *
     * @param musicPath
     * @param loopMusic
     *
     * */
    public void  setMusicPath(String musicPath, boolean loopMusic) {
        this.musicPath = musicPath;
        this.musicLoop = loopMusic;
    }


    /**
     * 设置视频比特率控制参数,默认 0.25
     *
     * @param control
     * */
    public void setBitrateControl(float control) {
        this.bitrateControl = control;
    }


    public List<Watermark> getWatermarkList() {
        return watermarkList;
    }

    /**
     * 设置水印
     *
     * @param watermarkList
     *
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
     * @param musicFadeoutDuration
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
     * 0 - 1.0, 输出音量为原音量 * musicVolume
     *
     * @param musicVolume
     * */
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }


    /**
     * 设置动态模板附加素材
     *
     * @param  json, 附加素材数据, json 字符串
     * */
    public void setDynamicSubFiles(String json) {
        this.subImgJson = json;
    }


    /**
     * 为动态模板设置关联的附加文字
     *
     * <p>
     * 1. 当前文字是由 TextPainter 绘制,使用这个接口, 必须先设置好 assetPath 和 textpianter path <br>
     * 2. 非动态模板设置无效
     * </p>
     *
     * @param subTextJson, 文字素材数组
     * */
    public void setDynamicSubTexts(String subTextJson) {
        this.subTextJson = subTextJson;
    }

    /**
     * 设置引擎生成的素材存放目录,
     *
     * <p>
     * 1. TextPainter 绘制的文字图片会被放到设置的目录, 引擎不会对该目录执行清理动作,
     * 需要调用方在渲染完成后,删除该目录进行清理 <br>
     * 2.  由于生成的素材可能与别的任务重名,所以建议每个任务使用单独的素材目录
     * </p>
     *
     * @param assetPath, 素材存放目录
     * */
    public void setAssetDir(String assetPath) {
        this.assetPath = assetPath;
    }


    /**
     * 设置文字绘制工具目录
     *
     * <p>
     * 1. 引擎使用该目录的 TextPainter 和 font_list.json 进行文字绘制
     * </p>
     *
     * @param textPainterPath, 文字绘制工具目录
     * */
    public void setTextPainterDir(String textPainterPath) {
        this.textPainterPath = textPainterPath;
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
            errorMsg = "task not initialized";
            return false;
        }


        if (this.assetPaths != null && this.assetPaths.length > 0) {
            boolean set = engine.setRenderProcessReplaceableFiles(renderId, assetPaths);
            if (!set) {
                errorCode = ErrorCode.INVALID_ASSETS_DATA.getErrCode();
                errorMsg = ErrorCode.INVALID_ASSETS_DATA.getErrMsg();
                return false;
            }
        }

        if (this.replaceableJson != null && this.replaceableJson.length() > 0) {
            boolean set = engine.setRenderProcessReplaceableJson(renderId, replaceableJson);
            if (!set) {
                errorCode = ErrorCode.INVALID_ASSETS_DATA.getErrCode();
                errorMsg = ErrorCode.INVALID_ASSETS_DATA.getErrMsg();
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

        if (assetPath != null) {
            engine.setRenderProcessAssetPath(renderId, assetPath);
        }

        if (subTextJson != null) {
            engine.setRenderProcessDynamicSubTexts(renderId, subTextJson);
        }

        if (textPainterPath != null) {
            engine.setRenderProcessTextPainterPath(renderId, textPainterPath);
        }

        if (snapShotPath!= null && snapShotFrames != null && !snapShotPath.isEmpty() && snapShotFrames.length > 0 ) {
            engine.setRenderProcessSnapShotPath(renderId, snapShotPath);
            engine.setRenderProcessSnapShotFrames(renderId, snapShotFrames);
        }

        engine.setRenderProcessBitrateControl(renderId, bitrateControl);
        engine.setRenderProcessMusicLoop(renderId, this.musicLoop);

        if (retainAudioOfVideo) {
            engine.setRenderProcessRetainAudioOfVideo(renderId, retainAudioOfVideo);
        }

        if (enalbeSourceManager) {
            engine.enableRenderProcessSourceManager(renderId, enalbeSourceManager);
            engine.setRenderProcessSourceManagerCacheSize(renderId, sourceManagerCacheSize);
        }

        errorCode = engine.nStartRenderProcess(renderId);

        if (errorCode != ErrorCode.OK.getErrCode())  {
            errorMsg = "start render process failed, error code : " + errorCode;
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
     * 开启素材缓存管理
     *
     * @param enable, true or false
     * */
    public void setEnableSourceManager(boolean enable) {
        this.enalbeSourceManager = enable;
    }


    /**
     * 设置素材管理器缓存大小，单位 M
     *
     * @param size, 默认 300
     * */
    public void setSourceManagerCacheSize(int size) {
        this.sourceManagerCacheSize = size;
    }


    /**
     * 是否保留视频素材的音频,
     *
     * @param retainAudioOfVideo, true or false， 默认: false
     * */
    public void setRetainAudioOfVideo(boolean retainAudioOfVideo) {
        this.retainAudioOfVideo = retainAudioOfVideo;
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
}
