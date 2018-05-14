package com.mobileai.luncert.core.storm.bolt;

import java.util.List;
import java.util.Map;

import com.mobileai.luncert.core.KnowledgeGraph;
import com.mobileai.luncert.core.storm.entity.MatchResult;
import com.mobileai.luncert.mapper.EventMapper;
import com.mobileai.luncert.model.mysql.Event;
import com.mobileai.luncert.utils.MySQLUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class VirusDetect extends BaseRichBolt {

	private static final long serialVersionUID = 1L;

	private KnowledgeGraph graph;
    
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		graph = KnowledgeGraph.getInstance();
	}

	@Override
	public void execute(Tuple input) {
		if ((int)input.getValue(0) == MatchResult.MATCH_FAILED_ALERT) {
			// 取出上次被攻击后的所有事件记录
			SqlSession session = MySQLUtil.open();
			EventMapper eventMapper = session.getMapper(EventMapper.class);
			Event lastAlert = eventMapper.fetchLastAlert();
			List<Event> records;
			if (lastAlert != null) records = eventMapper.fetchAllAfter(lastAlert.getId());
			else records = eventMapper.fetch1000();
			MySQLUtil.close(session);
			
			// 交由KnowledgeGraph学习
			graph.learn(records);
			
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("virusDetect"));
	}

}