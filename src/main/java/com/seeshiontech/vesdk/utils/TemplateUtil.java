package com.seeshiontech.vesdk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seeshiontech.vesdk.TemplateType;

import java.util.*;

public class TemplateUtil {

    private class LayerData{
        private String source;
        private int type;
    }

    private class CompData{
        private String name;
        private int offsetStyle;
        private int replaceNum;
        private int offset;
        private int duration;
        private List<LayerData> layers;
    }


    private String version;
    private String description;
    private boolean valid;
    private float fps;
    private int duration;

    private int mainCompWidth;
    private int mainCompHeight;

    private int templateType;

    private HashMap<String, CompData> otherComp;
    private HashMap<String, CompData> sourcesComp;
    private List<CompData> segmentsComp;
    private CompData entranceComp;
    private CompData exitComp;
    private HashMap<String, String> sources;


    /**
     * 传入 config.json 字符串
     *
     * */
    public TemplateUtil(String json) {
        otherComp = new HashMap<>();
        segmentsComp = new ArrayList<>();
        sourcesComp = new HashMap<>();
        sources = new HashMap<>();
        load(json);
    }


    /**
     * 解析 comp
     *
     * @param value
     * @return CompData
     * */
    private CompData compResolve(JSONObject value) {
        CompData compData = new CompData();
        compData.name =  value.getString("name");
        compData.duration = value.getInteger("duration");
        compData.layers = new ArrayList<>();

        JSONArray layers = value.getJSONArray("layers");
        Iterator<Object> ite =  layers.iterator();
        while(ite.hasNext()) {
            JSONObject o = (JSONObject) ite.next();
            LayerData layerData = new LayerData();
            layerData.type = o.getInteger("type");
            layerData.source = o.getString("source");
            compData.layers.add(layerData);
        }

        return compData;
    }


    /**
     * 解析传入的配置文件
     *
     * @param json, config.json content
     * */
    private void load(String json) {
        JSONObject config = JSON.parseObject(json);
        if (config == null) {
            valid = false;
            return;
        }

        templateType = config.getInteger("type");
        // normal template
        if (templateType == TemplateType.NORMAL_TEMPLATE.getValue()) {
            String mainName = config.getString("main");
            description = config.getString("description");
            fps = config.getFloat("fps");
            version = config.getString("version");
            JSONArray comps = config.getJSONArray("comps");
            Iterator<Object> compsIte = comps.iterator();
            while(compsIte.hasNext()) {
                JSONObject comp = (JSONObject) compsIte.next();
                String name = comp.getString("name");
                if (name.equals(mainName)) {
                    JSONArray size = comp.getJSONArray("size");
                    mainCompWidth = (Integer) size.get(0);
                    mainCompHeight = (Integer) size.get(1);
                    duration = comp.getInteger("duration");
                }
            }

            return;
        }


        // dynamic template
        HashMap<String, Entry<Integer, Integer>> offsetMap = new HashMap<>();
        JSONObject offsets = config.getJSONObject("offsets");
        Set<String> offsetKeySet = offsets.keySet();
        Iterator<String> ite = offsetKeySet.iterator();
        while(ite.hasNext()){
            String key = ite.next();
            int style = 0;
            int offset = 0;
            if (offsets.get(key) instanceof Integer) {
                offset = offsets.getInteger(key);
            } else {
                JSONObject value = offsets.getJSONObject(key);
                style = value.getInteger("style");
                offset = value.getInteger("time");
            }

            Entry<Integer, Integer> p = new Entry<>(style, offset);
            offsetMap.put(key, p);
        }


        Set<String> configKeys = config.keySet();
        Iterator<String>  configIte = configKeys.iterator();
        while(configIte.hasNext()) {
            String key = configIte.next();
            if (key.equals("size")) {
                JSONArray arr = config.getJSONArray(key);
                mainCompWidth = (Integer) arr.get(0);
                mainCompHeight = (Integer) arr.get(1);
                continue;
            }

            if (key.equals("description")) {
                description = config.getString(key);
                continue;
            }

            if (key.equals("fps")) {
                fps = config.getFloat(key);
                continue;
            }

            if (key.equals("version")) {
                version = config.getString(key);
                continue;
            }

            if (key.equals("entrance")) {
                entranceComp = compResolve(config.getJSONObject(key));
                Entry<Integer, Integer> offset = offsetMap.get(entranceComp.name);
                if (offset != null) {
                   entranceComp.offsetStyle = offset.getKey();
                   entranceComp.offset = offset.getValue();
                }
                continue;
            }

            if (key.equals("exit")) {
                exitComp = compResolve(config.getJSONObject(key));
                Entry<Integer, Integer> offset = offsetMap.get(exitComp.name);
                if (offset != null) {
                    exitComp.offsetStyle = offset.getKey();
                    exitComp.offset = offset.getValue();
                }
                continue;
            }

            if (key.equals("sources")) {
                JSONArray sourcesArr = config.getJSONArray(key);
                Iterator<Object> sourcesIte = sourcesArr.iterator();
                while(sourcesIte.hasNext()) {
                    JSONObject source = (JSONObject) sourcesIte.next();
                    CompData compData = compResolve(source);
                    sourcesComp.put(compData.name, compData);
                }

                continue;
            }

            if (key.equals("others")) {
                JSONArray sourcesOther = config.getJSONArray(key);
                Iterator<Object> sourcesIte = sourcesOther.iterator();
                while(sourcesIte.hasNext()) {
                    JSONObject source = (JSONObject) sourcesIte.next();
                    CompData compData = compResolve(source);
                    otherComp.put(compData.name, compData);
                }

                continue;
            }

            if (key.equals("segments")) {
                JSONArray sourcesSeg = config.getJSONArray(key);
                Iterator<Object> sourcesIte = sourcesSeg.iterator();
                while(sourcesIte.hasNext()) {
                    JSONObject source = (JSONObject) sourcesIte.next();
                    CompData compData = compResolve(source);
                    Entry<Integer, Integer> offset = offsetMap.get(compData.name);
                    if (offset != null) {
                        compData.offsetStyle = offset.getKey();
                        compData.offset = offset.getValue();
                    }
                    segmentsComp.add(compData);
                }

                continue;
            }

            if (key.equals("assets")) {
                String assetKey, name;
                List<Object> assets = config.getJSONArray(key);
                Iterator<Object> assetsIte = assets.iterator();
                while(assetsIte.hasNext()) {
                    JSONObject asset = (JSONObject) assetsIte.next();
                    int type = asset.getInteger("type");
                    if (type == 3) {
                        assetKey = asset.getString("key");
                        name = asset.getString("name");
                        sources.put(assetKey, name);
                    }
                }
            }

        }

        HashSet<String> sourcesNames = new HashSet<>();
        if (entranceComp != null) {
            computeReplaceNum(entranceComp, sourcesNames);
            entranceComp.replaceNum = sourcesNames.size();
        }

        sourcesNames.clear();
        if (exitComp != null) {
            computeReplaceNum(exitComp, sourcesNames);
            exitComp.replaceNum = sourcesNames.size();
        }

        Iterator<CompData> segmentsIte = segmentsComp.iterator();
        while(segmentsIte.hasNext()) {
            sourcesNames.clear();
            CompData compData = segmentsIte.next();
            computeReplaceNum(compData, sourcesNames);
            compData.replaceNum = sourcesNames.size();
        }



        return;
    }


    /**
     * 计算 comp 中可替换的素材数目
     *
     * @param comp
     * @param sourcesNames
     * */
    private void computeReplaceNum(CompData comp, HashSet<String> sourcesNames) {
        Iterator<LayerData> layers = comp.layers.iterator();
        while(layers.hasNext()) {
            LayerData layer = layers.next();

            if ((layer.type & 1) > 0) {
                String sourceName = sources.get(layer.source);
                if (sourceName == null) {
                    continue;
                }

                CompData compData = sourcesComp.get(sourceName);
                if (compData != null) {
                    sourcesNames.add(sourceName);
                    continue;
                }

                CompData otherCompData = otherComp.get(sourceName);
                if (otherCompData != null) {
                    computeReplaceNum(otherCompData, sourcesNames);
                }
            }
        }

    }


    /**
     * 根据素材数目计算视频时长
     *
     * 单位: 秒
     *
     * @param num
     * @return float
     * */
    public float getDurationWithFileNum(int num) {

        // 普通模板
        if (templateType == TemplateType.NORMAL_TEMPLATE.getValue()) {
            return duration / fps;
        }


        // 动态模板
        int frame = 0;
        int currentFileIndex = entranceComp != null ?  entranceComp.replaceNum : 0;
        int totalFileSize = exitComp != null ? num - exitComp.replaceNum : num;

        if (entranceComp != null) {
            frame += entranceComp.duration;
        }

        int totalContainedSources = 0;
        List<CompData> oneSourcesSegments = new ArrayList<>();

        Iterator<CompData> compIter = segmentsComp.iterator();
        while(compIter.hasNext()) {
            CompData segment = compIter.next();
            totalContainedSources += segment.replaceNum;
            if (segment.replaceNum == 1) {
                oneSourcesSegments.add(segment);
            }
        }

        if (totalContainedSources == 0) {
            duration = frame;

            return duration/fps;
        }


        int segIndex = 0;
        for(; currentFileIndex < totalFileSize; segIndex++) {
            int numFileLeft = totalFileSize - currentFileIndex;
            int index = segIndex % segmentsComp.size();
            CompData segment = segmentsComp.get(index);
            int numOfFileSupport = segment.replaceNum;

            if (numOfFileSupport <= numFileLeft) {
                frame = frame + segment.duration + segment.offset;
                currentFileIndex += numOfFileSupport;
            } else {
                CompData adaptComp = null;
                Iterator<CompData> iterator = segmentsComp.iterator();
                while(iterator.hasNext()) {
                    CompData tempComp = iterator.next();
                    if (tempComp.replaceNum == numFileLeft) {
                        adaptComp = tempComp;
                        break;
                    }
                }


                if (adaptComp != null) {
                    frame = frame + adaptComp.duration + adaptComp.offset;
                } else { // 没有找到数量一致的片段,使用单个素材片段来组合剩下素材
                    if (!oneSourcesSegments.isEmpty()) {
                        Iterator<CompData> it = segmentsComp.iterator();
                        while(it.hasNext()) {
                            CompData tempComp = it.next();
                            if (tempComp.replaceNum == 1) {
                                oneSourcesSegments.add(tempComp);
                            }
                        }

                        for(int s = 0; currentFileIndex < totalFileSize; currentFileIndex++, s++) {
                            if (s > oneSourcesSegments.size() - 1) {
                                s = 0;
                            }

                            frame = frame + oneSourcesSegments.get(s).duration + oneSourcesSegments.get(s).offset;
                        }
                    }
                    // 如果没有支持单个素材的片段,直接舍弃剩下的素材
                }
                currentFileIndex += numFileLeft;
            }
        }

        if (exitComp != null) {
            frame = frame +  exitComp.duration + exitComp.offset;
        }

        duration = frame;

        return duration / fps;
    }

    public boolean isValid() {
        return false;
    }

    public float getFps() {

        return 0;
    }


    public int getFrameCount() {
        return 0;
    }


    public int getWidth() {
        return 0;
    }


    public int getHeight() {
        return 0;
    }

    public int getTemplateType() {
        return 0;
    }


    public String getmVersion() {
        return "";
    }

}
