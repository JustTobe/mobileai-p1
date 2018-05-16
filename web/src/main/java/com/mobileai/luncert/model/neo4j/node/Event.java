package com.mobileai.luncert.model.neo4j.node;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "Event")
public class Event {

    @Id @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "eventType")
    private String eventType;

    @Property(name = "id")
    private int eventId;

    @Relationship(type = "Derivation", direction = Relationship.OUTGOING)
    private Set<Event> nextHop = new HashSet<>();
    
    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    public void setEventId(int eventId) { this.eventId = eventId; }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getEventType() { return eventType; }

    public int getEventId() { return eventId; }

}