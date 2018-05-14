package com.mobileai.luncert.service;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Neo4jDBCleaner {

    @Autowired
    Session session;

    public void cleanDB() {
        session.purgeDatabase();
    }

}