package com.dfocus.socket;

import io.socket.emitter.Emitter.Listener;

public class EventStruct {
    private String topic;
    private String event;
    private Listener callback;

    public EventStruct(String topic, String event, Listener callback) {
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

    public Listener getCallback() {
        return callback;
    }

    public void setCallback(Listener callback) {
        this.callback = callback;
    }

}