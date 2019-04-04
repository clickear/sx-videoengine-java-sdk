package com.seeshion;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;

public class VideoEngine {

    /**
     * 进程模式
     * */
    private native String nVeCreateRenderProcess(String tplFolder, String outputPath, int key) throws InvalidLicenseException, NotSupportedTemplateException, RenderException;
    private native int nVeRenderProcessSetReplaceableFiles(String id, String[] paths);
    private native int nVeRenderProcessStart(String id);
    private native String nVeRenderProcessStatus(String id);
    private native void nVeRenderProcessRelease(String id);

    private native String nCreateRenderProcess(String tplFolder, String outputPath, int key);
    private native boolean nRegisterRenderProcessLicense(String id, String licenseStr);
    private native String nRenderProcessLicenseProfile(String id);
    private native boolean nRenderProcessLicenseIsValid(String id);
    private native int nRenderProcessSetReplaceableFiles(String id, String[] paths);
    private native int nRenderProcessSetMusicFile(String id, String musicPath, boolean loop);
    private native int nRenderProcessStart(String id)throws InvalidLicenseException, NotSupportedTemplateException, RenderException;
    private native String nRenderProcessStatus(String id);
    private native void nRenderProcessRelease(String id);

    /**
     * 线程模式
     * */
    private native long nCreateRender(String tplFolder, String outputPath) throws InvalidLicenseException, NotSupportedTemplateException, RenderException;
    private native boolean nSetReplaceableFilePaths(long renderId, String paths[]);
    private native boolean nSetFileForAsset(long renderId, String filekey, String filePath);
    private native boolean nStartRender(long renderId);
    private native float nGetRenderProgress(long renderId);
    private native boolean nDestroyRender(long renderId);


    public String createRenderProcess(String tplFolder, String outputFile, int key) {
        return nCreateRenderProcess(tplFolder, outputFile, key);
    }
    public boolean registerRenderProcessLicense(String id, String licenseStr) {
        return nRegisterRenderProcessLicense(id, licenseStr);
    }
    public String getRenderProcessLicenseProfile(String id) {
       return nRenderProcessLicenseProfile(id);
    }
    public boolean isRenderProcessLicenseValid(String id) {
        return nRenderProcessLicenseIsValid(id);
    }
    public boolean setRenderProcessReplaceableFiles(String id, String[] paths) {
        return nRenderProcessSetReplaceableFiles(id, paths) == 0 ? true : false;
    }

    public boolean setRenderProcessMusicFile(String id, String musicPath, boolean loop) {
        return nRenderProcessSetMusicFile(id, musicPath, loop) == 0 ? true : false;
    }
    public boolean startRenderProcess(String id) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
        return nRenderProcessStart(id) == 0 ? true : false;
    }

    public String getRenderProcessStatus(String id) {
        return nRenderProcessStatus(id);
    }

    public void destroyRenderProcess(String id) {
        nRenderProcessRelease(id);
    }


    /**
     * 创建进程模式渲染对象
     *
     * @param tplFolder, 模板目录
     * @param outputPath, 输出路径
     * @param key 随机数，同一时间保持唯一
     * @return string render id
     * */
    public String pCreateRender(String tplFolder, String outputPath, int key) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
       return this.nVeCreateRenderProcess(tplFolder, outputPath, key) ;
    }


    /**
     * 设置素材
     *
     * @param id, render id
     * @param paths, 素材数组
     * @return boolean
     *
     * */
    public boolean pChangeAssetPaths(String id, String paths[]) {
        return this.nVeRenderProcessSetReplaceableFiles(id, paths) == 0 ? true : false;
    }

    /**
     * 开始渲染
     *
     * @param id, render id
     * @return true
     * */
    public boolean pStartRender(String id)  {
        return this.nVeRenderProcessStart(id) == 0 ? true : false;
    }


    /**
     * 获取渲染状态
     *
     * @param id, render id
     * @return string
     *
     * */
    public String pGetRenderStatus(String id) {
        return this.nVeRenderProcessStatus(id);
    }

    /**
     * 释放对象
     *
     * @param id, render id
     * */
    public void pDestroyRender(String id) {
        this.nVeRenderProcessRelease(id);
    }


    /**
     *  证书相关
     * */
    private native boolean nRegisterLicense(String licenseStr);
    private native boolean nIsLicenseValid();
    private native String nGetLicenseProfile();

    public native void nThrowException() throws InvalidLicenseException, NotSupportedTemplateException;

    public void testException() {
        try {
            nThrowException();
        } catch (InvalidLicenseException e) {
            e.printStackTrace();
        } catch (NotSupportedTemplateException e) {
            e.printStackTrace();
        }
    }



    /**
     * 注册 License
     *
     * @param licenseStr
     * @return boolean
     *
     * */
    public boolean registerLicense(String licenseStr) {
        return nRegisterLicense(licenseStr);
    }


    /**
     * 检查 license 是否有效
     *
     * @return boolean
     * */
    public boolean isLicenseValid() {
        return nIsLicenseValid();
    }


    /**
     * 获取 License string profile
     *
     * @return string
     * */
    public String getLicenseProfile() {
        return nGetLicenseProfile();
    }


    /**
     * 创建 Render 对象，成功会返回一个大于0的整数
     *
     * @param  tplFolder, 模板路径
     * @param outputPath, 输出文件路径
     * @return long, render 对象id
     * */
    public long createRender(String tplFolder, String outputPath) throws RenderException, NotSupportedTemplateException, InvalidLicenseException {
        return this.nCreateRender(tplFolder, outputPath);
    }


    /**
     * 批量设置素材路径
     *
     * @param renderId
     * @param paths, 素材路径数组
     * @return boolean
     * */
    public boolean changeAssetPaths(long renderId, String paths[]) {
       return this.nSetReplaceableFilePaths(renderId, paths);
    }


    /**
     * 替换素材路径
     *
     * @param renderId
     * @param fileKey, 素材 key
     * @param filePath, 素材路径
     * @return boolean
     * */
    public boolean replaceAsset(long renderId, String fileKey, String filePath) {
        return this.nSetFileForAsset(renderId, fileKey, filePath);
    }



    /**
     * 释放 render 对象
     *
     * @param renderId
     * @return boolean
     * */
    public boolean destroyRender(long renderId) {
        return this.nDestroyRender(renderId);
    }


    /**
     * 启动渲染
     *
     * @Note 阻塞方法，请在另外一个线程启动
     *
     * @param  renderId
     * @return boolean
     **/
    public boolean startRender(long renderId) {
        return this.nStartRender(renderId);
    }


    /**
     *
     * */
    public boolean startRender(long renderId, boolean isRelayAlbum) {
        return this.nStartRender(renderId);
    }




    /**
     * 获取渲染进度
     *
     * @param renderId
     * @return float, 进度，范围： 0.0 - 1.0
     * */
    public float getRenderProgress(long renderId)  {
        return this.nGetRenderProgress(renderId);
    }


    static {
        System.loadLibrary("SXVideoEngineJni");
    }
}
