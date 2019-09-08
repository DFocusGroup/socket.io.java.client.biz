package com.dfocus.socket;

public enum ClientState {

    CONNECTED("connected"), CONNECTING("connecting"), DISCONNECTED("disconnected");

    private String state;

    private ClientState(String state) {
        this.state = state;
    }

    public static ClientState from(String state) {
        for (ClientState c : ClientState.values()) {
            if (c.state.equals(state)) {
                return c;
            }
        }
        return null;
    }

    public boolean equals(String state) {
        return this.state.equals(state);
    }

    public boolean equals(ClientState state) {
        return this.state.equals(state.toString());
    }

    @Override
    public String toString() {
        return state;
    }

}