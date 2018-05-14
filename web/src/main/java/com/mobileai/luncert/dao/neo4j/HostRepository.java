package com.mobileai.luncert.dao.neo4j;


import java.util.List;

import com.mobileai.luncert.dao.neo4j.node.Host;
import com.mobileai.luncert.dao.neo4j.relationship.Reference;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends GraphRepository<Host> {

    Host findByIp(@Param("ip") int ip);

    @Query("MATCH (h:Host)<-[r:Reference]-(source:Host) WHERE id(h)={host} return r")
    List<Reference> getSourceAccess(@Param("host") Host host);

    @Query("MATCH (h:Host)-[r:Reference]->(target:Host) WHERE id(h)={host} return r")
    List<Reference> getTargetAccess(@Param("host") Host host);

    @Query("MATCH (h:Host)<-[r:Reference]-(source:Host) WHERE id(h)={host} return source")
    List<Host> getSources(@Param("host") Host host);

    @Query("MATCH (h:Host)-[r:Reference]->(target:Host) WHERE id(h)={host} return target")
    List<Host> getTargets(@Param("host") Host host);

}