package com.seeshiontech.vesdk;

/**
 * 音轨信息
 *
 * @author slayer
 * */

public class AudioTrack {

    /**
     * 包含音轨的文件路径，比如 mp3 文件
     * */
    private String audioPath = "";

    /**
     * 音轨插入时间点, 单位：秒
     * */
    private float inPoint = 0;

    /**
     * 音轨持续时间, 单位：秒
     * */
    private float duration = 0;

    /**
     * 音轨截取开始时间点， 单位：秒
     * */
    private float startTime = 0;

    /**
     * 音轨截取结束时间点， 单位：秒
     * */
    private float endTime = 0;

    /**
     * 当音轨长度小于目标持续时间时是否循环
     * 默认只播放一遍
     * */
    private boolean loop = false;

    /**
     * 音量控制
     * */
    private float volume = 1.0f;

    public AudioTrack() {
    }

    public AudioTrack(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public float getInPoint() {
        return inPoint;
    }

    public void setInPoint(float inPoint) {
        this.inPoint = inPoint;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
