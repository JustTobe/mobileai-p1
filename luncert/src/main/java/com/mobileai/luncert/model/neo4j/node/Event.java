package com.mobileai.luncert.model.neo4j.node;

import com.mobileai.luncert.model.normal.NormalEvent;

import org.neo4j.driver.v1.Value;

public class Event extends NormalEvent {

    private String name;

	private boolean beScene;

    public Event(Value value) {
        eventId = Integer.valueOf(value.get("id", ""));
        name = value.get("name", "");
		if (value.get("sceneType") != null) {
			eventType = value.get("sceneType", "");
			beScene = true;
		}
		else {
			eventType = value.get("eventType", "");
			beScene = false;
		}
    }

	public void setName(String name) { this.name = name; }

	public String getName() { return name; }

	public void setBeScene(boolean beScene) { this.beScene = beScene; }

	public boolean getBeScene() { return beScene; }
    
}