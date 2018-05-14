package com.mobileai.luncert.utils.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * not completed
 */
public class GraphNode<T> {

    private List<GraphNode<T>> pioneer;

    private List<GraphNode<T>> succeed;

    private int id;

    private T value;

    public int getId() { return id; }

    public T getValue() { return value; }

    private void init(int id, T value) {
        this.id = id;
        this.value = value;
        this.pioneer = new ArrayList<>();
        this.succeed = new ArrayList<>();
    }

    public GraphNode(int id, T value) { this.init(id, value); }

    public GraphNode(int id, T value, boolean bePoineerOf, GraphNode<T> n) {
        this.init(id, value);
        if (bePoineerOf) {
            this.addSucceed(n);
            n.addPioneer(this);
        }
        else {
            this.addPioneer(n);
            n.addSucceed(this);
        }
    }

    public void addSucceed(GraphNode<T> node) { this.succeed.add(node); }

    public void addPioneer(GraphNode<T> node) { this.pioneer.add(node); }

    public boolean match(int id) { return this.id == id; }

	public List<GraphNode<T>> matchAll(int id) {
		return null;
	}

	public List<GraphNode<T>> getOneDegreeNodes() {
		return null;
	}

	public GraphNode<T> find(int id) {
		return null;
	}
    
}