package com.seeshiontech.vesdk;

import com.alibaba.fastjson.annotation.JSONField;

public class DynamicSubFiles {
    @JSONField(name="img_path")
    public String imgPath;
    @JSONField(name="d_key_prefix")
    public String dKeyPrefix;
    @JSONField(name="d_img_paths")
    public String[] dImgPaths;

    public DynamicSubFiles() {
    }

    public DynamicSubFiles(String imgPath, String dKeyPrefix, String[] dImgPaths) {
        this.imgPath = imgPath;
        this.dKeyPrefix = dKeyPrefix;
        this.dImgPaths = dImgPaths;
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

    public String[] getdImgPaths() {
        return dImgPaths;
    }

    public void setdImgPaths(String[] dImgPaths) {
        this.dImgPaths = dImgPaths;
    }
}
