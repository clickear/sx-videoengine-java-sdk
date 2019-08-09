package com.seeshiontech.vesdk;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class Asset {

    @JSONField(name="main_file")
    private String mainFile;
    private List<ReplaceAsset> replaces = new ArrayList<>();

    public Asset() {
    }

    public Asset(String mainFile) {
        this.mainFile = mainFile;
    }

    public Asset(String mainFile, List<ReplaceAsset> replaces) {
        this.mainFile = mainFile;
        this.replaces = replaces;
    }

    public Asset(List<ReplaceAsset> replaces) {
        this.replaces = replaces;
    }

    public String getMainFile() {
        return mainFile;
    }

    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }

    public List<ReplaceAsset> getReplaces() {
        return replaces;
    }

    public void setReplaces(List<ReplaceAsset> replaces) {
        this.replaces = replaces;
    }

    public void addReplaceAsset(ReplaceAsset replaceAsset) {
       replaces.add(replaceAsset);
    }

    public ReplaceAsset buildTextAsset(String content, String key) {
        ReplaceAsset asset = new ReplaceAsset(2);
        asset.setText(content);
        if (key != null || key.length() > 0) {
            asset.setdKey(key);
        }
        return asset;
    }

    public ReplaceAsset buildPrefixTextAsset(String content, String key) {
        ReplaceAsset asset = new ReplaceAsset(2);
        asset.setText(content);
        if (key != null || key.length() > 0) {
            asset.setdKeyPrefix(key);
        }
        return asset;
    }

    public ReplaceAsset buildFileAsset(String filePath, String key) {
        ReplaceAsset asset = new ReplaceAsset(1);
        asset.setFile(filePath);
        if (key != null || key.length() > 0) {
            asset.setdKey(key);
        }
        return asset;
    }

    public ReplaceAsset buildPrefixFileAsset(String filePath, String key) {
        ReplaceAsset asset = new ReplaceAsset(1);
        asset.setFile(filePath);
        if (key != null || key.length() > 0) {
            asset.setdKeyPrefix(key);
        }
        return asset;
    }

    public void addPrefixTextReplaceAsset(String text, String key) {
        replaces.add(buildPrefixTextAsset(text, key));
    }
    public void addPrefixTextReplaceAsset(String text) {
        replaces.add(buildPrefixTextAsset(text, ""));
    }

    public void addTextReplaceAsset(String text, String key) {
        replaces.add(buildTextAsset(text, key));
    }

    public void addPrefixFileReplaceAsset(String filePath) {
        replaces.add(buildPrefixFileAsset(filePath, ""));
    }

    public void addPrefixFileReplaceAsset(String filePath, String key) {
        replaces.add(buildPrefixFileAsset(filePath, key));
    }
}
