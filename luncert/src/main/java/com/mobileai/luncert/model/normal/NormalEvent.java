package com.mobileai.luncert.model.normal;



public class NormalEvent {

	public static final int DETAIL_ATTACK_PATTERN = 1;

	public static final int STANDARD_ATTACK_PATTERN = 2;

	public static final int META_ATTACK_PATTERN = 3;

    public static final int SCENE = 4;
    
    protected int eventId;

    protected String eventType;

	public void setEventId(int eventId) { this.eventId = eventId; }

	public int getEventId() { return eventId; }

	public void setType(String eventType) { this.eventType = eventType; }

    public String getType() { return eventType; }

	public int getTypeIdentifier() {
		if (eventType.startsWith("Detail")) return DETAIL_ATTACK_PATTERN;
		else if (eventType.startsWith("Standard")) return STANDARD_ATTACK_PATTERN;
		else if (eventType.startsWith("Meta")) return META_ATTACK_PATTERN;
		else return SCENE;
	}
    
}