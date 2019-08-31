package com.seeshiontech.vesdk;

public enum LogLevel {
    LOG_TRACE(1),
    LOG_DEBUG(2),
    LOG_INFO(3),
    LOG_WARN(4),
    LOG_ERROR(5),
    LOG_FATAL(6);

    private  int value;

    LogLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
