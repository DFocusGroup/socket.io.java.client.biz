package com.dfocus.socket;

import com.dfocus.po.EventMessage;

public interface EventCallback {
    public void onFire(EventMessage message);
}