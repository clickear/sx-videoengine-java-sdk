package com.seeshiontech.vesdk;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 高级替换素材
 *
 * 高级文字替换素材示例:
 *
 * 前缀查找文字替换
 * {
 *     text: "你好",
 *     type: 1,
 *     d_key_prefix: "dtext"
 * }
 *
 * 默认前缀(dtext)查找文字替换
 * {
 *     text: "你好",
 *     type: 1
 * }
 *
 * 精确查找文字替换
 * {
 *     text: "你好",
 *     type: 1,
 *     d_key: "title"
 * }
 *
 * 高级图片替换素材示例:
 *
 * 前缀查找图片替换
 * {
 *     file: "/path/to/file.png",
 *     type: 2,
 *     d_key_prefix: "dtext"
 * }
 *
 * 默认前缀(dtext)查找替换
 * {
 *     file: "/path/to/file.png",
 *     type: 2
 * }
 *
 * 精确查找替换
 * {
 *     file: "/path/to/file.png",
 *     type: 2,
 *     d_key: "title"
 * }
 *
 *
 * */
public class ReplaceAsset {
    /**
    * 文字
    * */
    private String text;

    /**
     * 类型, 1 - 图片， 2 - 文字
     * */
    private int type;

    /**
     * 图片路径
     * */
    private String file;


    /**
     * 精确替换查找 key
     * */
    @JSONField(name="d_key")
    private String dKey;

    /**
     * 前缀查找替换前缀
     * */
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
