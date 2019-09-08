package com.dfocus.socket;

public class RetryOpts {
    /**
     * whether to reconnect automatically. Default is true
     */
    private Boolean reconnection = true;
    /**
     * number of reconnection attempts before giving up Default is Infinity
     */
    private Integer reconnectionAttempts = Integer.MAX_VALUE;
    /**
     * how long to initially wait before attempting a new reconnection (1000).
     * Affected by +/- randomizationFactor, for example the default initial delay
     * will be between 500 to 1500ms. Default is 1000
     */
    private Integer reconnectionDelay = 1000;
    /**
     * maximum amount of time to wait between reconnections (5000). Each attempt
     * increases the reconnection delay by 2x along with a randomization as above
     * Default is 5000
     */
    private Integer reconnectionDelayMax = 5000;


    public Boolean getReconnection() {
        return reconnection;
    }

    public void setReconnection(Boolean reconnection) {
        this.reconnection = reconnection;
    }

    public Integer getReconnectionAttempts() {
        return reconnectionAttempts;
    }

    public void setReconnectionAttempts(Integer reconnectionAttempts) {
        this.reconnectionAttempts = reconnectionAttempts;
    }

    public Integer getReconnectionDelay() {
        return reconnectionDelay;
    }

    public void setReconnectionDelay(Integer reconnectionDelay) {
        this.reconnectionDelay = reconnectionDelay;
    }

    public Integer getReconnectionDelayMax() {
        return reconnectionDelayMax;
    }

    public void setReconnectionDelayMax(Integer reconnectionDelayMax) {
        this.reconnectionDelayMax = reconnectionDelayMax;
    }

    public RetryOpts(Boolean reconnection) {
        this.reconnection = reconnection;
    }
}