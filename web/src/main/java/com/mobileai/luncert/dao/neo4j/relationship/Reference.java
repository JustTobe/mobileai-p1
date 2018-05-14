package com.mobileai.luncert.dao.neo4j.relationship;

import com.mobileai.luncert.dao.neo4j.node.Host;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "Reference")
public class Reference {
    
    @GraphId
    Long id;

    @StartNode
    private Host sourceHost;

    @EndNode
    private Host targetHost;

    @Property(name = "riskEstimates")
    private float riskEstimates;

    @Property(name = "alertTimes")
    private int alertTimes;

    @Property(name = "eventTiems")
    private int eventTimes;

    public void setId(Long id) { this.id = id; }

    public void setSourceHost(Host sourceHost) { this.sourceHost = sourceHost; }

    public void setTargetHost(Host targetHost) { this.targetHost = targetHost; }

    public void setRiskEstimates(float riskEstimates) { this.riskEstimates = riskEstimates; }

    public void setAlertTimes(int alertTimes) { this.alertTimes = alertTimes; }

    public void setEventTimes(int eventTimes) { this.eventTimes = eventTimes; }

    public long getId() { return id; }

    public Host getSourceHost() { return sourceHost; }

    public Host getTargetHost() { return targetHost; }

    public float getRiskEstimates() { return riskEstimates; }

    public int getAlertTimes() { return alertTimes; }

    public int getEventTimes() { return eventTimes; }

}