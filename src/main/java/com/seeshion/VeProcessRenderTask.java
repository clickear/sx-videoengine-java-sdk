package com.seeshion;

import com.seeshion.exceptions.InvalidLicenseException;
import com.seeshion.exceptions.NotSupportedTemplateException;
import com.seeshion.exceptions.RenderException;

import java.util.Random;

public class VeProcessRenderTask {

    private String license;
    private String tplFolder;
    private String outputPath;
    private String[] assetPaths;
    private VideoEngine engine;
    private String renderId;
    private String errorMsg = "";
    private TemplateType templateType = TemplateType.NORMAL_TEMPLATE;
    private boolean initialized = false;
    private String musicPath;
    private boolean loopMusic = false;


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

    public boolean setMusicFile(String musicPath, boolean loopMusic) {
        this.musicPath = musicPath;
        this.loopMusic = loopMusic;
        return true;
    }

    public boolean render() throws InvalidLicenseException, RenderException, NotSupportedTemplateException {
        if (!initialized) {
            errorMsg = "task not initialized";
            return false;
        }
        if (this.assetPaths.length > 0) {
            boolean set = engine.setRenderProcessReplaceableFiles(renderId, this.assetPaths);
            if (!set) {
                errorMsg = "set task asset paths failed";
                return false;
            }
        }

        if (this.musicPath.length() > 0) {
            boolean set = engine.setRenderProcessMusicFile(renderId, this.musicPath, this.loopMusic);
            if (!set) {
                errorMsg = "set task music paths failed";
                return false;
            }
        }

        boolean success = engine.startRenderProcess(renderId);
        if (!success)  {
            errorMsg = "start render process failed";
            return false;
        }

        String taskStatus = engine.getRenderProcessStatus(renderId);
        if (taskStatus.equals("end")) {
            return true;
        }

        errorMsg = "task exit with status : " + taskStatus;
        return false;
    }

    public void destroy() {
       engine.destroyRenderProcess(renderId);
    }

}
