package com.mobileai.luncert.core;

import java.util.ArrayList;
import java.util.List;

import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.neo4j.node.Host;
import com.mobileai.luncert.model.neo4j.relationship.Reference;
import com.mobileai.luncert.utils.Neo4jUtil;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

public class HostGraph {

    private Driver driver;

    public static HostGraph getInstance() { return HostGraphInner.INSTANCE; }

    public void update(SecurityEvent event) {
        Session session = driver.session();

        Host source;

        StringBuilder statement = new StringBuilder().append("MATCH (h:Host) WHERE h.ip = ").append(event.getSource()).append(" RETURN host");
        StatementResult result = session.run(statement.toString());
        if (result.hasNext()) source = new Host(result.next().get("host"));
        else {
            // source 主机不存在，创建节点
            statement = new StringBuilder().append("CREATE (host:Host{ip: ").append(event.getSource()).append(", riskEstimates: 0}) RETURN host");
            source = new Host(session.run(statement.toString()).next().get("host"));
        }

        statement = new StringBuilder().append("MATCH (h:Host) WHERE h.ip = ").append(event.getTarget()).append(" RETURN host");
        result = session.run(statement.toString());
        if (!result.hasNext()) {
            // target 主机不存在，创建节点
            statement = new StringBuilder().append("CREATE (host:Host{ip: ").append(event.getTarget()).append(", riskEstimates: 0})");
            session.run(statement.toString());
        }

        statement = new StringBuilder().append("MATCH (target:Host)-[ref:Reference]->(source:Host) WHERE target.ip = ").append(event.getTarget()).append(" AND source.ip = ").append(event.getSource()).append(" RETURN target, ref, source");
        result = session.run(statement.toString());
        if (result.hasNext()) {
            Reference ref = new Reference(result.next().get("ref"));
            ref.setProbability(event.getTypeIdentifier());
            ref.calcRiskEstimates();
        } else {
            // source主机对target主机无访问记录，创建关系
            Reference ref = new Reference();
            ref.setProbability(event.getTypeIdentifier());
            statement = new StringBuilder().append("CREATE (source:Host)-[r:Reference{riskEstimates: ").append(ref.getRiskEstimates())
                .append(", l1Probability: ").append(ref.getL1Probability())
                .append(", l2Probability: ").append(ref.getL2Probability())
                .append(", l3Probability: ").append(ref.getL3Probability())
                .append(", l4Probability: ").append(ref.getL4Probability())
                .append("}]->(target:Host) WHERE source.ip = ").append(event.getSource())
                .append(" AND target.ip = ").append(event.getTarget());
            session.run(statement.toString());
        }

        // 获取所有source主机对其他主机的访问记录，计算source主机危险度
        Double totalEstimates = 0d, expectation = 0d;
        List<Double> refEstimates = new ArrayList<>();
        statement = new StringBuilder().append("MATCH (source:Host)-[ref:Reference]->() where source.ip = ").append(event.getSource()).append(" RETURN ref");
        result = session.run(statement.toString());
        while (result.hasNext()) {
            Value ref = result.next().get("ref");
            refEstimates.add(Double.valueOf(ref.get("riskEstimates", 0)));
            totalEstimates += refEstimates.get(refEstimates.size() - 1);
        }
        for (int i = 0, limit = refEstimates.size(); i < limit; i++) {
            expectation += refEstimates.get(i) * refEstimates.get(i) / totalEstimates;
        }
        source.setRiskEstimates(expectation);

        // 更新主机危险度
        statement = new StringBuilder().append("MATCH (host:Host) set host.riskEstimates = ").append(source.getRiskEstimates()).append(" WHERE host.ip = ").append(source.getIp());
        session.run(statement.toString());
    }

    private HostGraph() {
        driver = Neo4jUtil.getDriver();
    }

    private static class HostGraphInner {
        private static final HostGraph INSTANCE = new HostGraph();
    }
}