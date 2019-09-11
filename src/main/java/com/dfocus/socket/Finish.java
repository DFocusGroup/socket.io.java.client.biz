package com.dfocus.socket;

public interface Finish {

    /**
     * will be called once connected or failed
     * 
     * @param errorMessage message to indicate if error occurs or not
     * 
     */
    public void onFinished(String errorMessage);
}