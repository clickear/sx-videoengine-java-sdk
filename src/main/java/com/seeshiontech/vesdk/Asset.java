package com.seeshiontech.vesdk;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 替换素材实体类
 *
 * */

public class Asset {


    /**
     * 主替换素材
     * */
    @JSONField(name="main_file")
    private String mainFile;

    /**
     * 高级替换素材
     * */
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


    /**
     * 构建精确查找替换的高级文字素材
     *
     * @param content, String, 文字内容
     * @param key, String, 精确查找的 key
     * @return ReplaceAsset
     * */
    public ReplaceAsset buildTextAsset(String content, String key) {
        ReplaceAsset asset = new ReplaceAsset(2);
        asset.setText(content);
        if (key != null || key.length() > 0) {
            asset.setdKey(key);
        }
        return asset;
    }

    /**
     * 构建前缀查找替换的高级文字素材
     *
     * @param content, String, 文字内容
     * @param key, String, 前缀查找 key 的前缀
     * @return ReplaceAsset
     * */
    public ReplaceAsset buildPrefixTextAsset(String content, String key) {
        ReplaceAsset asset = new ReplaceAsset(2);
        asset.setText(content);
        if (key != null || key.length() > 0) {
            asset.setdKeyPrefix(key);
        }
        return asset;
    }


    /**
     * 构建精确查找的高级图片素材
     *
     * @param filePath, String, 文件路径
     * @param key, String 精确查找的 key
     * @return ReplaceAsset
     * */
    public ReplaceAsset buildFileAsset(String filePath, String key) {
        ReplaceAsset asset = new ReplaceAsset(1);
        asset.setFile(filePath);
        if (key != null || key.length() > 0) {
            asset.setdKey(key);
        }
        return asset;
    }


    /**
     * 构建前缀查找替换的高级图片素材
     *
     *
     * @param filePath, String, 文件路径
     * @param key, String, 前缀查找 key 的前缀
     * @return ReplaceAsset
     * */
    public ReplaceAsset buildPrefixFileAsset(String filePath, String key) {
        ReplaceAsset asset = new ReplaceAsset(1);
        asset.setFile(filePath);
        if (key != null || key.length() > 0) {
            asset.setdKeyPrefix(key);
        }
        return asset;
    }

    /**
     * 添加一个前缀查找替换的高级文字素材
     *
     * @param text, String 文字内容
     * @param key, String 指定 ui_key 的前缀
     * */
    public void addPrefixTextReplaceAsset(String text, String key) {
        replaces.add(buildPrefixTextAsset(text, key));
    }

    /**
     * 添加一个前缀查找替换的高级文字素材(默认前缀为 dtext)
     *
     * @param text, String 文字内容
     * */
    public void addPrefixTextReplaceAsset(String text) {
        replaces.add(buildPrefixTextAsset(text, ""));
    }

    /**
     * 添加一个精确查找替换的高级文字素材
     *
     * @param text, String 文字内容
     * @param key, String 查找的 key
     * */
    public void addTextReplaceAsset(String text, String key) {
        replaces.add(buildTextAsset(text, key));
    }

    /**
     * 添加一个前缀查找替换的高级图片素材(默认前缀 dtext)
     *
     * @param filePath, String 文件路径
     * */
    public void addPrefixFileReplaceAsset(String filePath) {
        replaces.add(buildPrefixFileAsset(filePath, ""));
    }

    /**
     * 添加一个前缀查找替换的高级图片素材
     *
     * @param filePath, String 文件路径
     * @param key, String 查找的 key 前缀
     * */
    public void addPrefixFileReplaceAsset(String filePath, String key) {
        replaces.add(buildPrefixFileAsset(filePath, key));
    }

    /**
     * 添加一个精确查找替换的高级图片素材
     *
     * @param filePath, String 文件路径
     * @param key, String 查找的 key
     * */
    public void addFileReplaceAsset(String filePath, String key) {
        replaces.add(buildFileAsset(filePath, key));
    }
}
