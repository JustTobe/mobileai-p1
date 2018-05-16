package com.mobileai.luncert.model.neo4j;


import java.util.List;

import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.model.neo4j.relationship.Derivation;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository extends Neo4jRepository<Event, Long>{

    @Query("MATCH p=(start:Event)-[:Derivation]->(end:Event) return p")
    public List<Derivation> fetchAllRelationship();

}