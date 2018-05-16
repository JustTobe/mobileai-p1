package com.mobileai.luncert.model.neo4j;

import java.util.List;

import com.mobileai.luncert.Application;
import com.mobileai.luncert.model.neo4j.relationship.Derivation;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestEventRepository {

    private static Logger logger = Logger.getLogger(TestEventRepository.class);

    @Autowired
    EventRepository eventRepository;

    @Test
    public void testFetchAllRelationship() {
        List<Derivation> r = eventRepository.fetchAllRelationship();
        for (Derivation item : r) {
            logger.info(item.getStartEvent().getName() + " -> " + item.getEndEvent().getName());
        }
    }

}