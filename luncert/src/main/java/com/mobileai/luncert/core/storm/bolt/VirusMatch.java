package com.mobileai.luncert.core.storm.bolt;

import java.util.Map;

import com.mobileai.luncert.core.KnowledgeGraph;
import com.mobileai.luncert.core.storm.entity.MatchResult;
import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.Graph.Path;
import com.mobileai.luncert.utils.Graph.TreeNode;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class VirusMatch extends BaseRichBolt {

    private static final long serialVersionUID = 1L;

    private OutputCollector collector;

    private KnowledgeGraph graph;
    
    private Path<TreeNode<Event>> records;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        graph = KnowledgeGraph.getInstance();
        records = new Path<>();
	}

	@Override
	public void execute(Tuple input) {
        SecurityEvent event = (SecurityEvent)input.getValue(0);
        Values toemit;

        // 强关联匹配
        if (records.size() > 0 && records.lastNode().getParent().match(event.getEventId())) {
            TreeNode<Event> parent = records.lastNode().getParent();
            if (parent.beRoot) {
                // 匹配结束，开始新的匹配
                records = new Path<>();
                toemit = new Values(MatchResult.MATCH_SUCCESS_ALERT, event);
            }
            else {
                // 添加到匹配记录
                records.addNode(parent);
                toemit = new Values(MatchResult.MATCH_SUCCESS, event);
            }
        }
        // 弱关联匹配
        else {
            // 达到阀值，匹配成功
            if (event == null) {}
            // 匹配失败，但是场景事件
            else if (graph.getRoot().find(event.getEventId()).beRoot) {
                toemit = new Values(MatchResult.MATCH_FAILED_ALERT, event);
            }
            // 不是场景事件，视作噪音
            else {}
            toemit = null;
        }
        collector.emit(toemit);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("matchResult", "event"));
    }

}