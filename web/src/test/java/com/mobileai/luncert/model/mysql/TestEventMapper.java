package com.mobileai.luncert.model.mysql;

import java.util.List;

import com.mobileai.luncert.model.mysql.EventMapper;
import com.mobileai.luncert.model.mysql.entity.Event;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEventMapper {

    private static Logger logger = Logger.getLogger(TestUserMapper.class);

	@Autowired
	private EventMapper eventMapper;

	@Test
	public void testFetchAll() {
        
		List<Event> result = eventMapper.fetchAll();
		for (Event event : result) {
			logger.info(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());		
        }
    }

    @Test
    public void testFetchN() {
		logger.info("");		
        
		List<Event> result = eventMapper.fetchN(1);
		for (Event event : result) {
			logger.info(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());		
		}
	}

}
