package com.mobileai.luncert.dao.mysql;

import java.util.List;

import com.mobileai.luncert.dao.mysql.EventMapper;
import com.mobileai.luncert.dao.mysql.entity.Event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEventMapper {

	@Autowired
	private EventMapper eventMapper;

	@Test
	public void testFetchAll() {
        System.out.println("fetchAll:");
        
		List<Event> result = eventMapper.fetchAll();
		for (Event event : result) {
			System.out.println(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());
        }
    }

    @Test
    public void testFetchN() {
        System.out.println("fetchN:");
        
		List<Event> result = eventMapper.fetchN(1);
		for (Event event : result) {
			System.out.println(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());
		}
	}

}
