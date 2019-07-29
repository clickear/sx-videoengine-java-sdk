package com.seeshiontech.vesdk;

public enum TemplateType {
    NORMAL_TEMPLATE(1), DYNAMIC_TEMPLATE(2), FILTER(4), CUSTOM(8), IMAGE_FILTER(16), VIDEO_CLIP(32);

    private  int value;

    TemplateType(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
