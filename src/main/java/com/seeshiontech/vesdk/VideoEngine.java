package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;

public class VideoEngine {

    /**
     * 渲染 jni 接口
     * */

    private native String nCreateRenderProcess(String tplFolder, String outputPath, int key);
    private native boolean nRegisterRenderProcessLicense(String id, String licenseStr);
    private native String nRenderProcessLicenseProfile(String id);
    private native boolean nRenderProcessLicenseIsValid(String id);
    private native int nRenderProcessSetReplaceableFiles(String id, String[] paths);
    private native int nRenderProcessSetDynamicSubFiles(String id, String json);
    private native int nRenderProcessSetMusicFile(String id, String musicPath, boolean loop);
    private native int nRenderProcessSetMusicLoop(String id, boolean loop);
    private native int nRenderProcessStart(String id)throws InvalidLicenseException, NotSupportedTemplateException, RenderException;
    private native String nRenderProcessStatus(String id);
    private native void nRenderProcessRelease(String id);
    private native int nRenderProcessSetBitrateControl(String id, float control);
    private native int nRenderProcessSetMusicFadeoutDuration(String id, int duration);
    private native int nRenderProcessSetMusicVolume(String id, float volume);
    private native int nRenderProcessAddWatermark(String id, String[] paths, float posX, float posY, float timeStart, float timeEnd, float scaleX, float scaleY);
    private native int nRenderProcessSetScript(String id, String mainFilePath, String scriptDir, String scriptData);
    private native String nRenderProcessRenderedInfo(String id);



    /**********************************************************************************
     *
     * 进程模式渲染接口
     *
     *
     **********************************************************************************/

    /**
     * 创建进程模式渲染对象
     *
     * @param tplFolder, 模板目录
     * @param outputFile, 输出路径
     * @param key 随机数，同一时间保持唯一
     * @return string render id
     * */
    public String createRenderProcess(String tplFolder, String outputFile, int key) {
        return nCreateRenderProcess(tplFolder, outputFile, key);
    }


    /**
     * 注册 license
     *
     * @param id, render id
     * @param licenseStr, 证书字符串
     * @return boolean
     * */
    public boolean registerRenderProcessLicense(String id, String licenseStr) {
        return nRegisterRenderProcessLicense(id, licenseStr);
    }


    /**
     * 获取 License profile
     *
     * @param id render id
     * @return String
     * */
    public String getRenderProcessLicenseProfile(String id) {
       return nRenderProcessLicenseProfile(id);
    }


    /**
     * 检测注册的 License 是否有效
     *
     * @param id, render id
     * @return boolean
     * */
    public boolean isRenderProcessLicenseValid(String id) {
        return nRenderProcessLicenseIsValid(id);
    }


    /**
     * 设置素材
     *
     * @param id, render id
     * @param paths, 素材数组
     * @return boolean
     *
     * */
    public boolean setRenderProcessReplaceableFiles(String id, String[] paths) {
        return nRenderProcessSetReplaceableFiles(id, paths) == 0 ? true : false;
    }

    /**
     * 为动态模板设置关联的附加素材
     *
     * @note 非动态模板设置无效
     *
     * @param id, render id
     * @param json, 素材数组
     * @return boolean
     *
     * */
    public boolean setRenderProcessDynamicSubFiles(String id, String json) {
        return nRenderProcessSetDynamicSubFiles(id, json) == 0 ? true : false;
    }

    /**
     * 设置音乐
     *
     * @param id, render id
     * @param musicPath, 音乐文件路径
     * @param loop 是否循环音乐
     * @return boolean
     * */
    public boolean setRenderProcessMusicFile(String id, String musicPath, boolean loop) {
        return nRenderProcessSetMusicFile(id, musicPath, loop) == 0 ? true : false;
    }

    /**
     * 设置音乐是否循环
     *
     * @param id, render id
     * @param loop 是否循环音乐
     * @return boolean
     * */
    public boolean setRenderProcessMusicLoop(String id, boolean loop) {
        return nRenderProcessSetMusicLoop(id, loop) == 0 ? true : false;
    }


    /**
     * 设置音乐淡出时间, 单位秒
     *
     * @param id, render id
     * @param duration 淡出时间
     * @return boolean
     * */
    public  boolean setRenderProcessMusicFadeoutDuration(String id, int duration) {
        return nRenderProcessSetMusicFadeoutDuration(id, duration) == 0 ? true: false;
    }

    /**
     * 设置音量控制参数
     *
     * @param id, render id
     * @param volume
     * @return boolean
     * */
    public boolean setRenderProcessMusicVolume(String id, float volume) {
        return nRenderProcessSetMusicVolume(id, volume) == 0 ? true : false;
    }

    /**
     * 添加水印
     *
     * @param id, render id
     * @param paths
     * @param posX 水印 x 坐标
     * @param posY 水印 y 坐标
     * @param timeStart 开始时间,单位秒
     * @param timeEnd 结束时间,单位秒
     * @param scaleX  x 轴缩放
     * @param scaleY  y 轴缩放
     * @return boolean
     * */
    public boolean addRenderProcessWatermark(String id, String[] paths, float posX, float posY, float timeStart, float timeEnd, float scaleX, float scaleY) {
        return nRenderProcessAddWatermark(id, paths, posX, posY, timeStart, timeEnd, scaleX, scaleY) == 0 ? true : false;
    }

    /**
     * 启动渲染
     *
     * @param id, render id
     * @return boolean
     * */
    public boolean startRenderProcess(String id) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
        return nRenderProcessStart(id) == 0 ? true : false;
    }


    /**
     * 获取渲染后的状态
     *
     * @param id, render id
     * @return String
     * */
    public String getRenderProcessStatus(String id) {
        return nRenderProcessStatus(id);
    }

    /**
     * 销毁渲染对象
     *
     * @param id, render id
     * */
    public void destroyRenderProcess(String id) {
        nRenderProcessRelease(id);
    }

    /**
     * 设置比特率控制参数
     *
     * @param id, render id
     * @param control 0.0 - 1.0
     * @return boolean
     * */
    public boolean setRenderProcessBitrateControl(String id, float control) {
        return nRenderProcessSetBitrateControl(id, control) == 0 ? true : false;
    }


    /**
     * 设置渲染任务 Lua 脚本
     *
     * @param id, render id
     * @param mainFile lua main 函数所在文件路径
     * @param scriptDir lua 文件查找目录, 没有传空字符串
     * @param data 传给脚本的数据, 没有传空字符串
     * @return boolean
     * */
    public boolean setRenderProcessScript(String id, String mainFile, String scriptDir, String data) {
        return nRenderProcessSetScript(id, mainFile, scriptDir, data) == 0;
    }

    /**
     * 获取渲染后的信息
     *
     * @note 在 startRenderProcess 后调用
     *
     * @param id render id
     * @return String
     * */
    public String getRenderProcessRenderedInfo(String id) {
        return nRenderProcessRenderedInfo(id);
    }



    static {
        System.loadLibrary("SXVideoEngineJni");
    }
}
