package com.dfocus.enums;

public enum BizEvent {
    AUTH("auth"), SUBSCRIBE("subscribe");

    private String event;

    private BizEvent(String event) {
        this.event = event;
    }

    public static BizEvent from(String event) {
        for (BizEvent c : BizEvent.values()) {
            if (c.event.equals(event)) {
                return c;
            }
        }
        return null;
    }

    public boolean equals(String event) {
        return this.event.equals(event);
    }

    public boolean equals(BizEvent event) {
        return this.event.equals(event.toString());
    }

    @Override
    public String toString() {
        return event;
    }
}