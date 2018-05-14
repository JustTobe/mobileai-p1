package com.mobileai.luncert.utils.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录病毒攻击已经过的步骤
 */

public class Path<T> {

    private List<T> records;

    public Path() {
        records = new ArrayList<>();
    }

    public int size() { return records.size(); }

    public void addNode(T node) { this.records.add(node); }

    public T lastNode() {
        if (records.size() > 0) return records.get(records.size() - 1);
        else return null;
    }

    public List<T> allNodes() { return records; }

}