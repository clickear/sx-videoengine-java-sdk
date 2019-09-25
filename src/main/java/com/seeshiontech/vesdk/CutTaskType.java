package com.seeshiontech.vesdk;

public enum CutTaskType {
    TASK_CUT_GIF(1), TASK_CUT_VIDEO(2);
    private int value;
    CutTaskType(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
