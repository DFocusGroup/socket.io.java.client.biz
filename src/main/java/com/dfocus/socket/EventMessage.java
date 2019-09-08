package com.dfocus.socket;

public class EventMessage {
    private String projectId;
    private String topic;
    private String event;
    private String payload;

    public EventMessage(String projectId, String topic, String event, String payload) {
        this.projectId = projectId;
        this.topic = topic;
        this.event = event;
        this.payload = payload;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}