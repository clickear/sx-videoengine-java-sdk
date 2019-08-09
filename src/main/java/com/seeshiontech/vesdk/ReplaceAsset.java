package com.seeshiontech.vesdk;


import com.alibaba.fastjson.annotation.JSONField;

public class ReplaceAsset {
    private String text;
    private int type;
    private String file;
    @JSONField(name="d_key")
    private String dKey;
    @JSONField(name="d_key_prefix")
    private String dKeyPrefix;

    public String getdKey() {
        return dKey;
    }

    public void setdKey(String dKey) {
        this.dKey = dKey;
    }

    public String getdKeyPrefix() {
        return dKeyPrefix;
    }

    public void setdKeyPrefix(String dKeyPrefix) {
        this.dKeyPrefix = dKeyPrefix;
    }

    public ReplaceAsset() {
   }

    public ReplaceAsset(int type) {
        this.type = type;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
