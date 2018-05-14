package com.mobileai.luncert.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Neo4jUtil;
import com.mobileai.luncert.utils.Graph.TreeNode;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

/**
 * 单例
 */
public class KnowledgeGraph{

    private static final int MAX_SEQ_LENGTH = 3;

    private static final double THRESHOLD = 2.0;

    private TreeNode<Event> root = null;

    private List<TreeNode<Event>> leafNodes;

    public static KnowledgeGraph getInstance() { return MatchGraphInner.INSTANCE; }

    public TreeNode<Event> getRoot() { return root; }

    public List<TreeNode<Event>> getLeafNodes() { return leafNodes; }

    private class Path {

        private double compactSum = 0d;

        private int[] path = new int[MAX_SEQ_LENGTH];

        public void setPath(int i, int value) { path[i] = value; }

        public int[] getPath() { return path; }

        public void setCompactSum(double compactSum) { this.compactSum = compactSum; }

        public double getCompactSum() { return compactSum; }
    }

    private class Status {
        
        private int pos = -1;

        private double compactSum = 0d;

        private boolean active = false;

        public void activate(int pos) {
            this.pos = pos;
            active = true;
        }

        public void deactivate() { active = false; }

        public void calcCompactSum(int startPos) { compactSum += (double)MAX_SEQ_LENGTH / (pos - startPos + 1); }

        public boolean isActive() { return active; }

        public double getCompactSum() { return compactSum; }
        
        public int getPos() { return pos; }
    }

    public boolean learn(List<com.mobileai.luncert.model.mysql.Event> records) {
        List<TreeNode<Status>> seqs = new ArrayList<>();

        for (int pos = 0, eventId, limit = records.size(); pos < limit; pos++) {
            boolean flag = true;
            eventId = records.get(pos).getEventId();
            for (TreeNode<Status> tree : seqs) {
                if (tree.getId() == eventId) flag = false;
                else addNodeToTree(tree, eventId, 1);
            }
            if (flag) seqs.add(new TreeNode<>(eventId, new Status()));
        }

        for (int pos = 0, eventId, limit = records.size(); pos < limit; pos++) {
            eventId = records.get(pos).getEventId();
            for (TreeNode<Status> tree : seqs) matchNodeToTree(tree, eventId, pos, 1);
        }

        // find best path
        Path bestPath = new Path();
        for (TreeNode<Status> tree : seqs) {
            Path path = findBestPath(tree);
            if (path.getCompactSum() > bestPath.getCompactSum()) bestPath = path;
        }

        System.out.println(Arrays.toString(bestPath.getPath()));
        System.out.println(bestPath.getCompactSum());

        if (bestPath.getCompactSum() > THRESHOLD) {
            // updateGraph();
            return true;
        } else return false;
    }

    private void addNodeToTree(TreeNode<Status> node, int id, int level) {
        boolean flag = true;
        for (TreeNode<Status> child : node.getChildren()) {
            if (child.getId() == id) flag = false;
            else if (level < MAX_SEQ_LENGTH - 1) addNodeToTree(child, id, level + 1);
        }
        if (flag) node.addChild(id, new Status());
    }

    private void matchNodeToTree(TreeNode<Status> node, int id, int pos, int level) {
        Status status = node.getValue();

        if (level > 1 && node.getParent().getValue().getPos() > status.pos) status.deactivate();

        if (node.getId() == id) {
            if (!status.isActive() || status.isActive() && level == 1) {
                status.activate(pos);
                // 匹配路径完成，计算紧凑和
                if (level == MAX_SEQ_LENGTH) {
                    // 获取根节点位置，用于计算紧凑和
                    status.calcCompactSum(node.getParent().getParent().getValue().getPos());
                }
            }
        }
        // 当前节点已经点亮，可以匹配子节点
        else if (node.getValue().isActive() && level < MAX_SEQ_LENGTH) {
            for (TreeNode<Status> child : node.getChildren())
                matchNodeToTree(child, id, pos, level + 1);
        }
    }

    private Path findBestPath(TreeNode<Status> node) {
        return findBestPath(node, 1, new Path());
    }

    private Path findBestPath(TreeNode<Status> node, int level, Path path) {
        if (level == MAX_SEQ_LENGTH) {
            Status status = node.getValue();
            if (status.getCompactSum() > path.getCompactSum()) {
                path.setPath(level - 1, node.getId());
                path.setCompactSum(status.getCompactSum());
            }
            return path;
        }
        else {
            path.setPath(level - 1, node.getId());
            for (TreeNode<Status> child : node.getChildren())
                return findBestPath(child, level + 1, path);
            return path;
        }
    }

    private void updateGraph() {
        /*
        if (bestMatch != null && bestMatchCompactSum > THRESHOLD) {
            System.out.println(bestMatch.getSeq());
            System.out.println(bestMatch.getCompactSum());
            Session session = Neo4jUtil.open();
            StringBuilder statement;
            int[] bestSeq = bestMatch.getSeq();
            for (int i = 1, limit = MAX_SEQ_LENGTH; i < limit; i++) {
                statement = new StringBuilder().append("MATCH (e1:Event)-[r:Derivation]->(e2:Event) WHERE e1.id = ").append(bestSeq[i - 1]).append(" AND e2.id = ").append(bestSeq[i]).append(" RETURN r");
                StatementResult result = session.run(statement.toString());
                if (!result.hasNext()) {
                    // 该边不存在
                    statement = new StringBuilder().append("CREATE (e1:Event)-[:Derivation]->(e2:Event) WHERE e1.id = ").append(bestSeq[i - 1]).append(" AND e2.id = ").append(bestSeq[i]);
                    session.run(statement.toString());
                }
            }
            return true;
        } else return false;
        */
    }

    private KnowledgeGraph() { init(); }

    private void init() {
        // create root node
        root = new TreeNode<>();
        leafNodes = new ArrayList<>();

        Session session = Neo4jUtil.open();

        String statement = "MATCH (parent)<-[:Derivation]-(child) RETURN DISTINCT parent, child";
        StatementResult result = session.run(statement);
        while (result.hasNext()) {
            Record record = result.next();
            Event parentEntity = new Event(record.get("parent"));
            Event childEntity = new Event(record.get("child"));
            List<TreeNode<Event>> matchRet = root.matchAll(parentEntity.getEventId());
            if (matchRet.size() > 0) {
                // create new instance for childEntity, add to each matched parent node
                for (TreeNode<Event> node : matchRet) {
                    if (!((TreeNode<Event>)node).hasChild(childEntity.getEventId())) {
                        new TreeNode<>(childEntity.getEventId(), childEntity, node);
                    }
                }
            } else {
                TreeNode<Event> parent = new TreeNode<>(parentEntity.getEventId(), parentEntity, root);
                new TreeNode<>(childEntity.getEventId(), childEntity, parent);
            }
        }

        leafNodes = root.getLeafNodes();

        Neo4jUtil.close(session);
    }

    private static class MatchGraphInner {
        private static final KnowledgeGraph INSTANCE = new KnowledgeGraph();
    }

}