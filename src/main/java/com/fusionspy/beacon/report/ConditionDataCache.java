package com.fusionspy.beacon.report;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


/**
 *
 * ConditionInitData初始化数据
 * 缓存使用了JSON文件，保存在classPath:report中，所有文件保持reportCache-{key}.json文件名格式
 * key = Report.ResourceType+"_"+Report.Attribute 组成
 *
 */
@Component
public class ConditionDataCache {


    private ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(ConditionDataCache.class);

    private static ConditionDataCache _self = new ConditionDataCache();

    private Cache<String,Map<String,ConditionInitData>> cache = CacheBuilder.newBuilder().build();



    Cache<String,Map<String,ConditionInitData>> getCache(){
        return this.cache;
    }

    static ConditionDataCache build(){
        return _self;
    }

    private ConditionDataCache(){
        init();
    }

    @PostConstruct
    void init(){
        try {
            for(File file :readCacheJsonFile()){

                String key = StringUtils.substringAfter(Files.getNameWithoutExtension(file.getName()),"reportCache-");
                cache.put(key, HashBiMap.create(Maps.asMap(parseJson(file), new Function<ConditionInitData, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable ConditionInitData input) {
                        return input.getName();
                    }
                })).inverse());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    File createCacheFolder() throws URISyntaxException {
        URL url = ClassUtils.getDefaultClassLoader().getResource("");
        String cacheFolder = ResourceUtils.toURI(url).getSchemeSpecificPart()+"reportCache";
        File folder = new File(cacheFolder);
        folder.mkdir();
        return folder;
    }

    //获取以“reportCache-”开头的所有文件
    @VisibleForTesting
    File[] readCacheJsonFile() throws URISyntaxException, FileNotFoundException {

        File file = null;
        try{
           file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "reportCache");
        }catch (FileNotFoundException e){
            file = createCacheFolder();
        }
        return file.listFiles(new FilenameFilter() {

            private Pattern pattern =  Pattern.compile("^reportCache-");
            @Override
            public boolean accept(File dir, String fileName) {
                return pattern.matcher(fileName).lookingAt();
            }
        });
    }

    @VisibleForTesting
    Set<ConditionInitData> parseJson(File file) throws IOException {
        return mapper.readValue(file, new TypeReference<Set<ConditionInitData>>() {});
    }



    LinkedHashSet<ConditionInitData> merge(StatisticReport statisticReport){
       return merge(statisticReport.getAttribute(),statisticReport.getConditionInitData());
    }

    public LinkedHashSet<ConditionInitData> merge(Attribute attribute, LinkedHashSet<ConditionInitData> conditionInitDatas) {
        String key = generateKey(attribute);
        if(cache.getIfPresent(key)==null){
            Map<String,ConditionInitData> map = Maps.newHashMap();
            cache.put(key,map);

            for(ConditionInitData conditionInitData:conditionInitDatas){
                map.put(conditionInitData.getName(),conditionInitData);
            }
            saveData(attribute,Sets.newHashSet(map.values()));

        }else{

            Map<String,ConditionInitData> m = cache.getIfPresent(key);
            for(ConditionInitData conditionInitData:conditionInitDatas) {

                if(m.get(conditionInitData.getName()) == null){
                    m.put(conditionInitData.getName(),conditionInitData);
                }else {
                    Set<String> origin = m.get(conditionInitData.getName()).getValues();
                    Set<String> diff = Sets.difference(origin, conditionInitData.getValues());
                    if (diff.isEmpty()) {
                        continue;
                    } else {
                        m.get(conditionInitData.getName()).getValues().addAll(diff);
                        conditionInitData.setValues(m.get(conditionInitData.getName()).getValues());
                    }
                }
            }
            saveData(attribute,Sets.newHashSet(m.values()));

        }
        return conditionInitDatas;
    }

    @VisibleForTesting
    String generateKey(Attribute attribute){
        return attribute.getResourceType()+"_"+attribute.getAttribute();

    }

    @VisibleForTesting
    void saveData(Attribute attribute,Set<ConditionInitData> conditionInitDatas){
        try {
            mapper.writeValue(getDataFile(attribute),conditionInitDatas);
        } catch (IOException e) {
           throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @VisibleForTesting
    File getDataFile(Attribute attribute) throws URISyntaxException, IOException {
        URL url = ClassUtils.getDefaultClassLoader().getResource("reportCache");
        String filePath = ResourceUtils.toURI(url).getSchemeSpecificPart()+"/reportCache-"+generateKey(attribute)+".json";
        return new File(filePath);
    }


}
