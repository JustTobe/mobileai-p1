package com.mobileai.luncert.dao.neo4j;

import com.mobileai.luncert.Application;
import com.mobileai.luncert.dao.neo4j.node.Host;
import com.mobileai.luncert.service.Neo4jDBCleaner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestHostRepository {

    @Autowired
    private HostRepository repo;

    @Autowired
    private Neo4jDBCleaner cleaner;

    @Test
    public void testFindAll() {
        for (Host host : repo.findAll()) {
            System.out.println(host.getId() + " = " + host.getIpString() + " : " + host.getReliability());
        }
    }

    @Test
    public void testFindOne() {
        Host host = repo.findByIp(16777343);
        System.out.println(host.getId() + " = " + host.getIpString() + " : " + host.getReliability());
    }

    /*
    @Test
    public void testSave() {
        Host host = new Host("127.0.0.1", 28.01);
        repo.save(host);
    }
    */
    
}