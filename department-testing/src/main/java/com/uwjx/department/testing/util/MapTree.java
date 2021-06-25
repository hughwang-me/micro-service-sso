package com.uwjx.department.testing.util;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.*;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/25 22:09
 */

public class MapTree implements Serializable {
    private static final long serialVersionUID = -7001256196262377645L;
    private static final String DEFAULT_NODE_ID_KEY = "id";
    private static final String DEFAULT_PAR_NODE_ID_KEY = "pid";
    private static final String DEFAULT_NODE_TEXT_KEY = "value";
    protected static final String PARENT_KEY = "parent";
    protected static final String CHILDS_KEY = "children";
    protected static final String DATA_KEY = "data";
    private Map<String, Object> treeMap;
    private Map<String, Object> rootNode;
    private String keyName;
    private String parKeyName;
    private String textKeyName;

    public MapTree(String keyName, String parKeyName, String textKeyName) {
        this.keyName = keyName;
        this.parKeyName = parKeyName;
        this.textKeyName = textKeyName;
        this.rootNode = null;
        this.treeMap = new HashMap();
    }

    public MapTree(String keyName, String parKeyName) {
        this(keyName, parKeyName, "value");
    }

    public MapTree() {
        this("id", "pid");
    }

    public MapTree(List<Map<String, Object>> datas) {
        this();
        this.buildTree(datas);
    }

    public MapTree(List<Map<String, Object>> datas, String keyName, String parKeyName) {
        this(keyName, parKeyName);
        this.buildTree(datas);
    }

    public MapTree(List<Map<String, Object>> datas, String keyName, String parKeyName, String textKeyName) {
        this(keyName, parKeyName, textKeyName);
        this.buildTree(datas);
    }

    protected void putDatas(List<Map<String, Object>> datas) {
        Iterator iter = datas.iterator();

        while(iter.hasNext()) {
            Map<String, Object> dataMap = (Map)iter.next();
            Map<String, Object> nodeMap = new HashMap();
            nodeMap.put("data", dataMap);
            this.treeMap.put(dataMap.get(this.keyName).toString(), nodeMap);
            if (this.rootNode == null) {
                this.rootNode = nodeMap;
            }
        }

    }

    protected void buildTree(List<Map<String, Object>> datas) {
        this.rootNode = null;
        this.treeMap.clear();
        this.putDatas(datas);
        Iterator iter = datas.iterator();

        while(iter.hasNext()) {
            Map<String, Object> dataMap = (Map)iter.next();
            Map<String, Object> nodeMap = this.getNode(dataMap.get(this.keyName).toString());
            Map<String, Object> parNodeMap = (Map)this.treeMap.get(dataMap.get(this.parKeyName).toString());
            if (parNodeMap != null) {
                nodeMap.put("parent", parNodeMap);
                List<Map<String, Object>> childs = (List)parNodeMap.get("children");
                if (childs == null) {
                    childs = new ArrayList();
                    parNodeMap.put("children", childs);
                }

                ((List)childs).add(nodeMap);
            }
        }

        this.adjRootNode();
    }

    protected void adjRootNode() {
        Map node;
        for(node = this.rootNode; this.getParentNode(node) != null; node = this.getParentNode(node)) {
        }

        this.rootNode = node;
    }

    public boolean isAncestor(Map<String, Object> higherNode, Map<String, Object> lowerNode) {
        Map node = lowerNode;

        do {
            if ((node = this.getParentNode(node)) == null) {
                return false;
            }
        } while(node != higherNode);

        return true;
    }

    public boolean isAncestor(String higherKey, String lowerKey) {
        return this.isAncestor(this.getNode(higherKey), this.getNode(lowerKey));
    }

    public Map<String, Object> getNode(String key) {
        return (Map)this.treeMap.get(key);
    }

    public Map<String, Object> getData(Map<String, Object> node) {
        return node == null ? null : (Map)node.get("data");
    }

    public Map<String, Object> getData(String key) {
        return this.getData(this.getNode(key));
    }

    public Map<String, Object> getParentNode(Map<String, Object> node) {
        return node == null ? null : (Map)node.get("parent");
    }

    public Map<String, Object> getParentNode(String key) {
        return this.getParentNode(this.getNode(key));
    }

    public Map<String, Object> getParentData(Map<String, Object> node) {
        return this.getData(this.getParentNode(node));
    }

    public Map<String, Object> getParentData(String key) {
        return this.getParentData(this.getNode(key));
    }

    public List<Map<String, Object>> getChildNodes(Map<String, Object> node) {
        return node == null ? null : (List)node.get("children");
    }

    public List<Map<String, Object>> getChildNodes(String key) {
        return this.getChildNodes(this.getNode(key));
    }

    public List<Map<String, Object>> getChildDatas(Map<String, Object> node) {
        List<Map<String, Object>> dataList = new ArrayList();
        List<Map<String, Object>> childs = this.getChildNodes(node);
        if (childs != null) {
            Iterator iter = childs.iterator();

            while(iter.hasNext()) {
                Map<String, Object> nodeMap = (Map)iter.next();
                Map<String, Object> dataMap = this.getData(nodeMap);
                if (dataMap != null) {
                    dataList.add(dataMap);
                }
            }
        }

        return dataList;
    }

    public List<Map<String, Object>> getChildDatas(String key) {
        return this.getChildDatas(this.getNode(key));
    }

    public List<Map<String, Object>> getChildDatasAll(Map<String, Object> node, List<Map<String, Object>> dataList) {
        List<Map<String, Object>> childs = this.getChildNodes(node);
        if (childs != null) {
            Iterator iter = childs.iterator();

            while(iter.hasNext()) {
                Map<String, Object> nodeMap = (Map)iter.next();
                Map<String, Object> dataMap = this.getData(nodeMap);
                if (dataMap != null) {
                    dataList.add(dataMap);
                    this.getChildDatasAll(nodeMap, dataList);
                }
            }
        }

        return dataList;
    }

    public List<Map<String, Object>> getChildDatasAll(String key) {
        List<Map<String, Object>> dataList = new ArrayList();
        return this.getChildDatasAll(this.getNode(key), dataList);
    }

    public Map<String, Object> getFirstChildNode(Map<String, Object> node) {
        List<Map<String, Object>> childs = this.getChildNodes(node);
        return childs != null && childs.size() > 0 ? (Map)childs.get(0) : null;
    }

    public Map<String, Object> getFirstChildNode(String key) {
        return this.getFirstChildNode(this.getNode(key));
    }

    public Map<String, Object> getFirstChildData(Map<String, Object> node) {
        return this.getData(this.getFirstChildNode(node));
    }

    public Map<String, Object> getFirstChildData(String key) {
        return this.getFirstChildData(this.getNode(key));
    }

    public boolean isLeaf(Map<String, Object> node) {
        return this.getChildNodes(node) == null;
    }

    public boolean isLeaf(String key) {
        return this.getChildNodes(key) == null;
    }

    public boolean isRoot(Map<String, Object> node) {
        return node == this.rootNode;
    }

    public boolean isRoot(String key) {
        return this.getNode(key) == this.rootNode;
    }

    public List<Map<String, Object>> getAllData() {
        return this.getAllData(new ArrayList(), this.rootNode, false);
    }

    public List<Map<String, Object>> getAllLeafData() {
        return this.getAllData(new ArrayList(), this.rootNode, true);
    }

    protected List<Map<String, Object>> getAllData(List<Map<String, Object>> datas, Map<String, Object> node, boolean leafOnly) {
        Map<String, Object> dataMap = this.getData(node);
        if (dataMap != null && (!leafOnly || this.isLeaf(node))) {
            datas.add(dataMap);
        }

        if (!this.isLeaf(node)) {
            List<Map<String, Object>> childs = this.getChildNodes(node);
            Iterator iter = childs.iterator();

            while(iter.hasNext()) {
                Map<String, Object> nodeMap = (Map)iter.next();
                this.getAllData(datas, nodeMap, leafOnly);
            }
        }

        return datas;
    }

    public Map<String, Object> getRootNode() {
        return this.rootNode;
    }

    public Map<String, Object> getRootData() {
        return this.getData(this.getRootNode());
    }

    public MapTree getSubTree(Map<String, Object> node) {
        MapTree subTree = new MapTree(this.keyName, this.parKeyName);
        subTree.rootNode = node;
        subTree.treeMap = this.treeMap;
        return subTree;
    }

    public MapTree getSubTree(String key) {
        return this.getSubTree(this.getNode(key));
    }

    public List<Map<String, Object>> getPath(Map<String, Object> node) {
        List<Map<String, Object>> path = new ArrayList();

        for(Map tnode = node; tnode != null; tnode = this.getParentNode(tnode)) {
            path.add(tnode);
        }

        return path;
    }

    public List<Map<String, Object>> getPath(String key) {
        return this.getPath(this.getNode(key));
    }

    public String getKeyValue(Map<String, Object> data) {
        return data.get(this.keyName).toString();
    }

    public String getParKeyValue(Map<String, Object> data) {
        return data.get(this.parKeyName).toString();
    }

    public String getText(Map<String, Object> data) {
        return data.get(this.textKeyName).toString();
    }

    public String toJSON() {
        Map<String, Object> map = this.nodeToMap(this.rootNode);
        return JSON.toJSONString(map);
    }

    public List<Map<String, Object>> toJsonMap(String keyLevel) {
        Set<String> keySet = this.treeMap.keySet();
        List<Map<String, Object>> rootNodes = new ArrayList();
        int maxLevel = 99999;
        Iterator var5 = keySet.iterator();

        Map node;
        while(var5.hasNext()) {
            String key = (String)var5.next();
            node = (Map)this.treeMap.get(key);
            Map<String, Object> data = (Map)node.get("data");
            Integer level = Integer.valueOf((String)data.get(keyLevel).toString());
            if (level < maxLevel) {
                maxLevel = level;
                rootNodes.clear();
                rootNodes.add(node);
            } else if (level == maxLevel) {
                rootNodes.add(node);
            }
        }

        List<Map<String, Object>> result = new ArrayList();
        Iterator var11 = rootNodes.iterator();

        while(var11.hasNext()) {
            node = (Map)var11.next();
            result.add(this.nodeToMap(node));
        }

        return result;
    }

    public Map<String, Object> nodeToMap(Map<String, Object> node) {
        Map<String, Object> result = new LinkedHashMap();
        Map<String, Object> data = (Map)node.get("data");
        Set<String> keySet = data.keySet();
        Iterator var5 = keySet.iterator();

        while(var5.hasNext()) {
            String key = (String)var5.next();
            result.put(key, data.get(key));
        }

        if (node.get("children") != null) {
            List<Map<String, Object>> list = new ArrayList();
            List<Map<String, Object>> children = (List)node.get("children");
            Iterator var7 = children.iterator();

            while(var7.hasNext()) {
                Map<String, Object> itemNode = (Map)var7.next();
                Map<String, Object> child = this.nodeToMap(itemNode);
                list.add(child);
            }

            result.put("children", list);
        }

        return result;
    }
}
