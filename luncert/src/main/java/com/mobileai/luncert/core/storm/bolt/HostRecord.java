package com.mobileai.luncert.core.storm.bolt;

import java.util.Map;

import com.mobileai.luncert.core.HostGraph;
import com.mobileai.luncert.model.core.SecurityEvent;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class HostRecord extends BaseRichBolt {
    
	private static final long serialVersionUID = 1L;
	
	private HostGraph graph;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		graph = HostGraph.getInstance();
	}

	@Override
	public void execute(Tuple input) {
		SecurityEvent event = (SecurityEvent)input.getValue(0);
		graph.update(event);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("hostRecord"));
	}

}