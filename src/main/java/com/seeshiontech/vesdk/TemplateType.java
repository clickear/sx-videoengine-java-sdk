package com.seeshiontech.vesdk;

public enum TemplateType {
    NORMAL_TEMPLATE(1), DYNAMIC_TEMPLATE(2);

    private  int value;

    TemplateType(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
