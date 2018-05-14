package com.mobileai.luncert.utils.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeNode<T> {

    public boolean beRoot = false;

    private TreeNode<T> parent;

    private List<TreeNode<T>> children;

    private int id;

    private T value;

    public int getId() { return id; }

    public T getValue() { return value; }

    /**
     * root node with null fields
     */
    public TreeNode() {
        beRoot = true;
        children = new ArrayList<>();
    }

    public TreeNode(int id, T value) {
        this.id = id;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public TreeNode(int id, T value, TreeNode<T> parent) {
        this.id = id;
        this.value = value;
        this.children = new ArrayList<>();
        relateToParent(parent);
    }

    public void relateToParent(TreeNode<T> parent) {
        this.parent = parent;
        parent.addChild(this);
    }

    public void setParent(TreeNode<T> parent) { this.parent = parent; }

    public void addChild(TreeNode<T> child) { this.children.add(child); }

    public void addChild(int id, T value) { new TreeNode<T>(id, value, this); }

    public TreeNode<T> getParent() { return parent; }

    public List<TreeNode<T>> getChildren() { return children; }

    public boolean hasChild(int id) {
        for (TreeNode<T> node : this.children) if (node.match(id)) return true;
        return false;
    }

	public boolean match(int id) {
        // root node has no id
        return !beRoot && this.id == id;
    }

	public TreeNode<T> find(int id) {
        if (this.match(id)) return this;

        Stack<TreeNode<T>> nodeStack = new Stack<>();
        if (this.children.size() > 0) nodeStack.push(this);

        while (nodeStack.size() > 0) {
            int cursor = 0;
            TreeNode<T> parent = nodeStack.pop(), child = null;
            if (parent.match(id)) return parent;
            while (cursor < parent.children.size()) {
                child = parent.children.get(cursor);
                if (child.match(id)) return child;
                if (child.children.size() > 0) nodeStack.push(child);
                cursor++;
            }
        }
		return null;
	}

    /**
     * 广度优先遍历
     */
	public List<TreeNode<T>> matchAll(int id) {
        List<TreeNode<T>> ret = new ArrayList<>();
        Stack<TreeNode<T>> nodeStack = new Stack<>();
        if (this.children.size() > 0) nodeStack.push(this);
        else if (this.match(id)) ret.add(this); // root node won't be add to ret
        while (nodeStack.size() > 0) {
            int cursor = 0;
            TreeNode<T> parent = nodeStack.pop(), child = null;
            if (parent.match(id)) ret.add(parent);
            while (cursor < parent.children.size()) {
                child = parent.children.get(cursor);
                if (child.match(id)) ret.add(child);
                if (child.children.size() > 0) nodeStack.push(child);
                cursor++;
            }
        }
		return ret;
	}

	public List<TreeNode<T>> getLeafNodes() {
        List<TreeNode<T>> ret = new ArrayList<>();
        Stack<TreeNode<T>> nodeStack = new Stack<>();
        if (this.children.size() > 0) nodeStack.push(this);
        while (nodeStack.size() > 0) {
            int cursor = 0;
            TreeNode<T> parent = nodeStack.pop(), child = null;
            while (cursor < parent.children.size()) {
                child = parent.children.get(cursor);
                if (child.children.size() > 0) nodeStack.push(child);
                else ret.add(child);
                cursor++;
            }
        }
		return ret;
	}

}