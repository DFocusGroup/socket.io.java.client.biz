package com.dfocus.po;

import io.socket.emitter.Emitter.Listener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EventStruct {
    private String topic;
    private String event;
    private Listener callback;
}