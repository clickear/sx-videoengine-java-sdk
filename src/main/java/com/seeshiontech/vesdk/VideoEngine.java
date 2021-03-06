package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;

/**
 * 渲染核心库
 **/

public class VideoEngine {

    /**
     * 视频渲染 jni 接口
     */

    private native String nCreateRenderProcess(String tplFolder, String outputPath, int key);

    private static  native void nRenderProcessSetLoggerLevel(int level);

    private native boolean nRegisterRenderProcessLicense(String id, String licenseStr);

    private native String nRenderProcessLicenseProfile(String id);

    private native boolean nRenderProcessLicenseIsValid(String id);

    private native int nRenderProcessSetReplaceableFiles(String id, String[] paths);

    private native int nRenderProcessSetReplaceableJson(String id, String json);

    private native int nRenderProcessSetDynamicSubFiles(String id, String json);

    private native int nRenderProcessSetMusicFile(String id, String musicPath, boolean loop);

    private native int nRenderProcessSetMusicLoop(String id, boolean loop);

    private native int nRenderProcessStart(String id) throws InvalidLicenseException, NotSupportedTemplateException, RenderException;

    private native String nRenderProcessStatus(String id);

    private native void nRenderProcessRelease(String id);

    private native int nRenderProcessSetBitrateControl(String id, float control);

    private native int nRenderProcessSetMusicFadeoutDuration(String id, int duration);

    private native int nRenderProcessSetMusicVolume(String id, float volume);

    private native int nRenderProcessAddWatermark(String id, String[] paths, float posX, float posY, float timeStart, float timeEnd, float scaleX, float scaleY);

    private native int nRenderProcessSetScript(String id, String mainFilePath, String scriptDir, String scriptData);

    private native String nRenderProcessRenderedInfo(String id);

    private native int nRenderProcessSetDynamicSubTexts(String id, String json);

    private native int nRenderProcessSetAssetPath(String id, String path);

    private native int nRenderProcessSetTextPainterPath(String id, String path);

    private native int nRenderProcessSetSnapShotPath(String id, String path);

    private native int nRenderProcessSetSnapShotFrames(String id, int[] frames);

    private native float nRenderProcessProgress(String id);

    private native int nRenderProcessError(String id);

    private native int nRenderProcessSetDynamicAdaptVideo(String id, boolean adapt);

    private native int nRenderProcessSetRetainAudioOfVideo(String id, boolean retain);

    private native String nCreateRenderClipProcess(String outputPath, int key);

    private native int nRenderClipProcessSetParams(String id, int width, int height, float frameRate);

    private static  native void nRenderProcessInitLogger();

    private native int nRenderProcessEnableSourceManager(String id, boolean enable);

    private native int nRenderProcessSetSourceManagerCacheSize(String id, int size);

    private native int nRenderProcessAddAudioTrack(String id, String audioPath, float inPoint, float duration,
                                                   float startTime, float endTime, boolean loop, float volume);



    /**********************************************************************************
     *
     * 进程模式渲染接口
     *
     *
     **********************************************************************************/

    /**
     * 创建进程模式渲染对象
     *
     * @note 为避免文件被覆盖，确保输出路径只有当前任务读写
     *
     * @param tplFolder,  模板目录
     * @param outputFile, 输出路径
     * @param key         随机数，同一时间保持唯一
     * @return string render id
     */
    public String createRenderProcess(String tplFolder, String outputFile, int key) {
        return nCreateRenderProcess(tplFolder, outputFile, key);
    }



    /**
     * 设置全局日志打印级别
     *
     * @param level, see {@link LogLevel}
     * */
    public static void setRenderProcessLoggerLevel(LogLevel level) {
        nRenderProcessSetLoggerLevel(level.getValue());
    }

    /**
     * 注册 license
     *
     * @param id,         render id
     * @param licenseStr, 证书字符串
     * @return boolean
     */
    public boolean registerRenderProcessLicense(String id, String licenseStr) {
        return nRegisterRenderProcessLicense(id, licenseStr);
    }


    /**
     * 获取 License profile
     *
     * @param id render id
     * @return String
     */
    public String getRenderProcessLicenseProfile(String id) {
        return nRenderProcessLicenseProfile(id);
    }


    /**
     * 检测注册的 License 是否有效
     *
     * @param id, render id
     * @return boolean
     */
    public boolean isRenderProcessLicenseValid(String id) {
        return nRenderProcessLicenseIsValid(id);
    }


    /**
     * 设置主替换素材
     *
     * @param id,    render id
     * @param paths, 素材数组
     * @return boolean
     */
    public boolean setRenderProcessReplaceableFiles(String id, String[] paths) {
        return nRenderProcessSetReplaceableFiles(id, paths) == 0;
    }


    /**
     * 设置替换素材信息
     *
     * 参考 http://www.seeshiontech.com/docs/page_103.html
     *
     * @param id,    render id
     * @param json, 素材信息
     * @return boolean
     */
    public boolean setRenderProcessReplaceableJson(String id, String json) {
        return nRenderProcessSetReplaceableJson(id, json) == 0;
    }


    /**
     * 为动态模板设置关联的附加素材
     *
     * <p>
     *     非动态模板设置无效
     * </p>
     * @param id,   render id
     * @param json, 素材数组
     * @return boolean
     */
    public boolean setRenderProcessDynamicSubFiles(String id, String json) {
        return nRenderProcessSetDynamicSubFiles(id, json) == 0;
    }


    /**
     * 为动态模板设置关联的附加文字
     *
     * <p>
     *     1. 当前文字是由 TextPainter 绘制,使用这个接口, 必须先设置好 assetPath 和 textpainter path <br>
     *     2. 非动态模板设置无效
     * </p>
     *
     * @param id,   render id
     * @param json, 文字素材数组
     * @return boolean
     */
    public boolean setRenderProcessDynamicSubTexts(String id, String json) {
        return nRenderProcessSetDynamicSubTexts(id, json) == 0;
    }


    /**
     * 设置引擎生成的素材存放目录
     *
     * <p>
     * 1. TextPainter 绘制的文字图片会被放到设置 AssetPath目录, 引擎不会对该目录执行清理动作,
     * 需要调用方在渲染完成后,删除该目录进行清理 <br>
     * 2. 由于生成的素材可能与其他任务的图片重名, 确保每个任务使用单独的素材目录
     * </p>
     *
     * @param id,   render id
     * @param path, 素材存放目录
     * @return boolean
     */
    public boolean setRenderProcessAssetPath(String id, String path) {
        return nRenderProcessSetAssetPath(id, path) == 0;
    }


    /**
     * 设置文字绘制工具目录
     *
     * <p>
     * 引擎会使用该目录的 TextPainter 和 font_list.json 进行文字绘制
     * </p>
     *
     * @param id,   render id
     * @param path, 文字绘制工具目录
     * @return boolean
     */
    public boolean setRenderProcessTextPainterPath(String id, String path) {
        return nRenderProcessSetTextPainterPath(id, path) == 0;
    }


    /**
     * 设置快照存储目录,确保一个目录同一时间只有一个任务使用
     *
     * <p>
     * 引擎将使用该目录保存快照
     * </p>
     *
     * @param id,   render id
     * @param path, 快照目录
     * @return boolean
     */
    public boolean setRenderProcessSnapShotPath(String id, String path) {
        return nRenderProcessSetSnapShotPath(id, path) == 0;
    }


    /**
     * 设置快照帧索引
     * <p>
     * [1, 100] 表示第1, 100 帧会被生成 1.png , 100.png 到 snapshot path 目录中
     * </p>
     *
     * @param id,    render id
     * @param frames
     * @return boolean
     */
    public boolean setRenderProcessSnapShotFrames(String id, int[] frames) {
        return nRenderProcessSetSnapShotFrames(id, frames) == 0;
    }


    /**
     * 设置音乐
     *
     * @param id,        render id
     * @param musicPath, 音乐文件路径
     * @param loop       是否循环音乐
     * @return boolean
     */
    public boolean setRenderProcessMusicFile(String id, String musicPath, boolean loop) {
        return nRenderProcessSetMusicFile(id, musicPath, loop) == 0;
    }


    /**
     * 设置音乐是否循环
     *
     * @param id,  render id
     * @param loop 是否循环音乐
     * @return boolean
     */
    public boolean setRenderProcessMusicLoop(String id, boolean loop) {
        return nRenderProcessSetMusicLoop(id, loop) == 0;
    }


    /**
     * 设置音乐淡出时间, 单位秒
     *
     * @param id,      render id
     * @param duration 淡出时间
     * @return boolean
     */
    public boolean setRenderProcessMusicFadeoutDuration(String id, int duration) {
        return nRenderProcessSetMusicFadeoutDuration(id, duration) == 0;
    }


    /**
     * 设置音量控制参数
     *
     * @param id,    render id
     * @param volume
     * @return boolean
     */
    public boolean setRenderProcessMusicVolume(String id, float volume) {
        return nRenderProcessSetMusicVolume(id, volume) == 0;
    }


    /**
     * 添加音轨, 此方法添加的音轨不会替换背景音乐
     *
     * @param id
     * @param audioPath, 包含音频的文件地址，比如　mp3, 带有音轨的　mp4
     * @param inPoint, 音频出现的时间点，单位：秒;
     * 					0: 起始点开始出现
     * @param duration, 音频持续时长，单位：秒;
     * 					0: 持续到视频结束
     * @param startTime, 音频截取开始时间点，单位：秒;
     * 					0: 从音频起始点开始截取
     * @param endTime, 音频截取结束时间点，单位: 秒;
     * 					0 截取到音频末尾
     * @param loop, 音频长度不够是否循环, 默认不循环
     * @param volume, 音量， 大于 0, 默认 1
     */
    public boolean addRenderProcessAudioTrack(String id, String audioPath, float inPoint, float duration,
                                                float startTime, float endTime, boolean loop, float volume) {
       return nRenderProcessAddAudioTrack(id, audioPath, inPoint, duration, startTime, endTime, loop, volume) == 0;
    }


    /**
     * 添加水印
     *
     * @param id,       render id
     * @param paths
     * @param posX      水印 x 坐标
     * @param posY      水印 y 坐标
     * @param timeStart 开始时间,单位秒
     * @param timeEnd   结束时间,单位秒
     * @param scaleX    x 轴缩放
     * @param scaleY    y 轴缩放
     * @return boolean
     */
    public boolean addRenderProcessWatermark(String id, String[] paths, float posX, float posY, float timeStart,
                                             float timeEnd, float scaleX, float scaleY) {
        return nRenderProcessAddWatermark(id, paths, posX, posY, timeStart, timeEnd, scaleX, scaleY) == 0;
    }


    /**
     * 启动渲染, 返回成功或失败
     *
     * @param id, render id
     * @return boolean
     * @throws InvalidLicenseException
     * @throws RenderException
     * @throws NotSupportedTemplateException
     */
    public boolean startRenderProcess(String id) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
        return nRenderProcessStart(id) == 0;
    }


    /**
     * 启动渲染, 返回响应错误码
     *
     * @param id, render id
     * @return int, 错误码参考 {@link ErrorCode}
     * @throws InvalidLicenseException
     * @throws RenderException
     * @throws NotSupportedTemplateException
     */
    public int nStartRenderProcess(String id) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
        return nRenderProcessStart(id);
    }


    /**
     * 获取渲染进度
     *
     * 无论成功失败,最终都会返回 1.0, 范围 0.0 - 1.0
     *
     * @param id, render id
     * @return float
     */
    public float getRenderProcessProgress(String id) {
        return nRenderProcessProgress(id);
    }


    /**
     * 获取渲染错误
     *
     * @param id, render id
     * @return float
     */
    public int getRenderProcessError(String id) {
        return nRenderProcessError(id);
    }


    /**
     * 获取渲染后的状态
     *
     * @param id, render id
     * @return String
     */
    public String getRenderProcessStatus(String id) {
        return nRenderProcessStatus(id);
    }


    /**
     * 销毁渲染对象
     *
     * @param id, render id
     */
    public void destroyRenderProcess(String id) {
        nRenderProcessRelease(id);
    }


    /**
     * 设置比特率控制参数
     *
     * @param id,     render id
     * @param control 0.0 - 1.0
     * @return boolean
     */
    public boolean setRenderProcessBitrateControl(String id, float control) {
        return nRenderProcessSetBitrateControl(id, control) == 0;
    }


    /**
     * 设置渲染任务 Lua 脚本
     *
     * @param id,       render id
     * @param mainFile  lua main 函数所在文件路径
     * @param scriptDir lua 文件查找目录, 没有传空字符串
     * @param data      传给脚本的数据, 没有传空字符串
     * @return boolean
     */
    public boolean setRenderProcessScript(String id, String mainFile, String scriptDir, String data) {
        data = data == null ? "" : data;
        scriptDir = scriptDir == null ? "" : scriptDir;
        return nRenderProcessSetScript(id, mainFile, scriptDir, data) == 0;
    }

    /**
     * 获取渲染后的信息
     *
     * <p>
     *     在 startRenderProcess 后调用
     * </p>
     * @param id render id
     * @return String
     */
    public String getRenderProcessRenderedInfo(String id) {
        return nRenderProcessRenderedInfo(id);
    }


    /**
     * 设置是否保留视频素材中的音频
     *
     * @param id render id
     * @return boolean
     * */
    public boolean setRenderProcessRetainAudioOfVideo(String id, boolean retain) {
        return nRenderProcessSetRetainAudioOfVideo(id, retain) == 0;
    }


    /**
     * 设置是否对动态模板中的视频素材自适应
     *
     * @param id render id
     * @return boolean
     * */
    public boolean setRenderProcessDynamicAdaptVideo(String id, boolean adapt) {
        return nRenderProcessSetDynamicAdaptVideo(id, adapt) == 0;
    }


    /**
     * 开启素材缓存管理器，用于提升渲染速度，默认缓存池大小： 300, 单位: M
     *
     * @param id, render id
     * @param enable, true or false
     * @return boolean
     * */
    public boolean enableRenderProcessSourceManager(String id, boolean enable) {
        return nRenderProcessEnableSourceManager(id, enable) == 0;
    }


    /**
     * 设置素材管理器缓存池大小，默认缓存池大小： 300, 单位: M
     *
     * @param id, render id
     * @param size, 大小， 单位： M
     * @return boolean
     *
     * */
    public boolean setRenderProcessSourceManagerCacheSize(String id, int size) {
        return nRenderProcessSetSourceManagerCacheSize(id, size)  == 0;
    }




    /**********************************************************************************
     *
     * 进程模式剪辑渲染接口
     *
     *
     **********************************************************************************/

    /**
     * 创建进程模式剪辑渲染对象
     *
     * @param outputFile, 输出路径
     * @param key         随机数，同一时间保持唯一
     * @return string render id
     */
    public String createRenderClipProcess(String outputFile, int key) {
        return nCreateRenderClipProcess(outputFile, key);
    }


    /**
     * 设置剪辑的参数
     *
     *
     * @param id,   render id
     * @param width, 宽, px
     * @param height, 高, px
     * @param frameRate, 帧率, fps
     * @return boolean
     */
    public boolean setRenderClipProcessParams(String id, int width, int height, float frameRate) {
        return nRenderClipProcessSetParams(id, width, height, frameRate) == 0;
    }




    /************************************************************************************
     *
     * 图片滤镜渲染 jni 接口
     *
     *
     **************************************************************************************/
    private native String nCreateRenderImageProcess(String[] imagePaths, String outputPath);

    private native boolean nRegisterRenderImageProcessLicense(String id, String licenseStr);

    private native String nRenderImageProcessLicenseProfile(String id);

    private native boolean nRenderImageProcessLicenseIsValid(String id);

    private native int nRenderImageProcessAddFilter(String id, String filterPath);

    private native int nRenderImageProcessStart(String id) throws InvalidLicenseException, NotSupportedTemplateException, RenderException;

    private native void nRenderImageProcessRelease(String id);

    private native int nRenderImageProcessAddWatermark(String id, String[] paths, float posX, float posY, float timeStart, float timeEnd, float scaleX, float scaleY);


    /**
     * 创建图片进程模式渲染对象
     *
     * @param imagePaths, 图片路径数组
     * @param outputPath, 输出路径
     * @return string render id
     */
    public String createRenderImageProcess(String[] imagePaths, String outputPath) {
        return nCreateRenderImageProcess(imagePaths, outputPath);
    }


    /**
     * 销毁图片渲染对象
     *
     * @param id, render id
     */
    public void destroyRenderImageProcess(String id) {
        nRenderImageProcessRelease(id);
    }


    /**
     * 注册图片渲染 license
     *
     * @param id,         render id
     * @param licenseStr, 证书字符串
     * @return boolean
     */
    public boolean registerRenderImageProcessLicense(String id, String licenseStr) {
        return nRegisterRenderImageProcessLicense(id, licenseStr);
    }


    /**
     * 获取图片渲染 License profile
     *
     * @param id render id
     * @return String
     */
    public String getRenderImageProcessLicenseProfile(String id) {
        return nRenderImageProcessLicenseProfile(id);
    }


    /**
     * 检测图片渲染 License 是否有效
     *
     * @param id, render id
     * @return boolean
     */
    public boolean isRenderImageProcessLicenseValid(String id) {
        return nRenderImageProcessLicenseIsValid(id);
    }


    /**
     * 启动图片渲染, 返回成功或失败
     *
     * @param id, render id
     * @return boolean
     * @throws InvalidLicenseException
     * @throws RenderException
     * @throws NotSupportedTemplateException
     */
    public boolean startRenderImageProcess(String id) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
        return nRenderImageProcessStart(id) == 0;
    }

    /**
     * 启动图片渲染, 返回响应错误码
     *
     * @param id, render id
     * @return int, 错误码 {@link ErrorCode}
     * @throws InvalidLicenseException
     * @throws RenderException
     * @throws NotSupportedTemplateException
     */
    public int nStartRenderImageProcess(String id) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
        return nRenderImageProcessStart(id);
    }

    /**
     * 添加图片渲染水印
     *
     * @param id,       render id
     * @param paths
     * @param posX      水印 x 坐标
     * @param posY      水印 y 坐标
     * @param timeStart 开始时间,单位秒
     * @param timeEnd   结束时间,单位秒
     * @param scaleX    x 轴缩放
     * @param scaleY    y 轴缩放
     * @return boolean
     */
    public boolean addRenderImageProcessWatermark(String id, String[] paths, float posX, float posY, float timeStart, float timeEnd, float scaleX, float scaleY) {
        return nRenderImageProcessAddWatermark(id, paths, posX, posY, timeStart, timeEnd, scaleX, scaleY) == 0 ? true : false;
    }


    /**
     * 添加图片渲染滤镜
     *
     * @param id,         render id
     * @param filterPath, 滤镜路径
     * @return boolean
     */
    public boolean addRenderImageProcessFilter(String id, String filterPath) {
        return nRenderImageProcessAddFilter(id, filterPath) == 0;
    }


    /************************************************************************************
     *
     * gif/视频裁剪接口
     *
     *
     **************************************************************************************/

    private native String nCreateRenderCutProcess(String inputPath, String outputPath, int type);
    private native int nRenderCutProcessSetSize(String id, int width, int height);
    private native int nRenderCutProcessSetTransform(String id, int anchorX, int anchorY, int posX, int posY, float scale, float rotation);
    private native int nRenderCutProcessSetStartTime(String id, float startTime);
    private native int nRenderCutProcessSetDuration(String id, float duration);
    private native int nRenderCutProcessSetBitrateControl(String id, float control);
    private native int nRenderCutProcessStart(String id);
    private native int nRenderCutProcessRelease(String id);

    /**
     * 创建裁剪任务进程
     *
     * 裁剪任务使用 gif/视频文件 按照指定参数输出裁剪后的视频文件
     *
     * @param inputPath, 源文件路径，为 gif 或者视频文件
     * @param outputPath, 输出文件路径，以 .mp4 结尾
     * @param type, see {@link CutTaskType}
     * @return String, renderId
     * */
    public String createRenderCutProcess(String inputPath, String outputPath, CutTaskType type) {
       return nCreateRenderCutProcess(inputPath, outputPath, type.getValue()) ;
    }


    /**
     * 设置输出尺寸， 不设置默认使用原始尺寸
     *
     * @param id, renderId
     * @param width, 宽 px
     * @param height, 高 px
     * @return boolean
     * */
    public boolean setRenderCutProcessSize(String id, int width, int height) {
        return nRenderCutProcessSetSize(id, width, height) == 0;
    }


    /**
     * 设置变换参数
     *
     * @param id, renderId
     * @param anchorX, x 轴 锚点位置 px
     * @param anchorY, y 轴 锚点位置 px
     * @param posX, x 轴移动位置 px
     * @param posY, y 轴移动位置 px
     * @param scale, 缩放比例
     * @param rotation, 旋转角度
     * @return boolean
     * */
    public boolean setRenderCutProcessTransform(String id, int anchorX, int anchorY, int posX, int posY, float scale, float rotation) {
        return nRenderCutProcessSetTransform(id, anchorX, anchorY, posX, posY, scale, rotation) == 0;
    }

    /**
     * 设置裁剪开始时间， 秒
     *
     * @param id, renderId
     * @param startTime, 开始时间
     * @return boolean
     *
     * */
    public boolean setRenderCutProcessStartTime(String id, float startTime) {
        return nRenderCutProcessSetStartTime(id, startTime) == 0;
    }


    /**
     * 设置裁剪时长 秒
     *
     * @param id, renderId
     * @param duration, 裁剪时长
     * @return boolean
     *
     * */
    public boolean setRenderCutProcessDuration(String id, float duration) {
        return nRenderCutProcessSetDuration(id, duration) == 0;
    }


    /**
     * 设置视频比特率参数，范围 > 0, 默认值 0.25
     *
     * @param id, renderId
     * @param control, 范围 > 0, 默认值 0.25
     * @return boolean
     *
     * */
    public boolean setRenderCutProcessBitrateControl(String id, float control) {
        return nRenderCutProcessSetBitrateControl(id, control) == 0;
    }

    /**
     * 开始渲染
     *
     * @param id, renderId
     * @return boolean
     * */
    public boolean startRenderCutProcess(String id) {
        return nRenderCutProcessStart(id) == 0;
    }

    /**
     * 释放渲染资源
     *
     * @param id, renderId
     * */
    public void destroyRenderCutProcess(String id) {
        nRenderCutProcessRelease(id);
    }


    static {
        System.loadLibrary("SXVideoEngineJni");
        nRenderProcessInitLogger();
    }
}
