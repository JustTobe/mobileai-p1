package com.mobileai.luncert;

import com.mobileai.luncert.core.storm.bolt.EventRecord;
import com.mobileai.luncert.core.storm.bolt.HostRecord;
import com.mobileai.luncert.core.storm.bolt.VirusDetect;
import com.mobileai.luncert.core.storm.bolt.VirusMatch;
import com.mobileai.luncert.core.storm.spout.LocalSpout;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

public class LocalApp {

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new LocalSpout(), 4);

        builder.setBolt("virusMatch", new VirusMatch(), 2).setNumTasks(4).shuffleGrouping("spout");
       
        builder.setBolt("virusDetect", new VirusDetect(), 2).setNumTasks(4).fieldsGrouping("virusMatch", new Fields("matchResult", "event"));
        builder.setBolt("hostRecord", new HostRecord(), 2).setNumTasks(4).fieldsGrouping("virusMatch", new Fields("matchResult", "event"));
        builder.setBolt("eventRecord", new EventRecord(), 2).setNumTasks(4).fieldsGrouping("virusMatch", new Fields("matchResult", "event"));

        Config conf = new Config();
        conf.setDebug(true);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(2);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", conf, builder.createTopology());
            Utils.sleep(10000);
            cluster.killTopology("test");
            cluster.shutdown();
        }
    }

}