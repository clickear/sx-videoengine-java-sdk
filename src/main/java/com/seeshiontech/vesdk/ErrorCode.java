package com.seeshiontech.vesdk;

/**
 * 错误码列表
 */
public enum ErrorCode {

    /**
     * 1, "ok"
     */
    OK(0, "ok"),

    /**
     * -1, "fail"
     */
    ERR(-1, "fail"),

    /**
     * 20002, "template invalid, check template file"
     */
    TEMPLATE_INVALID(20002, "template invalid, check template file"),

    /**
     * 20010, "ffmpeg command run fail, check ffmpeg run env"
     */
    FFMPEG_RUN_FAIL(20010, "ffmpeg command run fail, check ffmpeg run env"),

    /**
     * 20020, "video encoder create error"
     */
    VIDEO_ENCODER_CREATE_FAIL(20020, "video encoder create error"),

    /**
     * 20021, "video encode error"
     */
    VIDEO_ENCODE_FAIL(20021, "video encode error"),

    /**
     * 20030, "invalid license"
     */
    LICENSE_INVALID(20030, "invalid license"),

    /**
     * 20031, "license not fullly support render task"
     */
    LICENSE_NOT_FULLY_SUPPORT(20031, "license not fully support render task"),

    /**
     * 20040, "fork render process error, check render log"
     * */
    FORK_ERROR(20040, "fork render process error, check render log"),

    /**
     * 20050, "invalid watermarks data"
     * */
    INVALID_WATERMARKS_DATA(20050, "invalid watermarks data"),

    /**
     * 20051, "invalid sub imgs data"
     * */
    INVALID_SUB_IMGS_DATA(20051, "invalid sub imgs data"),

    /**
     * 20052, "invalid sub texts data"
     * */
    INVALID_SUB_TEXTS_DATA(20052, "invalid sub texts data"),

    /**
     * 20053, "invalid snapshots data"
     * */
    INVALID_SNAPSHOTS_DATA(20053, "invalid snapshots data"),

    /**
     * 20054, "invalid assets file data"
     * */
    INVALID_ASSETS_DATA(20054, "invalid assets file data");


    private int errCode;
    private String errMsg;


    ErrorCode(int code, String msg) {
        errCode = code;
        errMsg = msg;
    }


    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}