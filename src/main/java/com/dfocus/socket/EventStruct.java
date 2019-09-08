package com.dfocus.socket;

public class EventStruct {
    private String topic;
    private String event;
    private EventCallback callback;

    public EventStruct(String topic, String event, EventCallback callback) {
        this.topic = topic;
        this.event = event;
        this.callback = callback;
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

    public EventCallback getCallback() {
        return callback;
    }

    public void setCallback(EventCallback callback) {
        this.callback = callback;
    }

}