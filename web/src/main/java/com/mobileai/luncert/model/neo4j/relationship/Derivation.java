package com.mobileai.luncert.model.neo4j.relationship;

import com.mobileai.luncert.model.neo4j.node.Event;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "Derivation")
public class Derivation {

    @Id @GeneratedValue
    Long id;

    @StartNode
    Event startEvent;

    @EndNode
    Event endEvent;

    public void setId(Long id) { this.id = id; }

    public void setStartEvent(Event startEvent) { this.startEvent = startEvent; }

    public void setEndEvent(Event endEvent) { this.endEvent = endEvent; }

    public Long getId() { return id; }

    public Event getStartEvent() { return startEvent; }

    public Event getEndEvent() { return endEvent; }
    
}