package com.dfocus.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EventMessage {
    private String projectId;
    private String topic;
    private String event;
    private String payload;
}