package com.seeshiontech.vesdk;

/**
 * 渲染进程状态
 * */
public enum RenderStatus {


    /**
     * "start", "render start"
     */
    START("start", "render start"),

    /**
     * "end", "render finished"
     */
    END("end", "render finished"),

    /**
     * "update", "render update, is running"
     */
    UPDATE("update", "render update, is running"),

    /**
     * "cancel", "render canceled, check log"
     */
    CANCEL("cancel", "render canceled, check log"),

    /**
     * "fail", "render failed, check log"
     */
    FAIL("fail", "render failed, check log"),

    /**
     * "crash", "render crashed, check log"
     */
    CRASH("crash", "render crashed, check log"),

    ;

    private String status;
    private String msg;

    RenderStatus(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
