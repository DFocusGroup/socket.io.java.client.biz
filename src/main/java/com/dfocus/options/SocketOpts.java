package com.dfocus.options;

import lombok.Data;

@Data
public class SocketOpts {
    private String base;
    private String projectId;
    private String token;
    private RetryOpts reconnect = new RetryOpts(true);

    public SocketOpts(String base, String projectId, String token) {
        this.base = base;
        this.projectId = projectId;
        this.token = token;
    }

    public SocketOpts withReconnectOpts(RetryOpts opts) {
        setReconnect(opts);
        return this;
    }
}
