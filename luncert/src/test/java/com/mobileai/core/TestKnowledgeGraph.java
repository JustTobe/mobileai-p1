package com.mobileai.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mobileai.luncert.core.KnowledgeGraph;
import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Graph.TreeNode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestKnowledgeGraph {

    private int indent = 0;

    private int count = 0;

    private void outputNode(TreeNode<Event> node) {
        count++;
        StringBuilder builder = new StringBuilder();
        Event n = (Event)node.getValue();
        for (int i = 0; i < indent; i++) builder.append("\t");
        builder.append(n.getName()).append("[").append(node.getId()).append("] ").append(n.getType());
        System.out.println(builder.toString());

        indent++;
        for (TreeNode<Event> child : node.getChildren()) outputNode(child);
        indent--;
    }

    private void outputGraph(KnowledgeGraph graph) {
        TreeNode<Event> root = graph.getRoot();
        for (TreeNode<Event> node : root.getChildren()) outputNode(node);
        System.out.println("node num:" + count);
    }

    private void outputLeafNodes(KnowledgeGraph graph) {
        for (TreeNode<Event> node : graph.getLeafNodes()) {
            Event event = (Event)node.getValue();
            System.out.println(event.getName() + "[" + event.getEventId() + "]" + event.getType());
        }
        System.out.println("leaf node num:" + graph.getLeafNodes().size());
    }

    // @Test
    public void test() {
        KnowledgeGraph graph = KnowledgeGraph.getInstance();
        
        outputGraph(graph);

        outputLeafNodes(graph);
    }

    @Test
    public void testLearn() {
        KnowledgeGraph graph = KnowledgeGraph.getInstance();

        // 生成事件记录
        List<com.mobileai.luncert.model.mysql.Event> records = new ArrayList<>();
        StringBuilder history = new StringBuilder();
        Random random = new Random();
        int[] a = {1, 7, 1, 6, 5};
        for (int i = 0, tmp; i < 5; i++) {
            // tmp = random.nextInt(10);
            tmp = a[i];
            history.append(tmp).append(" ");
            records.add(new com.mobileai.luncert.model.mysql.Event());
            records.get(records.size() - 1).setEventId(tmp);
        }

        System.out.println(history.toString());

        graph.learn(records);
    }
}