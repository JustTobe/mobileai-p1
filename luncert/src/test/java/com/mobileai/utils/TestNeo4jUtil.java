package com.mobileai.utils;

import com.mobileai.luncert.utils.Neo4jUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

@RunWith(JUnit4.class)
public class TestNeo4jUtil {
    
    @Test
    public void test() {
        Session session = Neo4jUtil.open();
        
        StatementResult result = session.run("match (c:Category) return c limit 25");
        while (result.hasNext()) {
            Record record = result.next();
            Value value = record.get("c");
            System.out.println(value.get("name", "") + ":" + value.get("id", ""));
        }

        Neo4jUtil.close(session);
    }
}