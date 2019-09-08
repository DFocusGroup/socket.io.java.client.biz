package com.dfocus.socket;

public class AuthStruct {
    private String projectId;
    private String token;

    public AuthStruct(String projectId, String token) {
        this.projectId = projectId;
        this.token = token;
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

}