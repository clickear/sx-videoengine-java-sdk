package com.seeshiontech.vesdk;

import com.seeshiontech.vesdk.exceptions.InvalidLicenseException;
import com.seeshiontech.vesdk.exceptions.NotSupportedTemplateException;
import com.seeshiontech.vesdk.exceptions.RenderException;


public class VeCutProcessRenderTask {

    /**
     * 源文件路径, gif/ 视频文件
     * */
    private String inputFile;

    /**
     * 图片输出目录绝对路径
     * */
    private String outputFile;

    /**
     * 裁剪类型
     * */
    private CutTaskType type;

    /**
     * 输出宽度 px
     * */
    private int width = 0;

    /**
     * 输出高度 px
     * */
    private int height = 0;

    /**
     * 裁剪开始时间, 秒
     * */
    private float startTime = 0;

    /**
     * 裁剪时长, 秒
     * */
    private float duration = 0;

    /**
     * 矩阵变换锚点 x 轴位置 px
     * */
    private int anchorX = 0;

    /**
     * 矩阵变换锚点 y 轴位置 px
     * */
    private int anchorY = 0;

    /**
     * 矩阵移动位置 x 轴位置 px
     * */
    private int posX = 0;

    /**
     * 矩阵移动位置 y 轴位置 px
     * */
    private int posY = 0;

    /**
     * 缩放比例, 0 - 1
     * */
    private float scale = 1;

    /**
     * 旋转角度
     * */
    private float rotation = 0;

    /**
     * bitrate control
     */
    private float bitrateControl = 0;


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


    public VeCutProcessRenderTask(String inputPath, String outputPath, CutTaskType type) {
        this.inputFile = inputPath;
        this.outputFile = outputPath;
        this.type = type;
    }

    public String getErrorMsg() {
        return errorMsg;
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
        if (engine == null) {
            engine = new VideoEngine();
            renderId = engine.createRenderCutProcess(inputFile, outputFile, type);
            if (renderId.isEmpty()) {
                return false;
            }
            this.initialized = true;
        }

        if (bitrateControl > 0) {
            engine.setRenderCutProcessBitrateControl(renderId, bitrateControl);
        }

        if (startTime < 0) {
            engine.setRenderCutProcessStartTime(renderId, startTime);
        }

        if (duration > 0) {
            engine.setRenderCutProcessDuration(renderId, duration);
        }

        if (width > 0 || height > 0) {
            engine.setRenderCutProcessSize(renderId, width, height);
        }

        if (anchorX > 0|| anchorY > 0 || posX > 0 || posY > 0 || scale != 1.0 || rotation > 0) {
            engine.setRenderCutProcessTransform(renderId, anchorX, anchorY, posX, posY, scale, rotation);
        }

        boolean success = engine.startRenderCutProcess(renderId);

        if (!success)  {
            errorMsg = "start render process failed";
            return false;
        }

        return true;
    }

    /**
     * 设置输出宽高, 单位 px, 默认值： 源文件宽高
     *
     * @param width
     * @param height
     * */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 设置裁剪开始时间，单位: 秒
     * eg : 从第 10 秒开始 ： -10
     *
     * @param startTime,范围 < 0
     * */
    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    /**
     * 设置裁剪时长, 单位: 秒
     *
     * @param duration
     * */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * 设置锚点坐标
     *
     * @param anchorX, 矩阵变换锚点 x 轴位置 px
     * @param anchorY, 矩阵变换锚点 y 轴位置 px
     * */
    public void setAnchor(int anchorX, int anchorY) {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }


    /**
     * 设置移动坐标
     *
     * @param posX, 矩阵移动位置 x 轴位置 px
     * @param posY, 矩阵移动位置 y 轴位置 px
     * */
    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }


    /**
     * 设置缩放比例
     *
     * @param scale, 缩放比例, 0 - 1
     * */
    public void setScale(float scale) {
        this.scale = scale;
    }


    /**
     * 设置旋转角度
     *
     * @param rotation, 旋转角度
     * */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }


    /**
     * 设置视频比特率参数，范围 > 0, 默认值 0.25
     *
     * @param control, 范围 > 0, 默认值 0.25
     * */
    public void setBitrateControl(float control) {
        this.bitrateControl = bitrateControl;
    }

    /**
     * 销毁渲染资源,必须调用
     * */
    public void destroy() {
       engine.destroyRenderCutProcess(renderId);
    }

}
