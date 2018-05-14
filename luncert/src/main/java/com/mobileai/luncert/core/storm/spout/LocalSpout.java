package com.mobileai.luncert.core.storm.spout;

import java.util.Map;

import com.mobileai.luncert.core.GenEvent;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class LocalSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	
	private SpoutOutputCollector collector;

	private GenEvent genEvent;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		genEvent = new GenEvent();
	}

	@Override
	public void nextTuple() {
		collector.emit(new Values(genEvent.nextEvent()));
		Utils.sleep(1000);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("localSpout"));
	}

}