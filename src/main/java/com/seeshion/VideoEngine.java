package com.seeshion;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;

public class VideoEngine {

    /**
     * VIDEO Render native methods
     * */
    private native long nCreateRender(String tplFolder, String outputPath) throws InvalidLicenseException, NotSupportedTemplateException, RenderException;
    private native boolean nSetReplaceableFilePaths(long renderId, String paths[]);
    private native boolean nSetFileForAsset(long renderId, String filekey, String filePath);
    private native boolean nStartRender(long renderId);
    private native float nGetRenderProgress(long renderId);
    private native boolean nDestroyRender(long renderId);

    /**
     * video render process native methods
     *
     * */
    private native String nVeCreateRenderProcess(String tplFolder, String outputPath, int key);
    private native int nVeRenderProcessSetReplaceableFiles(String id, String[] paths);
    private native int nVeRenderProcessStart(String id, boolean isRelayAlbum);
    private native String nVeRenderProcessStatus(String id);
    private native void nVeRenderProcessRelease(String id);


    public String pCreateRender(String tplFolder, String outputPath, int key) {
       return this.nVeCreateRenderProcess(tplFolder, outputPath, key) ;
    }

    public int pChangeAssetPaths(String id, String paths[]) {
        return this.nVeRenderProcessSetReplaceableFiles(id, paths);
    }

    public int pStartRender(String id)  {
        return this.nVeRenderProcessStart(id, false);
    }

    public int pStartRender(String id, boolean isRelayAlbum)  {
        return this.nVeRenderProcessStart(id, isRelayAlbum);
    }

    public String pGetRenderStatus(String id) {
        return this.nVeRenderProcessStatus(id);
    }

    public void pDestroyRender(String id) {
        this.nVeRenderProcessRelease(id);
    }


    /**
     * license register / check
     *
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
