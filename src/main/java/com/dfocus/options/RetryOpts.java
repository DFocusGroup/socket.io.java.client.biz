package com.dfocus.options;

import lombok.Getter;
import lombok.Setter;

public class RetryOpts {
    /**
     * whether to reconnect automatically. Default is true
     */
    @Getter @Setter private Boolean reconnection = true;
    /**
     * number of reconnection attempts before giving up Default is Infinity
     */
    @Getter @Setter private Integer reconnectionAttempts = Integer.MAX_VALUE;
    /**
     * how long to initially wait before attempting a new reconnection (1000).
     * Affected by +/- randomizationFactor, for example the default initial delay
     * will be between 500 to 1500ms. Default is 1000
     */
    @Getter @Setter private Integer reconnectionDelay = 1000;
    /**
     * maximum amount of time to wait between reconnections (5000). Each attempt
     * increases the reconnection delay by 2x along with a randomization as above
     * Default is 5000
     */
    @Getter @Setter private Integer reconnectionDelayMax = 5000;

    public RetryOpts(Boolean reconnection) {
        this.reconnection = reconnection;
    }
}