package com.mobileai.luncert.utils;

import org.neo4j.driver.v1.*;

import static java.util.concurrent.TimeUnit.SECONDS;


public class Neo4jUtil {

    private static final String NEO4J_BOLT_URL = "bolt://101.200.37.220:7687";
    private static final String NEO4J_USERNAME = "neo4j";
    private static final String NEO4J_PASSWORD = "Luncert428";

    private Config config;

    private Driver driver;

    private Neo4jUtil() {
        config = Config.build().withMaxIdleSessions(50)
                .withConnectionTimeout(300, SECONDS)
                .withLeakedSessionsLogging()
                .toConfig();
    }

    public static Driver getDriver() {
        return GraphDatabase.driver(NEO4J_BOLT_URL, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD), Neo4jUtilInner.INSTANCE.config);
    }

    public static void close(Driver driver, Session session) {
        session.close();
        driver.close();
    }

    public static Session open() {
        Neo4jUtil ins = Neo4jUtilInner.INSTANCE;
        ins.driver = GraphDatabase.driver(NEO4J_BOLT_URL, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD), ins.config);
        return ins.driver.session();
    }

    public static void close(Session session) {
        session.close();

        Neo4jUtil ins = Neo4jUtilInner.INSTANCE;
        if (ins.driver != null) {
            ins.driver.close();
            ins.driver = null;
        }
    }

    private static class Neo4jUtilInner {
        private static final Neo4jUtil INSTANCE = new Neo4jUtil();
    }
}