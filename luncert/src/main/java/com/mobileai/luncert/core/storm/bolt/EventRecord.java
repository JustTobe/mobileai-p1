package com.mobileai.luncert.core.storm.bolt;

import java.util.Date;
import java.util.Map;

import com.mobileai.luncert.core.storm.entity.MatchResult;
import com.mobileai.luncert.mapper.EventMapper;
import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.utils.MySQLUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class EventRecord extends BaseRichBolt {

    private static final long serialVersionUID = 1L;


	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {}

	@Override
	public void execute(Tuple input) {
		int matchResult = (int)input.getValue(0);
        boolean toAlert = matchResult == MatchResult.MATCH_FAILED_ALERT || matchResult == MatchResult.MATCH_SUCCESS_ALERT;
		SecurityEvent event = (SecurityEvent)input.getValue(1);
		
		// add event to mysql
		SqlSession session = MySQLUtil.open();
		session.getMapper(EventMapper.class).addEvent(new Date(), event.getSource(), event.getTarget(), event.getEventId(), toAlert);
		MySQLUtil.close(session);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("eventRecord"));
	}

}