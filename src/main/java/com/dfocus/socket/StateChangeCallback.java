package com.dfocus.socket;

import com.dfocus.enums.ClientState;

public interface StateChangeCallback {
    public void onChange(ClientState state);
}