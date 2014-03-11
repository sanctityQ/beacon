package com.fusionspy.beacon.report;


import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import com.google.common.cache.Cache;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.assertj.core.util.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static org.assertj.core.api.Assertions.assertThat;

public class ConditionCacheTest {

   private ConditionDataCache conditionCache = ConditionDataCache.build();

   @Test
   public void readCacheJsonFile() throws IOException, URISyntaxException {

       File[] files = conditionCache.readCacheJsonFile();
       assertThat(Pattern.compile("^reportCache").matcher("reportCache-test.json").lookingAt()).isTrue();
       assertThat(files).hasSize(1);
       for(File file:files){
           //Assert.assertEquals(file.getName(),"reportCache-test");
           assertThat(file.getName()).isEqualTo("reportCache-test.json");
           Set<ConditionInitData> set = conditionCache.parseJson(file);
           for(ConditionInitData conditionInitData:set){
               assertThat(conditionInitData.getName()).isEqualTo("test");
               assertThat(conditionInitData.getValues()).containsOnly("a","b","c");
           }

       }
   }

   @Test
   public void init(){
       conditionCache.init();
       Cache<String,Map<String,ConditionInitData>> cache = conditionCache.getCache();
       assertThat(cache.size()).isEqualTo(1);
       for(ConditionInitData conditionInitData:cache.getIfPresent("test").values()){
           assertThat( conditionInitData.getName()).isEqualTo("test");
           assertThat(conditionInitData.getValues()).containsOnly("a", "b", "c");
       }
   }


   @Test
   public void merge() throws IOException, URISyntaxException {


       Attribute attribute = new Attribute();
       attribute.setAttribute("t2");
       attribute.setResourceType(ResourceType.WEBLOGIC);

       ConditionInitData c1 = new ConditionInitData();
       c1.setName("c1");

       c1.setValues(newTreeSet("1", "2", "3"));

       conditionCache.merge(attribute, newLinkedHashSet(c1));

       File file = conditionCache.getDataFile(attribute);

       try {
           assertThat(file).exists();
           assertThat(Files.contentOf(file, "UTF-8")).isEqualTo("[{\"name\":\"c1\",\"values\":[\"1\",\"2\",\"3\"]}]");

           Cache<String, Map<String, ConditionInitData>> cache = conditionCache.getCache();

           ConditionInitData c1Cache = cache.getIfPresent(conditionCache.generateKey(attribute)).get("c1");
           assertThat(c1Cache).isEqualTo(c1);

           ConditionInitData c2 = new ConditionInitData();
           c2.setName("c2");
           c2.setValues(newTreeSet("4", "5", "6"));

           conditionCache.merge(attribute, newLinkedHashSet(c1, c2));

           ConditionInitData c2Cache = cache.getIfPresent(conditionCache.generateKey(attribute)).get("c2");

           assertThat(c2Cache).isEqualTo(c2);
           assertThat(Files.contentOf(file, "UTF-8")).contains("{\"name\":\"c1\",\"values\":[\"1\",\"2\",\"3\"]}")
                   .contains("{\"name\":\"c2\",\"values\":[\"4\",\"5\",\"6\"]}");

           c1.setValues(newTreeSet("4", "1", "2"));

           conditionCache.merge(attribute, newLinkedHashSet(c1, c2));

           c1.setValues(newTreeSet("4", "1", "2", "3"));
           conditionCache.merge(attribute, newLinkedHashSet(c1, c2));

           assertThat(c1Cache).isEqualTo(c1);

           assertThat(Files.contentOf(file, "UTF-8")).contains("{\"name\":\"c1\",\"values\":[\"1\",\"2\",\"3\",\"4\"]}");
       } finally {
           file.deleteOnExit();
       }

   }


   @Test
   public void newConditionInitDataFile() throws URISyntaxException, IOException {
       Attribute attribute = new Attribute();
       attribute.setResourceType(ResourceType.WEBLOGIC);
       attribute.setAttribute("t1");

       ConditionInitData conditionInitData = new ConditionInitData();
       conditionInitData.setName("ejbName");
       conditionInitData.setValues(newTreeSet("aa", "bb"));

       conditionCache.saveData(attribute, newHashSet(conditionInitData));
       File file = conditionCache.getDataFile(attribute);
       assertThat(file).exists();
       file.deleteOnExit();
   }

   private  static  TreeSet newTreeSet(String... e){
       Iterable<String> eList = Lists.newArrayList(e);
       return com.google.common.collect.Sets.newTreeSet(eList);
   }


   private static <E> LinkedHashSet newLinkedHashSet(E... e){
       Iterable<E> eList = Lists.newArrayList(e);
       return Sets.newLinkedHashSet(eList);
   }
}
