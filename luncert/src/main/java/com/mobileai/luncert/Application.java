package com.mobileai.luncert;

import java.util.ArrayList;
import java.util.List;

import com.mobileai.luncert.core.storm.bolt.VirusMatch;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

public class Application {

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		String topic = "flumeDemo";
		ZkHosts zkHosts = new ZkHosts("localhost:2181");
		// (hosts, topic, zkRoot, id);
		SpoutConfig spoutConfig = new SpoutConfig(zkHosts, topic, "", "AttackEvent");
		List<String> zkServers =  new ArrayList<>();
		zkServers.add("worker100"); 
		zkServers.add("worker101"); 
		zkServers.add("worker102");
		spoutConfig.zkServers = zkServers;
		spoutConfig.zkPort = 2181;
        spoutConfig.socketTimeoutMs = 60 * 1000 ;
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("kafkaSpout", new KafkaSpout(spoutConfig), 3).setNumTasks(8);
		builder.setBolt("matchBolt", new VirusMatch(), 3).shuffleGrouping("kafkaSpout");


        Config conf = new Config();
        conf.setNumWorkers(4);
        conf.setDebug(true);
        conf.setNumAckers(0);

        StormSubmitter.submitTopology("AttackStormTopology", conf, builder.createTopology());
	}
}
