package com.mobileai.core;

import com.mobileai.luncert.core.GenEvent;
import com.mobileai.luncert.model.core.SecurityEvent;
import com.mobileai.luncert.model.neo4j.node.Event;
import com.mobileai.luncert.utils.IPUtil;
import com.mobileai.luncert.utils.Graph.Path;
import com.mobileai.luncert.utils.Graph.TreeNode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestGenEvent {

    private void outputPath(Path<TreeNode<Event>> path) {
        StringBuilder builder = new StringBuilder();
        for (TreeNode<Event> node : path.allNodes()) {
            Event event = (Event)node.getValue();
            builder.append(" -> [").append(event.getEventId()).append("]").append(event.getName());
        }
        System.out.println(builder.toString());

    }

    private void outputEvent(SecurityEvent event) {
        System.out.println(IPUtil.ipToString(event.getSource()) + "|" + IPUtil.ipToString(event.getTarget()) + "|" + event.getEventId());
    }

    @Test
    public void test() {
        GenEvent genEvent = new GenEvent();
        for (int i = 0; i < 10; i++) {
            SecurityEvent event = genEvent.nextEvent();
            outputEvent(event);
            outputPath(genEvent.getPath());
        }
    }

}