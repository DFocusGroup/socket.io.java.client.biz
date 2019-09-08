package com.dfocus.socket;

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

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setReconnect(RetryOpts opts) {
        this.reconnect = opts;
    }

    public RetryOpts getReconnect() {
        return this.reconnect;
    }
  
}
