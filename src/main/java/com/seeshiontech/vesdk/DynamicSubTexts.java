package com.seeshiontech.vesdk;

import com.alibaba.fastjson.annotation.JSONField;

public class DynamicSubTexts {
    @JSONField(name="img_path")
    public String imgPath;
    @JSONField(name="d_key_prefix")
    public String dKeyPrefix;
    @JSONField(name="d_texts")
    public String[] dTexts;

    public DynamicSubTexts() {
    }

    public DynamicSubTexts(String imgPath, String dKeyPrefix, String[] dTexts) {
        this.imgPath = imgPath;
        this.dKeyPrefix = dKeyPrefix;
        this.dTexts = dTexts;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getdKeyPrefix() {
        return dKeyPrefix;
    }

    public void setdKeyPrefix(String dKeyPrefix) {
        this.dKeyPrefix = dKeyPrefix;
    }

    public String[] getdTexts() {
        return dTexts;
    }

    public void setdTexts(String[] dTexts) {
        this.dTexts = dTexts;
    }
}
