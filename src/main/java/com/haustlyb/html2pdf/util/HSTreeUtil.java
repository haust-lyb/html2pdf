package com.haustlyb.html2pdf.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/7/3 11:45 上午
 */
public class HSTreeUtil {

    private HSTreeUtil() {
    }

    private List data;

    private String ognlExpKeyName;
    private String treeKeyName;
    private String ognlExpParentKeyName;
    private String treeParentKeyName;
    private String ognlExpLabelName;
    private String treeLabelName;

    private String childrenName;

    private Boolean mustIncludeChildren;




    public <T> List<HashMap> exec(Class<T> tClass,BiConsumer<HashMap,T> function) {
        validate();
        List<HashMap> hashMapData = new ArrayList<>();
        data.stream().forEach(e -> {
            HashMap hsTreeItem = new HashMap();
            function.accept(hsTreeItem, (T) e);
            OgnlWrapper wrapper = new OgnlWrapper(e);
            hsTreeItem.put(treeLabelName, wrapper.get(ognlExpLabelName));
            hsTreeItem.put(treeKeyName, wrapper.get(ognlExpKeyName));
            hsTreeItem.put(treeParentKeyName, wrapper.get(ognlExpParentKeyName));
            hashMapData.add(hsTreeItem);
        });
        Map<Object, List<HashMap>> groupSubs = hashMapData.stream().filter(e -> e.get(treeParentKeyName) != null).collect(Collectors.groupingBy(e -> e.get(treeParentKeyName)));
        hashMapData.stream().forEach(e -> {
            if (groupSubs.get(e.get(treeKeyName)) != null) {
                e.put(childrenName, groupSubs.get(e.get(treeKeyName)));
            }else{
                if (mustIncludeChildren != null && mustIncludeChildren){
                    e.put(childrenName,new ArrayList<>());
                }
            }
        });
        List<HashMap> finalHsTree = hashMapData.stream().filter(e -> e.get(treeParentKeyName) == null).collect(Collectors.toList());
        return finalHsTree;
    }

    public List<HashMap> exec() {
        return exec(Object.class,(treeItem,o)->{});
    }


    public static HSTreeUtil build(List data) {
        HSTreeUtil bt = new HSTreeUtil();
        bt.setData(data);
        return bt;
    }


    public HSTreeUtil setKeyNameCorrespondence(String ognlExpKeyName, String treeKeyName) {
        this.ognlExpKeyName = ognlExpKeyName;
        this.treeKeyName = treeKeyName;
        return this;
    }

    public HSTreeUtil setParentKeyName(String ognlExpParentKeyName, String treeParentKeyName) {
        this.ognlExpParentKeyName = ognlExpParentKeyName;
        this.treeParentKeyName = treeParentKeyName;
        return this;
    }

    public HSTreeUtil setChildrenName(String childrenName) {
        this.childrenName = childrenName;
        return this;
    }

    public HSTreeUtil setLabelNameCorrespondence(String ognlExpLabelName, String treeLabelName) {
        this.ognlExpLabelName = ognlExpLabelName;
        this.treeLabelName = treeLabelName;
        return this;
    }

    public HSTreeUtil mustIncludeChildren(){
        this.mustIncludeChildren = true;
        return this;
    }


    private void setData(List data) {
        this.data = data;
    }

    private void validate() {

        if (data == null) {
            throw new RuntimeException("data 不能为空，请检查是否调用了build方法，并传入了正确的数据");
        }

        if (ognlExpKeyName == null) {
            throw new RuntimeException("ognlExpKeyName 不能为空");
        }

        if (treeKeyName == null) {
            throw new RuntimeException("treeKeyName 不能为空");
        }

        if (ognlExpLabelName == null) {
            throw new RuntimeException("ognlExpLabelName 不能为空");
        }

        if (treeLabelName == null) {
            throw new RuntimeException("treeLabelName 不能为空");
        }

        if (ognlExpParentKeyName == null) {
            throw new RuntimeException("ognlExpParentKeyName 不能为空");
        }

        if (childrenName == null) {
            throw new RuntimeException("childrenName 不能为空");
        }
    }


}
