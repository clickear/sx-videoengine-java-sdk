package com.seeshiontech.vesdk;

import java.util.List;

public class Watermark {

    /**
     * 水印路径数组
     * */
    private List<String> paths;

    /**
     * 以左上角顶点为原点
     *
     * x 轴位置
     * */
    private float posX = 0;

    /**
     * 以左上角顶点为原点
     *
     * y 轴位置
     * */
    private float poxY = 0;

    /**
     *  开始时间, 单位秒
     *  默认 0
     * */
    private float timeStart = 0;

    /**
     * 结束时间
     * 默认 0 表示一直播放到视频结束
     * */
    private float timeEnd = 0;

    /**
     * x 轴方向缩放
     * */
    private float scaleX = 1;

    /**
     * y 轴方向缩放
     * */
    private float scaleY = 1;


    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPoxY() {
        return poxY;
    }

    public void setPoxY(float poxY) {
        this.poxY = poxY;
    }

    public float getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(float timeStart) {
        this.timeStart = timeStart;
    }

    public float getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(float timeEnd) {
        this.timeEnd = timeEnd;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }
}
