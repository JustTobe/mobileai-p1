package com.mobileai.luncert.core;

import java.util.List;
import java.util.Random;

import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Graph.Path;
import com.mobileai.luncert.utils.Graph.TreeNode;

public class GenEvent {
    
    private KnowledgeGraph graph = KnowledgeGraph.getInstance();

    private Path<TreeNode<Event>> path;

    public Path<TreeNode<Event>> getPath() { return path; }

    public SecurityEvent nextEvent() {
        Random random = new Random();

        TreeNode<Event> node;
        if (path != null && path.size() > 0 && random.nextBoolean() && !path.lastNode().getParent().beRoot) {
            node = ((TreeNode<Event>)path.lastNode()).getParent();
        }
        else {
            List<TreeNode<Event>> leafNodes = graph.getLeafNodes();
            node = (TreeNode<Event>)leafNodes.get(random.nextInt(leafNodes.size()));
            path = new Path<>(); // create new path
        }
        path.addNode(node);
        return new SecurityEvent(genIp(), genIp(), node.getId(), ((com.mobileai.luncert.model.neo4j.node.Event)node.getValue()).getType());   
    }

	private int genIp() {
        final Random random = new Random();

		int ret = 0;
        for (int i = 0; i < 4; i++) ret += (random.nextInt(254) + 1) << (8 * i);
		return ret;
    }
    
}